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
                prop="project.projectCode"
                label="项目编号："
                class="dc-el-form-item_SelectInput"
              >
                <el-input
                  v-model="editFormData.projectCode"
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
                prop="user.id"
                label="姓名："
                :rules="[{ required: true, message: '姓名不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-select
                  v-model="editFormData.user.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                  @change="handleChange"
                >
                  <el-option v-for="(item, index) in userData" :key="item.id" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="sex"
                label="性别："
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-input
                  v-model="editFormData.sex"
                  type="text"
                  placeholder=""
                  disabled
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
              prop="teamRole.id"
              label="入职时间："
              class="dc-el-form-item_SelectInput"
            >
                <el-input
                  v-model="editFormData.createDate"
                  type="text"
                  placeholder=""
                  disabled
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="unitPriceCost" label="邮箱：" class="dc-el-form-item_NumberInput">
                <el-input
                  v-model="editFormData.email"
                  type="text"
                  placeholder=""
                  disabled
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="user.phone"
                label="电话："
                class="dc-el-form-item_NumberInput"
              >
                <el-input
                  v-model="editFormData.phone"
                  type="number"
                  placeholder=""
                  disabled
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="planCost" label="所属部门：" class="dc-el-form-item_NumberInput">
                <el-input
                  v-model="editFormData.departmentName"
                  type="text"
                  placeholder=""
                  disabled
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '进入项目日期不能为空', trigger: 'change' }]"
                prop="joinTime" label="进入项目日期：" class="dc-el-form-item_MutilpleInput">
                <el-date-picker
                  v-model="editFormData.joinTime"
                  :style="{ width: '100%' }"
                  placeholder="请选择进入项目时间"
                  :picker-options="startPickerOptions"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                  @change="handleChangeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '离开项目日期不能为空', trigger: 'change' }]"
                prop="leaveTime" label="离开项目日期：" class="dc-el-form-item_MutilpleInput">
                <el-date-picker
                  v-model="editFormData.leaveTime"
                  :style="{ width: '100%' }"
                  :picker-options="endPickerOptions"
                  placeholder="请选择离开项目时间"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                  @change="handleChangeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '角色不能为空', trigger: 'change' }]"
                prop="teamRole.id" label="角色：" class="dc-el-form-item_MutilpleInput">
                <el-select
                  v-model="editFormData.teamRole.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择角色"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option v-for="(item, index) in roleData" :key="index" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="unitPriceCost" label="成本单价：" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.unitPriceCost"
                  type="number"
                  placeholder="请输入成本单价"
                  :maxLength="30"
                  @change="handleChangeDate"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :rules="[{ required: true, message: '计划人天不能为空', trigger: 'change' }]"
                prop="planHumanDay" label="计划人天：" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.planHumanDay"
                  type="number"
                  placeholder="请输入计划人天"
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                  @change="handleChangeDay"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="planCost" label="计划成本：" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.planCost"
                  type="number"
                  placeholder="请输入计划成本"
                  :maxLength="30"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="remarks" label="职责描述：" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.remarks"
                  type="textarea"
                  placeholder="请输入职责描述"
                  rows="3"
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
import {
  validatenull
} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
// import {
//   listProjectListAll
// } from '@/api/projectManage/projectList.js'
import {
  listUserAll
} from '@/api/team/teamMember.js'
import * as user from '@/api/admin/user'
import { listDictItemAll } from '@/api/sys/dictItem'
/** 根据用户界面配置import组件 结束 */
import {
  getTeamMemberById,
  saveTeamMember
} from '@/api/team/teamMember'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import moment from 'moment'
export default {
  extends: BaseUI,
  name: 'teamMember-form',
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
        title: '团队'
      },
      userData: [],
      roleData: [],
      dialogTitle: '团队',
      // 选项变量
      // 关联项目id选项
      projectOptions: [],
      // 关联用户id选项
      userOptions: [],
      // 团队角色id选项
      teamRoleOptions: [],
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
    handleChangeDay () {
      const { planHumanDay = 0, unitPriceCost = 0 } = this.editFormData
      this.editFormData = {
        ...this.editFormData,
        planCost: Number((planHumanDay/21.75*unitPriceCost).toFixed(2))
       }
    },
    handleChangeDate () {
      const { leaveTime = '', joinTime = '', unitPriceCost = 0 } = this.editFormData
      if (leaveTime && joinTime) {
        const diff = moment(leaveTime).diff(joinTime, 'days')
       this.editFormData = {
        ...this.editFormData,
        planHumanDay: diff + 1,
        planCost: Number(((diff + 1)/21.75*unitPriceCost).toFixed(2))
       }
      }
    },
    handleChange () {
      const current = this.userData.findIndex(item => item.id === this.editFormData.user.id)
      console.log("----",this.editFormData.user.id);
      console.log("----",this.editFormData);
      const res = this.userData[current]
      this.editFormData = {
        ...this.editFormData,
        createDate: res.createDate,
        email: res.email,
        phone: res.phone,
        departmentName: res.department.name,
        sex: '-'
      }
    },
    handleDatas () {
      user.listUserAll({}).then((responseData) => {
          if (responseData.code == 100) {
            this.userData = responseData.data
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
      console.log("-------",this.editFormData);
      saveTeamMember({
        ...this.editFormData,
        project: {
          id: this.editFormData.project.id
        },
        teamRole: {
          id: this.editFormData.teamRole.id
        },
        user: {
          id: this.editFormData.user.id
        }
      }).then(responseData => {
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
        getTeamMemberById(id).then((responseData) => {
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
    listUserOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      // search_List.params.push.apply(search_List.params, [{
      //   'columnName': 'tenant.id, queryType: ' = ' value': currentUser.tenant.id
      // }])

      listUserAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.userOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },
    listTeamRoleOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1740772218121937856")


      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.teamRoleOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      // this.listProjectOptions()
      // this.listUserOptions()
      // this.listTeamRoleOptions()
    },
    initEditData(This) {
      return {
        id:null,
        project: {
          id: null,
          projectName: null,
          projectShortName: null,
          projectCode: null,
          projectStatus: null,
        },
        user: {
          id: null,
          name: null,
          departmentId: null,
          phone: null,
          email: null,
        },
        joinTime: '', // 加入项目时间
        leaveTime: '', // 离开项目时间
        teamRole: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null,
        },
        unitPriceCost: null, // 成本单价
        planHumanDay: null, // 计划人天
        planCost: null, // 计划成本
        remarks: '', // 备注信息
      }
    }
  },
  watch: {

  },
  mounted () {
    this.$nextTick(() => {
      this.handleDatas();
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
        console.log("-rowrow---",row);
        this.editFormData = {
          ...this.initEditData(),
          ...row,
          ...row.project,
          id: row.id
        }
        console.log("----",this.editFormData);
        // console.log(this.editFormData);


        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function(row) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}成员`
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
