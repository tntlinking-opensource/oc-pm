<style scoped>
.flex {
  display: flex;
}
.flex-cloumn {
  flex-direction: column;
}
.circle {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #018CB7;
  font-size: 12px;
  color: #fff;
  line-height: 20px;
  text-align: center;
}
.center {
  align-items: center;
}
.size-12 {
  font-size: 12px;
}
.size-14 {
  font-size: 14px;
}
.jus-center {
  justify-content: center;
}
</style>
<template>
  <el-row class="dc-container" v-loading="loading">
    <el-form
      ref="editForm"
      :model="editFormData"
      label-width="100px"
      :disabled="action == 'view'"
      class="dc-el-form_ElEditForm"
    >
      <el-row>
        <el-col :span="24">
          <el-form-item
            prop="reportingTheme"
            label="汇报主题"
            :rules="[{ required: true, message: '汇报主题不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.reportingTheme"
              :maxLength="300"
              placeholder="请输入汇报主题"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item
            prop="project.id"
            label="项目"
            :rules="[{ required: true, message: '项目不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.project.id"
              :style="{ width: '100%' }"
              placeholder="请选择项目"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
              @change="handleChangePro"
            >
              <el-option v-for="(item, index) in projectOptions" :key="item.id" :label="item.projectName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="project.project.projectCode"
            label="项目编号"
            class="dc-el-form-item_SelectInput"
          >
            <el-input v-model="editFormData.project.projectCode" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="milestone.id"
            label="里程碑"
            :rules="[{ required: true, message: '里程碑不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.milestone.id"
              :style="{ width: '100%' }"
              placeholder="请选择里程碑"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
            <el-option v-for="(item, index) in milestoneOptions" :key="item.milestoneId" :label="item.milestoneName" :value="item.milestoneId"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="project.totalRate"
            label="项目进度"
            class="dc-el-form-item_SelectInput"
          >
            <div class="flex center">
              <el-input v-model="editFormData.totalRate" disabled></el-input>%
              <el-tooltip class="item" effect="dark" content="项目进度=各里程碑进度（里程碑进度*进度占比）之和" placement="top-start">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </div>
          </el-form-item>
        </el-col>

        <!-- <el-col :span="12">
          <el-form-item
            prop="milestoneProgress"
            label="当前汇报里程碑进度"
            :rules="[{ required: true, message: '当前汇报里程碑进度不能为空', trigger: 'blur' }, { type: 'number', min: 1, max: 100, message: '进度必须在1到100之间', trigger: 'blur' }]"
            class="dc-el-form-item_NumberInput"
          >
            <number-input
              v-model="editFormData.milestoneProgress"
              :min="0"
              :max="100"
              placeholder="请输入当前汇报里程碑进度"
              clearable
              class="dc-number-input_NumberInput"
            ></number-input>
          </el-form-item>
        </el-col> -->

        <el-col :span="24" v-if="milestoneOptions.length" class="flex jus-center">
            <div class="flex center">
              <div>
                <div class="size-12">进度:</div>
                <div class="circle"></div>
                <div class="size-14">开始</div>
              </div>
              <div class="flex center" v-for="(item, index) in milestoneOptions">
                <el-progress :percentage="progressForm[item.milestoneId]" :text-inside="true" :stroke-width="12" :format="format" style="width: 100px;"></el-progress>
                <div class="flex flex-cloumn center">
                  <div>
                    <el-input-number v-model="progressForm[item.milestoneId]" controls-position="right" @change="handleChangeMile" style="width: 100px !important" :min="1" :max="100"></el-input-number>
                    %
                  </div>
                  <div class="circle">{{ index + 1 }}</div>
                  <div class="size-14">{{ item.milestoneName }}</div>
                  <div class="size-12">进度占比: {{ item.progressRate }}%</div>
                </div>
              </div>
            </div>
            
        </el-col>
          
        
        <el-col :span="24">
          <el-form-item
            prop="progressDesc"
            label="进度描述"
            :rules="[{ required: true, message: '进度描述不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.progressDesc"
              :maxLength="300"
              placeholder="请输入进度描述"
              clearable
              type="textarea"
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item prop="issuesAndReasons" label="存在的问题以及原因" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.issuesAndReasons"
              :maxLength="300"
              placeholder="请输入存在的问题以及原因"
              clearable
              type="textarea"
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item prop="solution" label="问题解决方案" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.solution"
              :maxLength="300"
              placeholder="请输入问题解决方案"
              clearable
              type="textarea"
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            prop="nextPhasePlan"
            label="下一阶段工作计划"
            :rules="[{ required: true, message: '下一阶段工作计划不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.nextPhasePlan"
              :maxLength="300"
              placeholder="请输入下一阶段工作计划"
              clearable
              type="textarea"
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>

      </el-row>
    </el-form>
  </el-row>
</template>
<script>
import BizForm from '@/views/wf/common/bizForm'
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { listProjectListAll } from '@/api/projectManage/projectList.js'
/** 根据用户界面配置import组件 结束 */
import {
  getProgressReportById,
  createProgressReport,
  reapplyProgressReport,
  getProgressReportByTaskId,
  agreeProgressReport,
  forwardProgressReport,
  commissionProgressReport,
  proposeProgressReport,
  backProgressReport,
  rejectProgressReport,
  terminateProgressReport,
  reverseProgressReport,
  listBackActivity,
  saveDraftProgressReport,
  getMilestoneByProjectId,
  getProjectProgressByProjectId
} from '@/api/progress/progressReport'
import OperationIcon from '@/components/OperationIcon'
import _ from 'underscore'
export default {
  extends: BizForm,
  name: 'progressReport-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      dialogProps: {
        visible: false,
        title: ''
      },
      progressForm: {},

      // 选项变量
      // 项目id选项
      projectOptions: [],
      // 里程碑id选项
      milestoneOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

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
    handleChangeMile () {
      let total = 0
      console.log(this.milestoneOptions);
      _.each(this.milestoneOptions, item => {
        const rate = this.progressForm[item.milestoneId] || 0
        total += (item.progressRate * rate)/100
      })
      this.editFormData = {
        ...this.editFormData,
        totalRate: +(total.toFixed(2))
      }
      this.handleProgress()
    },
    handleChangePro () {
      const current = _.find(this.projectOptions, item => item.id === this.editFormData.project.id) || {}
      this.editFormData = {
        ...this.editFormData,
        project: {
          ...this.editFormData.project,
          projectCode: current.projectCode || ''
        }
      }
      this.handleProjectChange(this.editFormData.project.id)
    },
    handleProgress () {
      let res = []
      _.mapObject(this.progressForm, (val, key) => {
        const v = val || 0
        res.push({milestoneId: key, milestoneProgress: val})
      })
      this.editFormData = {
        ...this.editFormData,
        milestoneProgress: JSON.stringify(res)
      }
    },
    loadModelData(taskId) {
      this.setLoad()
      getProgressReportByTaskId(taskId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.editFormData = {
              ...this.initEditData(),
              ...responseData.data
            }
            this.handleProjectChange(this.editFormData.project.id)
            const progressForm = JSON.parse(this.editFormData.milestoneProgress)
            let form = {}
            _.each(progressForm, item => {
              form[item.milestoneId] = item.milestoneProgress
            })
            this.progressForm = form;
            this.$emit('after-load-data', this.editFormData) // 表单数据对象传给父节点
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 保存草稿，files附件列表, draftModel 草稿对象
    doSaveDraft(draftModel, files) {
      if (!draftModel) {
        // 不是从草稿打开创建，保存时更新原草稿
        draftModel = {
          procdefId: this.procDef.id,
          procdefName: this.procDef.name,
          name: this.procDef.name + '-' + this.editFormData.name
        }
      }
      this.handleProgress()
      this.setLoad()
      let formData = this.createWfData(this.editFormData, files)
      formData.append('strDraft', JSON.stringify(draftModel))
      saveDraftProgressReport(formData)
        .then((responseData) => {
          this.handleResponse(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 创建一个新流程 process流程 actNodes是提交的用户任务节点及其审批用户
    doCreate(files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.handleProgress()
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          let formData = this.createWfData(this.editFormData, files)
          createProgressReport(this.procDef.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 重新发起申请 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doReApply(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.handleProgress()
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          reapplyProgressReport(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 同意 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doAgree(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.handleProgress()
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          agreeProgressReport(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 转派任务
    doForward(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.handleProgress()
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          forwardProgressReport(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 委派任务
    doCommission(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.handleProgress()
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          commissionProgressReport(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 拟办（办理委派过来的任务）
    doPropose(comment, files) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.handleProgress()
          this.setLoad()
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          proposeProgressReport(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 拒绝
    doReject(comment, files) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          rejectProgressReport(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 退回 comment 意见, actNodes退回节点和用户信息
    doBack(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          backProgressReport(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 终止流程 comment 意见, actNodes退回节点和用户信息
    doTerminate(comment, files) {
      this.setLoad()
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      terminateProgressReport(this.task.id, formData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.$emit('save-finished')
          }
          this.showMessage(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 撤回任务
    doReverse(task) {
      this.setLoad()
      reverseProgressReport(task.id)
        .then((responseData) => {
          this.handleResponse(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    doListActivity() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          listBackActivity(this.task.id)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // handleProjectChange (projectId) {
      // const current = this.milestoneOptions.findIndex(item => item.id === this.editFormData.milestone.id)
      // const res = this.milestoneOptions[current]
      // console.log(res.id);
      // this.listMilestoneOptions(projectId)
      // this.editFormData = {
      //   ...this.editFormData
      // }
    // },
    listProjectOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])
      search_List.params.push({
          "columnName": "project_status",
          "queryType": "in",
          "value": ["2","3"]
        })
      // 数据权限: 项目清单project_list
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937934')

      listProjectListAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.projectOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    handleProjectChange(projectId) {
      // 数据权限: 里程碑管理milestone_manage
      getProjectProgressByProjectId(projectId).then((responseData) => {
        if (responseData.code == 100) {
          this.milestoneOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listProjectOptions()
    },
    initEditData(This) {
      return {
        reportingTheme: null, // 汇报主题
        project: {
          id: null,
          projectName: null,
          projectCode: null
        },
        milestone: {
          id: null,
          milestoneName: null,
          milestoneStatus: null,
          progressRate: null
        },
        milestoneProgress: null, // 当前汇报里程碑进度
        progressDesc: '', // 进度描述
        issuesAndReasons: '', // 存在的问题以及原因
        solution: '', // 问题解决方案
        nextPhasePlan: '', // 下一阶段工作计划
        annex: '', // 附件
        progressStatus: null, // 汇报状态
        remarks: '' // 备注信息
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.dialogProps.visible = true
      this.initOptions()
      this.tabIndex = '1'
      console.log(this.wfAction);
      if (this.wfAction == 'create') {
        // this.$refs['editForm'] && this.$refs['editForm'].resetFields()
        // 从草稿创建
        if (this.bizData) {
          this.editFormData = {
            ...this.initEditData(),
            ...this.bizData
          }
          this.handleProjectChange(this.editFormData.project.id)
          const progressForm = JSON.parse(this.editFormData.milestoneProgress)
          let form = {}
          _.each(progressForm, item => {
            form[item.milestoneId] = item.milestoneProgress
          })
          this.progressForm = form;
        }
      } else if (this.wfAction == 'approve') {
        // 重新申请
        let reApply =
          this.task.assignee == this.task.owner &&
          this.task.assignee == this.task.extInfo.applicantId &&
          this.task.taskDefinitionKey == this.task.extInfo.appActId
        if (!reApply) {
          this.action = 'view'
        }
        this.loadModelData(this.task.id)
      } else if (this.wfAction == 'viewBiz') {
        this.action = 'view'
        this.editFormData = {
          ...this.initEditData(),
          ...this.bizData
        }
        this.handleProjectChange(this.editFormData.project.id)
          const progressForm = JSON.parse(this.editFormData.milestoneProgress)
          let form = {}
          _.each(progressForm, item => {
            form[item.milestoneId] = item.milestoneProgress
          })
          this.progressForm = form;
      } else if (this.wfAction == 'viewTask') {
        this.action = 'view'
        this.loadModelData(this.task.id)
      } else {
        console.err('=============== 业务审批单中，实现的：' + this.wfAction)
      }
    })
  }
}
</script>
