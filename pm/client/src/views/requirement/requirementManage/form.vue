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
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="150px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item
                prop="project.id"
                label="项目名称："
                class="dc-el-form-item_SelectInput"
              >
                <el-input
                  v-model="editFormData.projectName"
                  type="text"
                  placeholder="请输入"
                  disabled
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="project.code"
                label="项目编号："
                class="dc-el-form-item_SelectInput"
              >
                <el-input
                  v-model="editFormData.projectCode"
                  type="text"
                  disabled
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '需求名称不能为空', trigger: 'change' }]"
                prop="requirementName" label="需求名称：" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.requirementName"
                  placeholder="请输入需求名称"
                  type="textarea"
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col> 
          
            
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '需求提出人不能为空', trigger: 'change' }]"
                prop="proposer" label="提出人：" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.proposer"
                  placeholder="请输入需求提出人"
                  type="textarea"
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item
                prop="milestone.milestoneName"
                label="所属里程碑："
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-select
                  v-model="editFormData.milestone.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                  @change="handleMilestoneChange(editFormData.milestone.id)"
                >
                  <el-option v-for="(item, index) in milestoneOptions" :key="item.id" :label="item.milestoneName" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item
                prop="schedule.milestoneName"
                label="所属日程："
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-select
                  v-model="editFormData.schedule.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                  @change="handleScheduleChange"
                >
                  <el-option v-for="(item, index) in scheduleOptions" :key="item.id" :label="item.scheduleName" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>


            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '提出日期不能为空', trigger: 'change' }]"
                prop="submissionDate" label="提出日期：" class="dc-el-form-item_MutilpleInput">
                <el-date-picker
                  v-model="editFormData.submissionDate"
                  :style="{ width: '100%' }"
                  placeholder="请选择提出日期"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item :rules="[{ required: true, message: '需求描述不能为空', trigger: 'change' }]"
                prop="requirementDesc" label="需求描述：" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.requirementDesc"
                  type="textarea"
                  placeholder="请输入需求描述"
                  rows="3"
                  :maxLength="255"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
               <el-form-item prop="attachments" label="附件：">
                   <upload-file
                     title="文件上传"
                     :action="action"
                     :objectId="editFormData.id"
                     :refresh="filerefresh"
                    ref="uploadFile"
                     :fileFormats="['jpg', 'jpeg', 'png', 'doc', 'docx', 'xls', 'xlsx', 'pdf', 'txt']"
                     v-model="editFormData.attachmentsFile"
                     class="dc-upload-file_ElUploadFile"
                   ></upload-file>
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
/** 根据用户界面配置import组件 结束 */

import {listDictItemAll} from '@/api/sys/dictItem'
import {
  getRequirementManageById,
  saveRequirementManageWithFile,
  listMilestoneManage,
  listScheduleManage
} from '@/api/requirement/requirementManage'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import UploadFile from '@/views/components/uploadFile'

export default {
  extends: BaseUI,
  name: 'requirementManage-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

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
        title: '需求'
      },
      filerefresh: new Date(),
      milestoneData: [],
      scheduleData: [],
      dialogTitle: '需求',
      // 选项变量
      // 关联项目id选项
      projectOptions: [],
      // 关联用户id选项
      milestoneOptions: [],
      // 团队角色id选项
      scheduleOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: ''
    }
  },
  computed: {
    startPickerOptions() {
      return {
        disabledDate: time => {
          // 在结束日期选择器中选择的日期之后的日期将被禁用
          if (this.editFormData.leaveTime) {
            return time.getTime() > new Date(this.editFormData.leaveTime).getTime();
          }
          return false;
        }
      };
    },
    endPickerOptions() {
      return {
        disabledDate: time => {
          // 在开始日期选择器中选择的日期之前的日期将被禁用
          if (this.editFormData.joinTime) {
            return time.getTime() < new Date(this.editFormData.joinTime).getTime();
          }
          return false;
        }
      };
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  methods: {
    handleMilestoneChange (milestonId) {
      // const current = this.milestoneOptions.findIndex(item => item.id === this.editFormData.milestone.id)
      // const res = this.milestoneOptions[current]
      // console.log(res.id);
      this.listScheduleOptions(milestonId)
      this.editFormData = {
        ...this.editFormData
      }
    },
    handleScheduleChange () {
      const current = this.scheduleData.findIndex(item => item.id === this.editFormData.schedule.id)
      // const res = this.scheduleData[current]
      this.editFormData = {
        ...this.editFormData

      }
    },
    createFormData(editFormData) {
      let formData = new FormData()
      let deletes = []
      for (let idx in editFormData.attachmentsFile.uploads) {
        console.log("--------",editFormData.attachmentsFile.uploads[idx])
        formData.append('attachments', editFormData.attachmentsFile.uploads[idx].raw)
      }

      deletes = deletes.concat(editFormData.attachmentsFile.deletes)
      formData.append('strEntity', JSON.stringify(editFormData))
      formData.append('deleteIds', JSON.stringify(deletes))
      return formData
    },
    handleDatas () {
      listMilestoneManage({
        params: [
          {
            "columnName": "project_id",
            "queryType": "=",
            "value": this.editFormData.project.id
          }
        ]
      }).then((responseData) => {
          if (responseData.code == 100) {
            console.log(responseData);
            this.milestoneData = responseData.data
          }
      })
      listDictItemAll({
        params: [
          {
            "columnName": "dictType.code",
            "queryType": "=",
            "value": `teamRole`
          }
        ]
      }).then((responseData) => {
        if (responseData.code == 100) {
          this.roleData = responseData.data
        }
      })
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
      saveRequirementManageWithFile(this.createFormData(params))
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
    switchEdit() {
      this.action = 'edit'
      this.dialogProps.title = `修改${this.dialogTitle}`
      this.initOptions(this.editFormData)
    },
    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getRequirementManageById(id).then((responseData) => {
          let form = {}
          if (responseData.code == 100) {
            form = {
              ...responseData,
              ...responseData.project,
            }
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
    listProjectOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])
      // 数据权限: 项目清单project_list
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1740772218121937934")
      listProjectListAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.projectOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },
    listMilestoneOptions() {

      let search_List = {
        params: [
          {
            "columnName": "project_id",
            "queryType": "=",
            "value": this.editFormData.project.id
          }
        ]
      }
      
      listMilestoneManage(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.milestoneOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },
    listScheduleOptions(milestoneId) {
      let search_List = {
        params: [
        {
            "columnName": "milestone_id",
            "queryType": "=",
            "value": milestoneId
          }
        ]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 字典项sys_dict_item
      // this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1740772218121937856")


      listScheduleManage(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.scheduleOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      // this.listProjectOptions()
      this.listMilestoneOptions()
      this.$nextTick(() => {
        this.$refs.uploadFile.initOption()
      })
      // this.listScheduleOptions()
    },
    initEditData(This) {
      return {
        project: {
          id: null,
          projectName: null,
          projectShortName: null,
          projectCode: null,
          projectStatus: null,
        },
        milestone: {
          id: null,
          milestoneName: null,
        },
        schedule: {
          id: null,
          scheduleName: null,
        },

        requirementName: '', // 需求名称
        proposer: '', // 需求提出人
        submissionDate: '', // 加入项目时间
        requirementDesc: '', // 离开项目时间
        attachmentsFile:{
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        }

      }
    }
  },
  watch: {

  },
  mounted () {
    this.$nextTick(() => {
      // this.handleDatas();
      this.$on('openViewDialog', async function(id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...await this.getFormById(id)
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function(row) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...row,
          ...row.project,
          id: row.id
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function(row) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...row,
          id: null,
          project: {
            id: row.id
          }
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openCopyDialog', async function(id) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...await this.getFormById(id)
        }
        this.initOptions(this.editFormData)
        this.editFormData.id = null //把id设置为空，添加一个新的
        this.dialogProps.visible = true
      })

    })
  }
}
</script>
