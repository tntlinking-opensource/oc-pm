<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="70%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-tabs v-model="tabName" type="border-card" tab-position="top" class="dc-el-tabs_ElTabs">
          <el-tab-pane label="应用主数据" name="editForm" class="dc-el-tab-pane_ElTabPane">
            <el-form
              ref="editForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <el-form-item
                  prop="masterDataType.id"
                  label="主数据类型"
                  :rules="[{ required: true, message: '主数据类型不能为空', trigger: 'change' }]"
                  class="dc-el-form-item_SelectInput"
                >
                  <el-select
                    v-model="editFormData.masterDataType"
                    :style="{ width: '100%' }"
                    placeholder="请选择主数据类型"
                    clearable
                    value-key="id"
                    filterable
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in masterDataTypeOptions"
                      :key="index"
                      :label="item.name"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  prop="appId"
                  label="应用"
                  :rules="[{ required: true, message: '应用不能为空', trigger: 'change' }]"
                  class="dc-el-form-item_SelectInput"
                >
                  <el-select
                    v-model="editFormData.appId"
                    :style="{ width: '100%' }"
                    placeholder="请选择"
                    clearable
                    filterable
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in selectOptions"
                      :key="index"
                      :label="item.name"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="defaultWorkModel" label="默认工作模式" class="dc-el-form-item_SelectInput">
                  <el-select
                    v-model="editFormData.defaultWorkModel"
                    :style="{ width: '100%' }"
                    placeholder="请选择"
                    clearable
                    filterable
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in workOptions"
                      :key="index"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="produceWorkModel" label="是否支持生产者工作模式" class="dc-el-form-item_SelectInput">
                  <el-select
                    v-model="editFormData.produceWorkModel"
                    :style="{ width: '100%' }"
                    placeholder="请选择"
                    clearable
                    filterable
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in producerOptions"
                      :key="index"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="consumerWorkModel" label="是否支持消费者模式" class="dc-el-form-item_SelectInput">
                  <el-select
                    v-model="editFormData.consumerWorkModel"
                    :style="{ width: '100%' }"
                    placeholder="请选择"
                    clearable
                    filterable
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in consumerOptions"
                      :key="index"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="level" label="level" class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="editFormData.level"
                    :maxLength="2"
                    placeholder="请输入level"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="auditStatus" label="审核" class="dc-el-form-item_Switch">
                  <el-switch
                    v-model="editFormData.auditStatus"
                    active-text="通过"
                    inactive-text="未通过"
                    active-value="1"
                    inactive-value="0"
                    class="dc-el-switch_Switch"
                  ></el-switch>
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
          </el-tab-pane>
        </el-tabs>
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
import { listMasterDataTypeAll } from '@/api/databus/masterDataType.js'
import { listMicroVersionAll } from '@/api/micro/microVersion.js'
/** 根据用户界面配置import组件 结束 */
import { getAppMasterDataTypeById, saveAppMasterDataType } from '@/api/databus/appMasterDataType'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'appMasterDataType-form',
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
        title: '应用主数据类型配置'
      },
      dialogTitle: '应用主数据类型配置',
      // 选项变量
      // 主数据类型选项
      masterDataTypeOptions: [],
      // 应用选项
      selectOptions: [],
      // 默认工作模式选项
      workOptions: [
        {
          label: '消费者',
          value: '0'
        },
        {
          label: '生产者',
          value: '1'
        }
      ],
      // 是否支持生产者工作模式选项
      producerOptions: [
        {
          label: '支持',
          value: '1'
        },
        {
          label: '不支持',
          value: '0'
        }
      ],
      // 是否支持消费者模式选项
      consumerOptions: [
        {
          label: '支持',
          value: '1'
        },
        {
          label: '不支持',
          value: '0'
        }
      ],
      tabName: 'editForm', // tab标签页
      // 窗口操作类型 view/edit/add
      action: ''
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
      let validFlag = true

      this.$refs['editForm'].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = 'editForm'
          validFlag = false
          return false
        }
      })
      if (validFlag) {
        this.doSave()
      }
    },
    doSave() {
      this.setLoad()

      saveAppMasterDataType(this.editFormData)
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
        getAppMasterDataTypeById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
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
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 平台主数据master_data_type
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1434540843767783426')

      listMasterDataTypeAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.masterDataTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listSelectOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      listMicroVersionAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.selectOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listMasterDataTypeOptions()

      this.listSelectOptions()
    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData(This) {
      return {
        masterDataType: {
          id: null
        },
        appId: null, // 应用
        defaultWorkModel: '0', // 默认工作模式
        produceWorkModel: '1', // 是否支持生产者工作模式
        consumerWorkModel: '1', // 是否支持消费者模式
        level: '0', // level
        auditStatus: '0', // 审核
        remarks: '' // 备注信息
      }
    }
  },
  watch: {
    tabName(val, oldVal) {
      this.$nextTick(() => {
        if (this.$refs[val] && this.$refs[val].doLayout) {
          this.$refs[val].doLayout()
        }
      })
    }
  },
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
        this.tabName = 'editForm'
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
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
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
        this.tabName = 'editForm'
        this.editFormData.id = null //把id设置为空，添加一个新的

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
<style>
.my-input-sc {
  display: inline-block;
  height: 30px;
  line-height: 30px;
  -webkit-appearance: none;
  background-color: #ffffff;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  box-sizing: border-box;
  color: #606266;
  font-size: inherit;
  outline: none;
  padding: 0 15px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
}
</style>
