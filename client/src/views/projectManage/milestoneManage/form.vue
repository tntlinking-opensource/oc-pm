<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="730px"
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
              <el-form-item label="项目名称" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.project.projectName"
                  disabled
                  :maxLength="50"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="projectCode" label="项目编号" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.project.projectCode"
                  disabled
                  :maxLength="50"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label-width="30px" class="dc-el-form-item_RadioGroup">
                <el-radio-group v-model="editFormData.inputType" class="dc-el-radio-group_RadioGroup">
                  <el-radio v-for="(item, index) in radioGroupOptions" :key="index" :label="item.value">
                    {{ item.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12" v-if="editFormData.inputType === '2'">
              <el-form-item label-width="10px" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="selectMilestoneTemplateId"
                  :style="{ width: '100%' }"
                  filterable
                  class="dc-el-select_SelectInput"
                  @change="handleMilestoneTemplateChange"
                >
                  <el-option
                    v-for="(item, index) in milestoneTemplates"
                    :key="index"
                    :label="item.templateName"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="editFormData.inputType === '1'">
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
            <el-col :span="12" style="display: flex">
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
                  :precision="0"
                  placeholder="请输入进度占比"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
              <span style="margin-left: 5px">%</span>
            </el-col>
            <el-col :span="24">
              <el-form-item
                prop="planBegin"
                label="计划日期"
                :rules="[{ required: true, message: '计划日期不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDateRange"
              >
                <el-date-picker
                  :value="[editFormData.planBegin, editFormData.planEnd]"
                  v-on:input="
                    (val) => {
                      editFormData.planBegin = val ? val[0] : ''
                      editFormData.planEnd = val ? val[1] : ''
                    }
                  "
                  :style="{ width: '100%' }"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="计划开始日期"
                  end-placeholder="计划结束日期"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDateRange"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="sort" label="顺序" class="dc-el-form-item_CounterInput" :rules="[{ required: true, message: '顺序不能为空', trigger: 'change', type: 'number' }]">
                <el-input-number
                  v-model="editFormData.sort"
                  :min="0"
                  :max="999"
                  :step="1"
                  :precision="0"
                  :style="{ width: '100%' }"
                  class="dc-el-input-number_CounterInput"
                ></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="participants" label="参与人员" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.participants"
                  :style="{ width: '100%' }"
                  multiple
                  placeholder="请选择参与人员"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in selectOptions"
                    :key="index"
                    :label="item.user.name"
                    :value="item.user.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="milestoneDesc" label="里程碑描述" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.milestoneDesc"
                  type="textarea"
                  placeholder="请输入"
                  rows="2"
                  :maxLength="-1"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="附件：">
                <upload-file
                  title="文件上传"
                  ref="uploadFile"
                  :action="action"
                  :objectId="editFormData.annex"
                  :refresh="filerefresh"
                  :fileFormats="['jpg', 'jpeg', 'png', 'doc', 'docx', 'xls', 'xlsx', 'pdf', 'txt']"
                  :fileSizes="10"
                  v-model="editFormData.annexFile"
                  class="dc-upload-file_ElUploadFile"
                ></upload-file>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-else-if="editFormData.inputType === '2'">
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column type="index" width="50" label="序号"></el-table-column>
              <el-table-column property="milestoneName" label="里程碑名称" min-width="120"></el-table-column>
              <el-table-column property="milestoneDesc" label="里程碑描述" min-width="120"></el-table-column>
              <el-table-column label="计划日期" min-width="300">
                <template slot-scope="scope">
                  <el-date-picker
                    v-model="planDate[`index_${scope.$index}`]"
                    :style="{ width: '100%' }"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="计划开始日期"
                    end-placeholder="计划结束日期"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    class="dc-el-date-picker_PickerTimeDateRange"
                  ></el-date-picker>
                </template>
              </el-table-column>
              <el-table-column property="progressRate" label="进度占比" min-width="120"></el-table-column>
              <el-table-column property="sort" label="顺序" min-width="120"></el-table-column>
            </el-table>
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
import uploadFile from '@/views/components/uploadFile'
/** 根据用户界面配置import组件 结束 */
import { getMilestoneManageById, saveMilestoneManage } from '@/api/projectManage/milestoneManage'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import {listTeamMemberAll} from "@/api/team/teamMember";
import {listMilestoneTemplateAll} from "@/api/projectManage/milestoneTemplate";
import {listMilestoneTemplateItemAll} from "@/api/projectManage/milestoneTemplateItem";
import {bulkInsertMilestoneManage, checkProgressRate} from "@/api/projectManage/milestoneManage";
export default {
  extends: BaseUI,
  name: 'milestoneManage-form',
  components: {
    /** 根据用户界面配置组件 开始 */
    uploadFile,
    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      selectMilestoneTemplateId: null,
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '里程碑管理'
      },
      dialogTitle: '里程碑管理',
      // 选项变量
      // 选项
      radioGroupOptions: [
        {
          label: '手动输入里程碑信息',
          value: '1'
        },
        {
          label: '使用里程碑模板',
          value: '2'
        }
      ],
      tableData: [],
      planDate: {},
      // 参与人员选项
      selectOptions: [],
      // 里程碑模板
      milestoneTemplates: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      filerefresh: new Date()
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    },
    project: {
      type: Object,
      required: true
    }
  },
  computed: {},
  methods: {
    handleMilestoneTemplateChange(id) {
      this.loadMilestoneTemplateItemsByTemplateId(id)
    },
    loadMilestoneTemplateItemsByTemplateId(templateId) {
      let params = {
        params: [{
          'columnName': 'template_id',
          'queryType': '=',
          'value': templateId
        }]
      }
      listMilestoneTemplateItemAll(params).then(responseData => {
        if (responseData.code == 100) {
          this.tableData = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
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
    async doSave() {
      this.setLoad()
      // 使用里程碑模板
      if (this.editFormData.inputType === '2') {
        let mms = this.tableData.map((item, index) => {
          let planDate = this.planDate[`index_${index}`]
          return {
            project: this.editFormData.project,
            milestoneStatus: this.editFormData.milestoneStatus,
            milestoneName: item.milestoneName,
            progressRate: item.progressRate,
            planBegin: planDate ? planDate[0] : null,
            planEnd: planDate ? planDate[1] : null,
            sort: item.sort,
            milestoneDesc: item.milestoneDesc,
          }
        })
        // 批量保存里程碑数据
        bulkInsertMilestoneManage(mms).then((responseData) => {
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
        return
      }
      // 判断当前项目下的里程碑的进度占比之和是否超过100%
      let params = {
        id: this.project.id,
        rate: this.editFormData.progressRate,
        excludeId: this.editFormData.id || null
      }
      let res = await checkProgressRate(params)
      if (res.code != 100 || !res.data) {
        this.$alert('该项目下的里程碑进度占比之和超过100%！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        this.resetLoad()
        return
      }
      // 处理参与人员数据
      let annexFile = this.editFormData.annexFile
      let editFormData = JSON.parse(JSON.stringify(this.editFormData))
      let participants = editFormData.participants
      if (participants && participants.length > 0) {
        editFormData.participants = participants.join(',')
      } else {
        editFormData.participants = ''
      }
      editFormData.annexFile = annexFile
      let formData = this.createFormData(editFormData)
      saveMilestoneManage(formData)
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

    createFormData(editFormData) {
      let formData = new FormData()
      let deletes = []

      for (let idx in editFormData.annexFile.uploads) {
        formData.append('annexUploads', editFormData.annexFile.uploads[idx].raw)
      }
      deletes = deletes.concat(editFormData.annexFile.deletes)

      formData.append('entity', JSON.stringify(editFormData))
      formData.append('deleteIds', JSON.stringify(deletes))
      return formData
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getMilestoneManageById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
              // 处理参与人
              form.participants = form.participantIds ? form.participantIds.split(',') : []
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
    initTeamMemberOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{
        'columnName': 'project_id',
        'queryType': '=',
        'value': this.project.id
      }])
      listTeamMemberAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.selectOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initMilestoneTemplate() {
      let params = {
        params: [{
          'columnName': 'status',
          'queryType': '=',
          'value': '1849492941489430544'
        }]
      }
      listMilestoneTemplateAll(params).then(responseData => {
        if (responseData.code == 100) {
          this.milestoneTemplates = responseData.data
          // 默认选中首个模板
          if (this.milestoneTemplates.length > 0) {
            this.selectMilestoneTemplateId = this.milestoneTemplates[0].id
            // 加载模板详情
            this.loadMilestoneTemplateItemsByTemplateId(this.selectMilestoneTemplateId)
          }
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      this.initTeamMemberOptions()
      this.initMilestoneTemplate()
      this.$nextTick(() => {
        console.log(123131)
        this.$refs.uploadFile.initOption()
      })
    },
    initEditData(This) {
      return {
        inputType: '1',
        project: {
          id: this.project.id,
          projectName: this.project.projectName, // 项目名称
          projectCode: this.project.projectCode, // 项目编号
        },
        // 里程碑状态
        milestoneStatus: {
          id: '1849492941489430558'
        },
        milestoneName: '', // 里程碑名称
        progressRate: null, // 进度占比
        planBegin: '', // 计划日期
        planEnd: '', // 计划日期
        sort: null, // 顺序
        participants: [], // 参与人员
        milestoneDesc: '', // 描述
        annex: null,
        annexFile: {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        }
      }
    }
  },
  watch: {
    'editFormData.annexFile'(nv, ov) {
      console.log(nv)
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
      this.$on('openAddDialog', function (parent) {
        this.action = 'add'
        console.log(this)
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...parent
        }
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

        this.editFormData.annex = null

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
