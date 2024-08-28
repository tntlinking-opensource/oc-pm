<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="60%"
      :fullscreen="true"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-form
        ref="editForm"
        :model="editFormData"
        label-width="100px"
        class="dc-el-form_ElEditForm"
      >
        <el-row>
          <div class="dc-divide">
            <div>基本信息</div>
          </div>
          <el-col :span="8">
            <el-form-item
              label="项目名称"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.projectName"
                disabled
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="项目简称"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.projectShortName"
                disabled
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="项目编号"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                disabled
                v-model="editFormData.projectCode"
                disabled
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="客户名称"
              class="dc-el-form-item_SelectInput"
            >
              <el-input
                disabled
                v-model="editFormData.custom.name"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="所属部门"
              class="dc-el-form-item_CascaderInput"
            >
              <el-input
                disabled
                v-model="editFormData.department.name"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="项目类型"
              class="dc-el-form-item_SelectInput"
            >
              <el-input
                disabled
                v-model="editFormData.projectType.name"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="项目经理"
              class="dc-el-form-item_SelectInput"
            >
              <el-input
                disabled
                v-model="editFormData.projectManager.name"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="计划开始日期"
              class="dc-el-form-item_PickerTimeDateRange"
            >
              <el-input
                disabled
                v-model="editFormData.planBeginDate"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="计划结束日期"
              class="dc-el-form-item_PickerTimeDateRange"
            >
              <el-input
                disabled
                v-model="editFormData.planEndDate"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="realBeginDate"
              label="实际开始/结束日期"
              :rules="[{ required: true, message: '实际开始/结束日期不能为空', trigger: 'change' }]"
              class="dc-el-form-item_PickerTimeDateRange"
            >
              <el-date-picker
                :value="[editFormData.realBeginDate, editFormData.realEndDate]"
                v-on:input="
                  (val) => {
                    editFormData.realBeginDate = val ? val[0] : ''
                    editFormData.realEndDate = val ? val[1] : ''
                  }
                "
                :style="{ width: '100%' }"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDateRange"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="状态"
              class="dc-el-form-item_PickerTimeDateRange"
            >
              <el-input
                disabled
                v-model="editFormData.projectStatus.name"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <div class="dc-divide">
            <div>项目预算投入</div>
          </div>
          <el-col :span="8">
            <el-form-item label="项目总预算" class="dc-el-form-item_NumberInput">
              <el-input
                disabled
                v-model="editFormData.projectBudget"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="计划投入总人月" class="dc-el-form-item_NumberInput">
              <el-input
                disabled
                v-model="editFormData.projectBudgetPersonMonth"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="计划投入人力成本" class="dc-el-form-item_NumberInput">
              <el-input
                disabled
                v-model="editFormData.planLaborCost"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="项目实际投入" class="dc-el-form-item_NumberInput">
              <el-input
                disabled
                v-model="editFormData.planLaborCost"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="实际投入总人月" class="dc-el-form-item_NumberInput">
              <el-input
                disabled
                v-model="editFormData.planLaborCost"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="实际投入人力成本" class="dc-el-form-item_NumberInput">
              <el-input
                disabled
                v-model="editFormData.planLaborCost"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>

          <div class="dc-divide">
            <div>完成情况</div>
          </div>
          <el-col :span="24">
            <el-form-item prop="completeDesc" label="完成情况" class="dc-el-form-item_MutilpleInput">
              <el-input
                v-model="editFormData.completeDesc"
                type="textarea"
                placeholder="请输入项目描述"
                rows="2"
                :maxLength="300"
                class="dc-el-input_MutilpleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <upload-file
              title="附件"
              :action="action"
              :objectId="editFormData.completeAnnex"
              :fileFormats="['jpg', 'jpeg', 'png', 'doc', 'docx', 'xls', 'xlsx', 'pdf', 'txt']"
              :fileSizes="10"
              v-model="editFormData.completeAnnexFile"
              class="dc-upload-file_ElUploadFile"
            ></upload-file>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
/** 根据用户界面配置import组件 结束 */
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import {getProjectListById, saveProjectCompleted} from "@/api/projectManage/projectList";
import uploadFile from '@/views/components/uploadFile'
export default {
  extends: BaseUI,
  name: 'project-completed-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon,
    uploadFile
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '项目结项'
      },
    }
  },
  props: {
    data: {
      type: Object,
      required: true
    }
  },
  computed: {},
  methods: {
    createFormData(editFormData) {
      let formData = new FormData()
      let deletes = []
      for (let idx in editFormData.completeAnnexFile.uploads) {
        formData.append('annexUploads', editFormData.completeAnnexFile.uploads[idx].raw)
      }
      deletes = deletes.concat(editFormData.completeAnnexFile.deletes)
      formData.append('entity', JSON.stringify(editFormData))
      formData.append('deleteIds', JSON.stringify(deletes))
      return formData
    },
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
      let formData = this.createFormData(this.editFormData)
      saveProjectCompleted(formData).then((responseData) => {
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
      }).catch((error) => {
        this.outputError(error)
      })
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getProjectListById(id).then((responseData) => {
          let form = {}
          if (responseData.code == 100) {
            form = responseData.data
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
          resolve(form)
        }).catch((error) => {
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
    initEditData(This) {
      return {
        projectName: '', // 项目名称
        projectShortName: '', // 项目简称
        projectCode: '', // 项目编号
        custom: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        // 所属部门
        department: {
          id: null,
          name: null,
          parentId: null,
          ids: null,
          code: null,
          companyId: null
        },
        projectStatus: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        projectType: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        projectManager: {
          id: null,
          name: null
        },
        planBeginDate: '', // 计划日期
        planEndDate: '', // 计划日期
        realBeginDate: '', // 实际开始日期
        realEndDate: '', // 实际结束日期
        projectBudget: null, // 项目总预算
        projectBudgetPersonMonth: null, // 项目计划投入总人月
        planLaborCost: null, // 计划投入人力成本
        completeDesc: '', // 完成情况
        completeAnnex: '', //项目完结附件
        completeAnnexFile:  {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        }
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openDialog', async function (id) {
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
<style lang="scss" scoped>
.dc-divide {
  width: 100%;
  background-color: #ecf5ff;
  padding: 5px 10px;
  margin: 10px 0;
  border-radius: 4px;
  div {
    border-left: 4px #409eff solid;
    padding-left: 6px;
  }
}
</style>
