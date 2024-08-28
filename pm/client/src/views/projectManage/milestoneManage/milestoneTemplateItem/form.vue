<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="60%"
      :modal="false"
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
            <el-col :span="12">
              <el-form-item
                label="模板名称"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  disabled
                  v-model="editFormData.template.templateName"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="milestoneName"
                label="里程碑名称"
                :rules="[{ required: true, message: '里程碑名称不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.milestoneName"
                  :maxLength="50"
                  placeholder="请输入里程碑名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="progressRate"
                label="进度占比"
                :rules="[{ required: true, message: '进度占比不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_NumberInput"
              >
                <number-input
                  v-model="editFormData.progressRate"
                  :min="0"
                  :max="99999999999"
                  append="%"
                  :precision="0"
                  placeholder="请输入进度占比"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="milestoneDesc" label="描述" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.milestoneDesc"
                  :maxLength="300"
                  placeholder="请输入描述"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="sort" label="顺序" class="dc-el-form-item_NumberInput">
                <number-input
                  v-model="editFormData.sort"
                  :min="0"
                  :max="99999999999"
                  :precision="0"
                  placeholder="请输入顺序"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
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
            </el-col>
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
import { listMilestoneTemplateAll } from '@/api/projectManage/milestoneTemplate.js'
/** 根据用户界面配置import组件 结束 */
import {
  getMilestoneTemplateItemById,
  saveMilestoneTemplateItem
} from '@/api/projectManage/milestoneTemplateItem'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import {checkProgressRate} from "../../../../api/projectManage/milestoneTemplateItem";
export default {
  extends: BaseUI,
  name: 'milestoneTemplateItem-form',
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
        title: '里程碑模板详情'
      },
      dialogTitle: '里程碑模板详情',
      // 选项变量
      // 里程碑模板选项
      templateOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: ''
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    },
    template: {
      type: Object,
      required: true
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
    async doSave() {
      this.setLoad()
      let params = {
        id: this.template.id,
        rate: this.editFormData.progressRate,
        excludeId: this.editFormData.id || null
      }
      let res = await checkProgressRate(params)
      if (res.code != 100 || !res.data) {
        this.$alert('该模板下的模板详情的进度占比之和超过100%！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        this.resetLoad()
        return
      }
      saveMilestoneTemplateItem(this.editFormData)
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
        getMilestoneTemplateItemById(id)
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

    listTemplateOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 里程碑模板milestone_template
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121938467')

      listMilestoneTemplateAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.templateOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listTemplateOptions()
    },
    initEditData(This) {
      return {
        template: {
          id: this.template.id,
          templateName: this.template.templateName
        },
        milestoneName: '', // 里程碑名称
        progressRate: null, // 进度占比
        milestoneDesc: '', // 描述
        sort: null, // 顺序
        remarks: '' // 备注信息
      }
    }
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
  }
}
</script>
