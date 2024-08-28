  if<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="30%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-form-item
              prop="tenantId"
              label="租户"
              :rules="[{ required: true, message: '租户不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="editFormData.tenantId"
                :maxLength="21"
                placeholder="请选择租户"
                clearable
                class="dc-el-input_SingleInput"
                oninput="value=value.replace(/[^\d]/g,'')"
              >
                <el-option
                  v-for="(item, index) in tenant"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              prop="masterDataTypeId"
              label="主数据类型"
              :rules="[{ required: true, message: '主数据类型不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="editFormData.masterDataTypeId"
                :maxLength="21"
                placeholder="请选择主数据类型"
                clearable
                class="dc-el-input_SingleInput"
              >
               <el-option
                    v-for="(item, index) in MainselectOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              prop="appId"
              label="生产者"
              :rules="[{ required: true, message: '应用不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="editFormData.appId"
                :maxLength="21"
                placeholder="请选择生产者"
                clearable
                class="dc-el-input_SingleInput"
                oninput="value=value.replace(/[^\d]/g,'')"
              >
                    <el-option
                      v-for="(item, index) in appIdAndName"
                      :key="index"
                      :label="item.app.name+item.name"
                      :value="item.id"
                    ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              prop="dataModel"
              label="消费者"
              :rules="[{ required: true, message: '应用不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select v-model="editFormData.dataModel" placeholder="请选择"
                         multiple="true"
                         size="medium"
                         clearable="true"
                         collapse-tags
                         style="width: 80%"
              >
                <el-option
                  v-for="item in appIdAndName"
                  :key="item.id"
                  :label="item.app.name+item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="remarks" label="备注信息" class="dc-el-form-item_MutilpleInput">
              <el-input
                v-model="editFormData.remarks"
                type="textarea"
                placeholder="请输入备注信息"
                rows="2"
                :maxLength="255"
                class="dc-el-input_MutilpleInput"
              ></el-input>
            </el-form-item>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import { getProductionRelationById, saveProductionRelation,listLedgertypenameAll } from '@/api/databus/productionRelation'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import { listMasterDataTypePage, deleteMasterDataType } from '@/api/databus/masterDataType'
import { listMicroVersionAll } from '@/api/micro/microVersion.js'
export default {
  extends: BaseUI,
  name: 'productionRelation-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '生产消费关系'
      },
      dialogTitle: '生产消费关系',
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      // 模式选项
      selectOptions: [
        {
          label: '消费',
          value: '0'
        },
        {
          label: '生产',
          value: '1'
        }
      ],
      // 主数据选项
      MainselectOptions: [
      ],
      appIdAndName: [],
      // 租户信息
      tenant: [],
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      this.setLoad()
      var editFormData = {
        tenantId: this.editFormData.tenantId, // 租户id
        masterDataTypeId: this.editFormData.masterDataTypeId, // 主数据类型id
        appId:this.editFormData.appId, // 应用
        dataModel: '', // 模式
        remarks:this.editFormData.remarks // 备注信息
      };
      var dataModel ="";
      debugger
      for (let i = 0; i < this.editFormData.dataModel.length; i++) {
        var dataModelElement = this.editFormData.dataModel[i];
        dataModel+=dataModelElement+",";
      }
      editFormData.dataModel = dataModel
      saveProductionRelation(editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.$emit('save-finished')
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    switchEdit() {
      this.action = 'edit'
      this.dialogProps.title = `修改${this.dialogTitle}`
      this.initOptions(this.editFormData)
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getProductionRelationById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              responseData.data.dataModel = responseData.data.dataModel.split(",");
              form = responseData.data
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
            resolve(form)
          })
          .catch((error) => {
            this.outputError(error)
          })
      })
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    listMasterDataTypeOptions() {

      listMicroVersionAll({"params":[]}).then((responseData) => {
        if (responseData.code == 100) {
          this.appIdAndName = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initOptions(This) {

      // 初始化自定义类型选择框选项变量
       this.listMasterDataTypeOptions()
    },
    initEditData(This) {
      return {
        tenantId: '', // 租户id
        masterDataTypeId: '', // 主数据类型id
        appId: '', // 应用
        dataModel: '', // 模式
        remarks: '' // 备注信息
      }
    },


  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openCopyDialog', async function (id) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.editFormData.id = null //把id设置为空，添加一个新的

        this.dialogProps.visible = true
      })
    })
        this.setLoad()
      /* 查询参数 和数据权限 */

      listMasterDataTypePage({"params":[],"offset":0,"limit":10,"columnName":"","order":""})
        .then((responseData) => {
          if (responseData.code == 100) {

            responseData.data.rows
            if(responseData.data.total!=0){
            for (let index = 0; index < responseData.data.rows.length; index++) {
              const element = responseData.data.rows[index];
              const row = {
                label: element.name,
                value: element.id
              }
              this.MainselectOptions.push(row);
            }
            }



          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    listLedgertypenameAll({"params":[],"offset":0,"limit":10,"columnName":"","order":""})
      .then((responseData) => {
        if (responseData.code == 100) {

          responseData.data.rows
          if(responseData.data.total!=0){
            for (let index = 0; index < responseData.data.length; index++) {
              const element = responseData.data[index];
              const row = {
                label: element.name,
                value: element.id
              }
              this.tenant.push(row);
            }
          }
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      })
      .catch((error) => {
        this.outputError(error)
      })
  }
}
</script>
