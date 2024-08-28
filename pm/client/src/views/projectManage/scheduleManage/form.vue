<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="60%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          v-if="dialogProps.visible"
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
                  :value="data.project.projectName"
                  disabled
                  :maxLength="50"
                  placeholder="请输入项目名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目编号" class="dc-el-form-item_SingleInput">
                <el-input
                  :value="data.project.projectCode"
                  disabled
                  :maxLength="50"
                  placeholder="请输入项目编号"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="scheduleName" label="所属里程碑" class="dc-el-form-item_SingleInput">
                <el-input
                  :value="data.milestoneName"
                  disabled
                  :maxLength="50"
                  placeholder="请输入所属里程碑"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="scheduleType.id"
                label="类型"
                :rules="[{ required: true, message: '类型不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.scheduleType"
                  :style="{ width: '100%' }"
                  placeholder="请选择类型"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in scheduleTypeOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="scheduleName"
                label="日程名称"
                :rules="[{ required: true, message: '日程名称不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.scheduleName"
                  :maxLength="50"
                  placeholder="请输入日程名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="participants"
                label="参与人"
                :rules="[{ required: true, message: '参与人不能为空', trigger: 'change', type: 'array' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-select
                  v-model="editFormData.participants"
                  :style="{ width: '100%' }"
                  multiple
                  placeholder="请选择参与人"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in participantOptions"
                    :key="index"
                    :label="item.user.name"
                    :value="item.user.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
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
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDateRange"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="scheduleDesc" label="日程描述" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.scheduleDesc"
                  type="textarea"
                  placeholder="请输入"
                  rows="2"
                  :maxLength="-1"
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
import { listDictItemAll } from '@/api/sys/dictItem.js'
/** 根据用户界面配置import组件 结束 */
import { getScheduleManageById, saveScheduleManage } from '@/api/projectManage/scheduleManage'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import {listTeamMemberAll} from "@/api/team/teamMember";
export default {
  extends: BaseUI,
  name: 'scheduleManage-form',
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
        title: '日程管理'
      },
      dialogTitle: '日程管理',
      // 选项变量
      // 类型选项
      scheduleTypeOptions: [],
      // 参与人选项
      participantOptions: [],
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
    data: {
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
    doSave() {
      this.setLoad()
      // 处理参与人员数据
      let editFormData = JSON.parse(JSON.stringify(this.editFormData))
      let participants = editFormData.participants
      if (participants && participants.length > 0) {
        editFormData.participants = participants.join(',')
      } else {
        editFormData.participants = ''
      }
      editFormData.milestone = {id: this.data.id}
      saveScheduleManage(editFormData)
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
        getScheduleManageById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
              // 处理参与人
              form.participants = form.participantIds.split(',')
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

    listScheduleTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{
        'columnName': 'dict_type_id',
        'queryType': '=',
        'value': '1859848726116007997'
      }])
      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937856')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.scheduleTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
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
        'value': this.data.project.id
      }])
      listTeamMemberAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.participantOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listScheduleTypeOptions()
      this.initTeamMemberOptions()
    },
    initEditData(This) {
      return {
        scheduleType: {
          id: null,
          name: null,
          value: null,
          dictTypeId: null
        },
        scheduleName: '', // 日程名称
        participants: '', // 参与人
        planBegin: '', // 计划日期
        planEnd: '', // 计划日期
        scheduleDesc: '' // 日程描述
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
      this.$on('openAddDialog', function (parent) {
        this.action = 'add'
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

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
