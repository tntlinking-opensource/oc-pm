<template>
  <el-row class="dc-container">
    <el-dialog v-on:open="onDialogOpen" v-on:close="onDialogClose" v-loading="loading" width="70%" :fullscreen="false"
      :title="dialogProps.title" :visible.sync="dialogProps.visible" :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog">
      <el-row>
        <el-form ref="editForm" :model="editFormData" label-width="110px" :disabled="action == 'view'"
          class="dc-el-form_ElEditForm">
          <el-row>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '项目名称不能为空', trigger: 'change' }]" label="项目名称："
                prop="projectId" class="dc-el-form-item_SelectInput">
                <el-select clearable v-model="editFormData.projectId" style="width:100%" @change="changeProject">
                  <el-option v-for="item in projectOptions" :key="item.key" :value="item.key"
                    :label="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目编号：" class="dc-el-form-item_SelectInput">
                <el-input v-model="editFormData.project.projectCode" type="text" class="dc-el-input_MutilpleInput"
                  disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '风险不能为空', trigger: 'blur' }]" label="风险名称："
                prop="riskName" class="dc-el-form-item_SelectInput">
                <el-input v-model="editFormData.riskName" type="text" class="dc-el-input_MutilpleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="风险编号：" class="dc-el-form-item_SelectInput">
                <el-input v-model="editFormData.riskCode" type="text" class="dc-el-input_MutilpleInput"
                  disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '所属里程碑不能为空', trigger: 'change' }]" label="所属里程碑："
                prop="milestoneId" class="dc-el-form-item_SelectInput">
                <el-select clearable v-model="editFormData.milestoneId" style="width:100%" @change="changeMile">
                  <el-option v-for="item in milestoneOptions" :key="item.key" :value="item.key"
                    :label="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="所属日程：" class="dc-el-form-item_MutilpleInput">
                <el-select clearable v-model="editFormData.scheduleId" style="width:100%">
                  <el-option v-for="item in scheduleOptions" :key="item.key" :value="item.key"
                    :label="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '风险类型不能为空', trigger: 'change' }]" label="风险类型："
                prop="riskType" class="dc-el-form-item_SelectInput">
                <el-select clearable v-model="editFormData.riskType" style="width:100%">
                  <el-option v-for="item in typeOptions" :key="item.value" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '风险概率不能为空', trigger: 'change' }]" label="风险概率："
                prop="riskImpact" class="dc-el-form-item_SelectInput">
                <el-select clearable v-model="editFormData.riskImpact" style="width:100%">
                  <el-option v-for="item in impactOptions" :key="item.value" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '风险状态不能为空', trigger: 'change' }]" label="风险状态："
                prop="riskStatus" class="dc-el-form-item_SelectInput">
                <el-select clearable v-model="editFormData.riskStatus" style="width:100%">
                  <el-option v-for="item in statusOptions" :key="item.value" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item :rules="[{ required: true, message: '风险内容不能为空', trigger: 'change' }]" label="风险内容："
                prop="riskText" class="dc-el-form-item_MutilpleInput">
                <el-input v-model="editFormData.riskText" placeholder="请输入风险内容" type="textarea"
                  class="dc-el-input_MutilpleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="应对方案：" class="dc-el-form-item_MutilpleInput">
                <el-input v-model="editFormData.riskOption" placeholder="请输入应对方案" type="textarea"
                  class="dc-el-input_MutilpleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="附件：">
                <upload-file title="文件上传" :action="action" :objectId="editFormData.id" :refresh="filerefresh"
                  ref="uploadFile" :fileFormats="['jpg', 'jpeg', 'png', 'doc', 'docx', 'xls', 'xlsx', 'pdf', 'txt']"
                  v-model="editFormData.attachmentsFile" class="dc-upload-file_ElUploadFile"></upload-file>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">保存</el-button>
        <el-button v-on:click="onDialogClose">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>

import {
  listMilestoneManage,
  listScheduleManage,
  getRiskCode,
  saveWithFile,
  listProjectList
} from '@/api/risk/riskManage'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import UploadFile from '@/views/components/uploadFile'
import { getDictTypeById } from '@/api/sys/dictType'
export default {
  extends: BaseUI,
  name: 'riskManage-form',
  components: {
    OperationIcon,
    UploadFile
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '风险'
      },
      filerefresh: new Date(),
      // 窗口操作类型 view/edit/add
      action: '',
      codeOptions: [],
      statusOptions: [],
      typeOptions: [],
      impactOptions: [],
      milestoneOptions: [],
      scheduleOptions: [],
      projectOptions: []
    }
  },

  methods: {
    createFormData(editFormData) {
      let formData = new FormData()
      let deletes = []
      for (let idx in editFormData.attachmentsFile.uploads) {
        formData.append('attachments', editFormData.attachmentsFile.uploads[idx].raw)
      }
      deletes = deletes.concat(editFormData.attachmentsFile.deletes)
      formData.append('strEntity', JSON.stringify(editFormData))
      formData.append('deleteIds', JSON.stringify(deletes))
      return formData
    },

    onSubmit() {
      this.$refs['editForm'].validate(valid => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      });
    },
    doSave() {
      this.setLoad()
      const params = {
        ...this.editFormData,
      }
      saveWithFile(this.createFormData(params))
        .then(responseData => {
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
        }).catch(error => {
          this.outputError(error)
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
    changeProject(val) {
      this.editFormData.scheduleId = ''
      this.editFormData.milestoneId = ''
      if (val) {
        let obj = this.projectOptions.find(item => item.key === val)
        this.editFormData.project.id = obj.key
        this.editFormData.project.projectName = obj.value
        this.getRiskCodeData(val)
        this.getMilestone()
      }
    },
    changeMile(val) {
      this.editFormData.scheduleId = ''
      if (val) {
        this.getSchedule()
      }
    },
    getRiskCodeData(val) {
      getRiskCode(val).then(res => {
        if (res.code == '100') {
          this.editFormData.project.projectCode = res.data.projectCode
          this.editFormData.riskCode = res.data.riskCode
        }
      })
    },

    initEditData(This) {
      return {
        milestoneId: "",
        project: {
          id: "",
          memberCount: null,
          planBeginDate: null,
          planEndDate: null,
          projectCode: "",
          projectManager: null,
          projectName: "",
          projectShortName: "",
          projectStatus: null
        },
        attachmentsFile: {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        },
        projectId: "",
        riskCode: "",
        riskImpact: "",
        riskName: "",
        riskStatus: "",
        riskText: "",
        riskType: "",
        scheduleId: ""
      }
    },
    initData(type) {
      this.$nextTick(() => {
        this.$refs.uploadFile.initOption()
      })
      if (type === 'add') {
        this.dialogProps.title = '新增风险'
      } else {
        this.dialogProps.title = '编辑风险'
      }
      this.action = type
      this.dialogProps.visible = true
      this.getStatus()
      this.getType()
      this.getImpact()
    },
    getStatus() {
      getDictTypeById('1849363886312079400').then(res => {
        if (res.code === '100') {
          this.statusOptions = res.data.dictItemList
        }
      })
    },
    getType() {
      getDictTypeById('1849363886312079390').then(res => {
        if (res.code === '100') {
          this.typeOptions = res.data.dictItemList
        }
      })
    },
    getImpact() {
      getDictTypeById('1849363886312079395').then(res => {
        if (res.code === '100') {
          this.impactOptions = res.data.dictItemList
        }
      })
    },
    getMilestone() {
      let params = {
        "params": [
          {
            "columnName": "project_id",
            "queryType": "=",
            "value": this.editFormData.projectId
          }
        ]
      }
      listMilestoneManage(params).then(res => {
        if (res.code == '100') {
          this.milestoneOptions = res.data
        }
      })
    },
    getSchedule() {
      let params = {
        "params": [
          {
            "columnName": "milestone_id",
            "queryType": "=",
            "value": this.editFormData.milestoneId
          }
        ]
      }
      listScheduleManage(params).then(res => {
        if (res.code == '100') {
          this.scheduleOptions = res.data
        }
      })
    },
    getProject() {
      let params = {
        params: [{ columnName: "project_status", queryType: "in", value: [2, 3] }]
      }
      listProjectList(params).then(res => {
        if (res.code == '100') {
          this.projectOptions = res.data
        }
      })
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.$on('openEditDialog', async function (row) {
        this.initData('edit')
        let obj = JSON.parse(JSON.stringify(row))
        obj.attachmentsFile = {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        }
        delete obj.attachmentFile
        this.editFormData = obj
        this.getProject()
      })
      this.$on('openAddDialog', function (row) {
        this.editFormData = this.initEditData()
        this.initData('add')
        this.getProject()

      })
    })
  }
}
</script>
