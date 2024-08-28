<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="65%"
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
                prop="project.id"
                label="项目名称"
                :rules="[{ required: true, message: '项目名称不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.project"
                  :style="{ width: '100%' }"
                  placeholder="请选择项目名称"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option v-for="(item, index) in projectOptions" :key="index" :label="item.projectName"
                             :value="item"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="" label="项目编号" class="dc-el-form-item_SingleInput">
                <el-input
                  disabled
                  v-model="editFormData.project.projectCode"
                  :maxLength="-1"
                  placeholder="请输入项目编号"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="reportType.id"
                label="报告类型"
                :rules="[{ required: true, message: '报告类型不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.reportType"
                  @change="reportTypeChange"
                  :style="{ width: '100%' }"
                  placeholder="请选择报告类型"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in reportTypeOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="overtimeType.id" label="加班类型" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.overtimeType"
                  @change="handleOvertimeHour"
                  :disabled="!isDayReport"
                  :style="{ width: '100%' }"
                  placeholder="请选择加班类型"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in overtimeTypeOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24" style="display: flex">
              <el-form-item
                prop="reportBegin"
                style="width: calc(100% - 50px)"
                label="报告时间"
                :rules="[{ required: true, message: '报告时间不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDateRange"
              >
                <el-date-picker
                  :value="[editFormData.reportBegin, editFormData.reportEnd]"
                  v-on:input="
                    (val) => {
                      editFormData.reportBegin = val ? val[0] : ''
                      editFormData.reportEnd = val ? val[1] : ''
                    }
                  "
                  :disabled="nonReportType"
                  @change="reportDateChange"
                  :picker-options="reportPickerOptions"
                  :style="{ width: '100%' }"
                  :type="isDayReport ? 'datetimerange' : 'daterange'"
                  range-separator="至"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                  :format="`yyyy-MM-dd${isDayReport ? ' HH:mm' : ''}`"
                  :value-format="`yyyy-MM-dd${isDayReport ? ' HH:mm' : ''}`"
                  class="dc-el-date-picker_PickerTimeDateRange"
                ></el-date-picker>
              </el-form-item>
              <div style="height: 100%; width: 55px; margin: 3px 0 0 20px">{{editFormData.reportDay}}  天</div>
            </el-col>
            <el-col :span="12" style="display: flex">
              <el-form-item prop="overtimeBegin" label="加班工时" style="width: calc(100% - 85px);" class="dc-el-form-item_PickerTimeDateRange" :rules="overtimeRule">
                <div style="display: flex">
                  <el-time-select
                    placeholder="开始时间"
                    v-model="editFormData.overtimeBegin"
                    @change="handleOvertimeHour"
                    :disabled="nonReportType || !isDayReport"
                    :picker-options="{
                      start: '18:00',
                      step: '00:30',
                      end: '23:30'
                    }">
                  </el-time-select>
                  <span style="margin: 0 5px"> 至 </span>
                  <el-time-select
                    placeholder="结束时间"
                    v-model="editFormData.overtimeEnd"
                    @change="handleOvertimeHour"
                    :disabled="nonReportType || !isDayReport"
                    :picker-options="{
                      start: '18:30',
                      step: '00:30',
                      end: '24:00',
                      minTime: editFormData.overtimeBegin
                    }">
                  </el-time-select>
                </div>
              </el-form-item>
              <div style="height: 100%; width: 75px; margin: 3px 0 0 20px">{{editFormData.overtimeHour}}  小时</div>
            </el-col>
            <el-col :span="24">
              <el-form-item
                prop="workContent"
                label="工作内容"
                :rules="[{ required: true, message: '工作内容不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_MutilpleInput"
              >
                <el-input
                  v-model="editFormData.workContent"
                  type="textarea"
                  placeholder="请输入工作内容"
                  rows="2"
                  :maxLength="-1"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <upload-file title="附件" :action="action" :objectId="editFormData.annex"
                           :fileFormats="['jpg', 'jpeg', 'png', 'doc', 'docx', 'xls', 'xlsx', 'pdf', 'txt']" :fileSizes="10"
                           v-model="editFormData.annexFile" class="dc-upload-file_ElUploadFile"></upload-file>
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
import {validatenull} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import {listProjectListByTeamMember} from '@/api/projectManage/projectList.js'
import {listDictItemAll} from '@/api/sys/dictItem.js'
import uploadFile from '@/views/components/uploadFile'
/** 根据用户界面配置import组件 结束 */
import {getReportManageById, saveReportManage} from '@/api/projectManage/reportManage'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'

export default {
  extends: BaseUI,
  name: 'reportManage-form',
  components: {
    /** 根据用户界面配置组件 开始 */
    uploadFile,
    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      reportPickerOptions: {
        // disabledDate: (time) => {
        //   console.log(time.getMinutes())
        //   return true
        // } ,
        selectableRange: `09:00:00-18:00:00`
      },
      overtimeRule: {validator: this.validOvertime, trigger: 'change'},
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '周报管理'
      },
      dialogTitle: '周报管理',
      // 选项变量
      // 项目名称选项
      projectOptions: [],
      // 报告类型选项
      reportTypeOptions: [],
      // 加班类型选项
      overtimeTypeOptions: [],
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
  computed: {
    // 是否日报。1：日报；2：周报
    isDayReport() {
      return this.editFormData.reportType && this.editFormData.reportType.value == 1
    },
    // 报告类型是否未选择
    nonReportType() {
      return !this.editFormData.reportType || !this.editFormData.reportType.value
    },
    // 是否双休日、节假日加班
    isWeekOvertime() {
      return this.editFormData.overtimeType && this.editFormData.overtimeType.value
    }
  },
  methods: {
    /**
     * 加班时长表单验证规则
     * @author Yogaa
     * @date 13:35 2023/12/16
     */
    validOvertime(rule, value, callback) {
      let {overtimeBegin, overtimeEnd} = this.editFormData
      // 加班时长只填写了其中一项
      if ((overtimeBegin && !overtimeEnd) || (!overtimeBegin && overtimeEnd)) {
        callback(new Error('加班时长请填写完整！'))
      } else {
        callback()
      }
    },
    /**
     * 处理加班时长
     * @author Yogaa
     * @date 15:43 2023/12/15
     */
    handleOvertimeHour() {
      let {overtimeBegin, overtimeEnd, reportBegin, reportEnd} = this.editFormData
      let time = 0
      if (overtimeBegin && overtimeEnd) {
        let str = '0000-01-01 '
        let begin = new Date(str + overtimeBegin)
        let end = new Date(str + overtimeEnd)
        // 判断结束时间是否大于开始时间
        if (begin > end) {
          this.editFormData.overtimeEnd = ''
          return
        }
        time = end - begin
      }
      // 双休日、节假日加班需要加上报告时间
      if (this.isWeekOvertime && reportBegin && reportEnd) {
        let begin = new Date(reportBegin)
        let end = new Date(reportEnd)
        time += (end - begin)
      }
      this.editFormData.overtimeHour = !time ? '-' : (time / (1000 * 3600)).toFixed(1)
    },
    /**
     * 处理报告时间
     * @author Yogaa
     * @date 16:11 2023/12/29
     */
    handleReportDay() {
      let begin = new Date(this.editFormData.reportBegin)
      let end = new Date(this.editFormData.reportEnd)
      // 报告时间
      let reportDay = '-'
      let timeDiff = end.getTime() - begin.getTime()
      if (this.isDayReport) {
        reportDay = (timeDiff / (1000 * 3600 * 9)).toFixed(2)
      } else {
        reportDay = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1
      }
      this.editFormData.reportDay = reportDay
    },
    /**
     * 报告时间选择改变事件
     * @author Yogaa
     * @date 11:36 2023/12/15
     */
    reportDateChange() {
      let begin = new Date(this.editFormData.reportBegin)
      let end = new Date(this.editFormData.reportEnd)
      // 报告时间
      let reportDay = '-'
      let timeDiff = end.getTime() - begin.getTime()
      let valid = false
      let msg = ''
      // 日报日期范围限制在同一天
      if (this.isDayReport) {
        // 年月日相同为同一天
        valid = begin.getFullYear() === end.getFullYear() && begin.getMonth() === end.getMonth() && begin.getDate() === end.getDate()
        msg = '日报的报告时间仅可选择1天！'
        // 日报报告时间
        reportDay = (timeDiff / (1000 * 3600 * 9)).toFixed(2)
      }
      // 周报限制7天
      else {
        let diffDay = Math.ceil(timeDiff / (1000 * 3600 * 24))
        valid = diffDay <= 6
        // 周报报告时间
        reportDay = diffDay + 1
        msg = '周报的报告时间最大不超过7天'
      }
      if (!valid) {
        this.editFormData.reportBegin = ''
        this.editFormData.reportEnd = ''
        this.clearReportDateValidate()
        this.$message.warning(msg)
      } else {
        this.editFormData.reportDay = reportDay
        // 双休日、节假日加班需要处理加班时长
        if (this.isWeekOvertime) {
          this.handleOvertimeHour()
        }
      }
    },
    /**
     * 报告类型选项改变事件
     * @author Yogaa
     * @date 10:04 2023/12/15
     */
    reportTypeChange() {
      // 清空报告时间、加班工时
      this.editFormData.reportBegin = ''
      this.editFormData.reportEnd = ''
      this.editFormData.overtimeBegin = ''
      this.editFormData.overtimeEnd = ''
      this.editFormData.reportDay = '-'
      this.editFormData.overtimeHour = '-'
      // 报告类型为周报时，清空加班类型
      if (!this.isDayReport) {
        this.editFormData.overtimeType = ''
      }
      this.clearReportDateValidate()
    },
    clearReportDateValidate() {
      this.$nextTick(() => {
        this.$refs.editForm.clearValidate('reportBegin')
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
    doSave() {
      this.setLoad()
      // 处理加班工时
      let data = JSON.parse(JSON.stringify(this.editFormData))
      let {overtimeBegin, overtimeEnd, reportBegin} = data
      // 将报告时间的年月日拼接到加班工时
      let str = reportBegin.split(' ')[0]
      data.overtimeBegin = `${str} ${overtimeBegin}`
      data.overtimeEnd = `${str} ${overtimeEnd}`
      if(data.overtimeHour === '-') {
        data.overtimeHour = null
      }
      let formData = this.createFormData(data)
      saveReportManage(formData)
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
        getReportManageById(id)
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

    listProjectOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 项目清单project_list
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937934')
      // search_List.extra = currentUser.id
      listProjectListByTeamMember(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.projectOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listReportTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{
        'columnName': 'dict_type_id',
        'queryType': '=',
        'value': '1840490672856612912'
      }])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937856')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.reportTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listOvertimeTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{
        'columnName': 'dict_type_id',
        'queryType': '=',
        'value': '1840490672856612916'
      }])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937856')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.overtimeTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listProjectOptions()

      this.listReportTypeOptions()

      this.listOvertimeTypeOptions()
    },
    initEditData(This) {
      return {
        project: {
          id: null,
          projectName: null,
          projectCode: null
        },
        reportType: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        overtimeType: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        reportBegin: '', // 报告时间
        reportEnd: '', // 报告时间
        reportDay: '-',
        overtimeBegin: '', // 加班工时
        overtimeEnd: '', // 加班工时
        overtimeHour: '-',
        workContent: '', // 工作内容
        annex: null,
        annexFile: {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        }
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
        let editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id)),
        }
        // editFormData.attachmentsFile = {
        //   deletes: [], // 待删除（已上传）的文件列表
        //   uploads: editFormData.annexFiles // 待上传的文件列表
        // }
        this.editFormData = editFormData
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

        this.editFormData.annex = null

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
