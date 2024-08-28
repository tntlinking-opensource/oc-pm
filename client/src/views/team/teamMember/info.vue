<style scoped>
.flex {
  display: flex;
  align-items: center;
}
.flex-end {
  justify-content: flex-end;
}
.flex-between {
  justify-content: space-between;
}
.align-center {
  align-items: center;
}
.border-top {
  border-top: 1px solid #eee;
}
.mt {
  margin-top: 10px;
}
.color {
  color: #018CB7;
}
.padd {
  padding: 10px;
  border-bottom: 1px solid #eee;
}
.position {
  width: 60px;
  height: 60px;
  background: #018CB7;
  border-radius: 6px;
  box-sizing: border-box;
  color: #fff;
  text-align: center;
  padding: 10px 5px;
  margin-right: 10px;
}
.team-container span{
  line-height: 1.5;
}
.ellipsis-span {
    display: inline-block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 1500px; /* 根据需要设置最大宽度 */
  }
.cursor {
  cursor: pointer;
}

</style>
<template>
  <el-row class="team-container">
    <edit-form
      ref="editForm"
      v-on:save-finished="handleFinish"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      :fullscreen="true"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-form
        ref="queryForm"
        :model="queryFormData"
        label-width="100px"
        class="dc-el-form_ElQueryForm"
      >
        <el-row>
          <el-col :span="6">
            <el-form-item prop="" label="姓名：" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="queryFormData.user.name"
                :maxLength="-1"
                placeholder="请输入"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="" label="手机号：" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="queryFormData.user.phone"
                :maxLength="-1"
                placeholder="请输入"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="" label="角色：" class="dc-el-form-item_SelectInput">
              <el-select
                v-model="queryFormData.teamRole.id"
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
          <el-col :span="6">
            <el-form-item prop="" label="状态：" class="dc-el-form-item_SelectInput">
              <el-select
                v-model="queryFormData.status"
                :style="{ width: '100%' }"
                placeholder="请选择状态"
                clearable
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in statusData" :key="index" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="" label="所属部门：" class="dc-el-form-item_SelectInput">
              <el-cascader
                ref="cascader106"
                :options="departmentOptions"
                v-model="queryFormData.department.id"
                :style="{ width: '100%' }"
                placeholder="请选择部门"
                :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
                clearable
                filterable
                separator="/"
                class="dc-el-cascader_CascaderInput"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="18" class="dc-el-form-item_SelectInput flex flex-end">
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button @click="getList" style="margin-left:20px" icon="el-icon-search"
                         class="dc-el-button_Button" type="primary">查询</el-button>
            </el-tooltip>
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button icon="el-icon-refresh" class="dc-el-button_Button" @click="handleReset">重置</el-button>
            </el-tooltip>
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button class="dc-el-button_Button" @click="onDialogClose">返回</el-button>
            </el-tooltip>
          </el-col>
        </el-row>
      </el-form>
      <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow flex flex-between">
        <el-tooltip v-show="permission.add" manual="true" class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
        </el-tooltip>
        <span class="ellipsis-span"
              @mouseover="isHovered = true"
              @mouseout="isHovered = false"
              :title="fullContent"
        >
          项目名称：{{ editFormData.projectName }}
          <template v-for="item in stat.teamRoleList">
            {{item.teamRoleName}}  {{item.teamRoleNameCount}}
          </template>
        </span>
      </el-row>
      <div class="border-top mt" v-if="tableData.length">
        <div v-for="item in tableData" :key="item.id" class="flex flex-between padd align-center">
          <div class="flex">
            <div>
              <div class="position" :style="{'background': handleBackground()}">{{ item.teamRole.name }}</div>
            </div>
            <div>
              <el-row :gutter="0">
                <el-col :span="24" class="flex">
                  <span style="width: 300px" >{{ item.user.name }} &nbsp;| &nbsp; - &nbsp; | &nbsp;{{ item.user.createDate || '-' }}</span>
                  <span class="color" style="margin-left: 100px">{{handleKeyValue(item.memberStatus, statusData)}}</span>
                </el-col>
                <el-col :span="24" class="flex">
                  <img class="img" :src="emailImg" alt="邮箱图标">
                  &nbsp;&nbsp;
                  <span>{{item.user.email}} | &nbsp;&nbsp;</span>
                  <img class="img" :src="phoneImg" alt="电话图标">
                  &nbsp;&nbsp;
                  <span>{{item.user.phone}}</span>
                </el-col>
              </el-row>
              <el-row style="width: 1200px" :gutter="0" class="mt">
                <el-col :span="8">
                  所属部门：{{ item.user.department.name }}
                </el-col>
                <el-col :span="8">
                  进入项目时间：{{ item.joinTime }}
                </el-col>
                <el-col :span="8">
                  离开项目时间：{{ item.leaveTime }}
                </el-col>
                <el-col :span="8">
                  成本单价：￥{{ item.unitPriceCost }}
                </el-col>
                <el-col :span="8">
                  计划人天：{{ item.planHumanDay }}
                </el-col>
                <el-col :span="8">
                  计划成本：¥ {{ item.planCost }}
                </el-col>
                <el-col :span="24">
                  职责描述：{{ item.remarks || '-'}}
                </el-col>
              </el-row>
            </div>
          </div>
          <div style="width: 100px">
            <div @click="onEdit(item)" class="color cursor">编辑</div>
            <div class="color mt cursor" @click="handleDelete(item)">删除</div>
          </div>
        </div>
      </div>
      <el-pagination
        :total="total"
        :page-size="queryFormData.limit"
        background
        :current-page="tableDataPage"
        :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onSizeChange"
        v-on:current-change="onCurrentChange"
        class="dc-el-pagination_ElPagination"
      ></el-pagination>
    </el-dialog>
  </el-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import {
  listUserAll, listTeamMemberPage
} from '@/api/team/teamMember.js'
import EditForm from './form'
import {
  listDictItemAll
} from '@/api/sys/dictItem.js'
/** 根据用户界面配置import组件 结束 */
import { treeDepartment } from '@/api/org/department.js'
import {
  getTeamMemberById,
  saveTeamMember,
  deleteTeamMember,
  getTeamMemberStat
} from '@/api/team/teamMember'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import _ from 'underscore'

const COLORS = [
  '#F34E1E',
  '#FFC700',
  '#10A959',
  '#6A9BF7',
  '#9747FF',
  '#D37C2D',
  '#FF9A62',
  '#FED233',
  '#4ECB70',
  '#86B6FF',
  '#D99BFF',
  '#E5A951',
  '#FFBDAD',
  '#FFE979',
  '#92E396',
  '#B1D0FF',
  '#E9BFFF',
  '#EAC287',
  '#D83D0E',
  '#DE8601',
  '#048621',
  '#256DF4',
  '#A661FF',
  '#9C4B00',
  '#CECCCD',
  '#CED3D9'
];
export default {
  extends: BaseUI,
  name: 'teamMember-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */
    EditForm,
    OperationIcon
  },
  computed: {
    fullContent() {
      const roles = this.stat.teamRoleList.map(item => `${item.teamRoleName} ${item.teamRoleNameCount}`);
      return `项目名称：${this.editFormData.projectName}, ${roles.join(', ')}`;
    }
  },
  watch: {
    isHovered(newValue) {
      if (newValue) {
        this.$refs.fullContentSpan.style.whiteSpace = 'normal';
        this.$refs.fullContentSpan.style.overflow = 'visible';
      } else {
        this.$refs.fullContentSpan.style.whiteSpace = 'nowrap';
        this.$refs.fullContentSpan.style.overflow = 'hidden';
      }
    }
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      statusData: [
        {id: '0', name: '未加入'},
        {id: '1', name: '已加入'},
        {id: '2', name: '已脱离'}
      ],
      isHovered: false,
      tableData: [],
      current: 1,
      editFormData: {},
      queryFormData: {
        offset: 0,
        limit: 10,
        status: null,
        user: {
          name: '',
          phone: ''
        },
        teamRole: {
          id: null
        },
        department: {
          id: null
        }
      },
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '团队'
      },
      stat: {
        teamRoleList: []
      },
      total: 0,
      tableDataPage: 1,
      tableDataTotal: 0,
      dialogTitle: '团队',
      // 选项变量
      // 关联项目id选项
      projectOptions: [],
      // 关联用户id选项
      userOptions: [],
      // 团队角色id选项
      teamRoleOptions: [],
      departmentOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      emailImg: require('../../../assets/images/email.svg'),
      phoneImg: require('../../../assets/images/phone.svg')

    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  methods: {
    handleFinish () {
      this.handleGetStat();
      this.getList();
    },
    handleBackground () {
      const random = Math.floor(Math.random()*26);
      return COLORS[random - 1] || COLORS[0];
    },
    handleKeyValue (key, arr) {
      const current = _.find(arr, item => item.id === key)
      return current && current.name
    },
    handleGetDep () {
      treeDepartment({}).then(responseData => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    handleGetStat () {
      const { id } = this.editFormData;
      getTeamMemberStat(id).then(data => {
        if (data.code == '100') {
          this.stat = data.data
      }
      })
    },
    handleDelete (item) {
      const { id, updateDate, memberStatus } = item
      if (memberStatus !== '0') {
        this.$message({
            type: 'warning',
            message: '选中数据已加入项目，无法删除！'
          })
      } else {
        this.$msgbox.confirm('确认删除该信息？\n' +
          '\n' +
          '删除后将无法恢复，请谨慎删除！', '提示').then(() => {
          deleteTeamMember({id, updateDate}).then(() => {
            this.$emit('save-finished')
            this.$message('操作成功')
            this.getList()
          })
        })
      }

    },
    hadnleGetData () {
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
    getList () {
      let params = [
        {
          "columnName": "project_id",
          "queryType": "=",
          "value": `${this.editFormData.id}`
        }
      ]
      if (this.queryFormData.user.name) {
        params.push({
          "columnName": "user.name",
          "queryType": "like",
          "value": `%${this.queryFormData.user.name}%`
        })
      }
      if (this.queryFormData.user.phone) {
        params.push({
          "columnName": "user.phone",
          "queryType": "like",
          "value": `%${this.queryFormData.user.phone}%`
        })
      }
      if (this.queryFormData.teamRole.id) {
        params.push({
          "columnName": "team_role_id",
          "queryType": "=",
          "value": `${this.queryFormData.teamRole.id}`
        })
      }
      if (this.queryFormData.status === '0') {
        params.push({
          "columnName": "join_time",
          "queryType": ">",
          "value": ``
        })
      }
      if (this.queryFormData.status === '1') {
        params.push({
          "columnName": "leave_time",
          "queryType": ">=",
          "value": ``
        })
      }
      if (this.queryFormData.status === '2') {
        params.push({
          "columnName": "leave_time",
          "queryType": "<",
          "value": ``
        })
      }
      if (this.queryFormData.department.id) {
        params.push({
          "columnName": "department.id",
          "queryType": "=",
          "value": `${this.queryFormData.department.id}`
        })
      }
      listTeamMemberPage({
        ...this.queryFormData,
        params
      }).then(responseData => {
        if (responseData.code == 100) {
          this.tableData = responseData.data.rows || []
          this.total = responseData.data.total
        }
      })
    },
    handleReset () {
      this.queryFormData = {
        offset: 0,
          limit: 10,
          status: null,
          user: {
          name: '',
            phone: ''
        },
        teamRole: {
          id: null
        },
        department: {
          id: null
        }
      }
      this.getList()
    },
    onCreate() {
      this.$refs.editForm.$emit('openAddDialog', this.editFormData)
    },
    onEdit(row) {
      this.$refs.editForm.$emit('openEditDialog', {
        ...row,
        ..._.omit(row.user, 'createDate', 'department', 'id'),
        departmentName: row.user && row.user.department && row.user.department.name,
        sex: '-'
      })
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.queryFormData.limit = val
      this.queryFormData.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.queryFormData.offset = (val - 1) * this.queryFormData.limit
      this.tableDataPage = val
      this.getList()
    },
    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getTeamMemberById(id).then((responseData) => {
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
      // 关闭对话框的逻辑
      this.dialogProps.visible = false;
      this.$emit('save-finished')

    },
    onDialogOpen() {
      this.$nextTick(() => {
        // this.$refs['editForm'].clearValidate()
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
    }
  },
  watch: {

  },
  mounted: function() {
    this.$nextTick(() => {
      this.hadnleGetData()
      this.$on('openViewDialog', async function (row) {
        this.action = 'view'
        this.dialogProps.title = `团队管理详情`
        this.editFormData = {
          ...row
        }
        this.getList()
        this.handleGetStat()
        this.handleGetDep()
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
