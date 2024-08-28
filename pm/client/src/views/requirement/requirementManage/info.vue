<style lang="scss"  scoped>
.reqTable {
  display: flex;
  margin: 10px 0;
  border-bottom: 1px solid #d1caca;
  justify-content: space-between;
  height: auto;
  min-height: 120px;

  .tableIndex {
    height: 50px;
    width: 50px;
    border-radius: 10px;
    text-align: center;
    line-height: 50px;
    margin-left: 10px;
    margin-right: 20px;
    color: #fff;

  }

  .tableForm {
    width: 100%;

    .tableCol {
      margin-bottom: 10px;

      .tableIcon {
        width: 8px;
        height: 8px;
        background: #018cb7;
        display: inline-block;
        border-radius: 50%;
        position: relative;
        top: -2px;
      }

      .tableColumn {
        font-size: 14px;
        color: #999999;
      }

      .tableName {
        font-size: 14px;
      }

      .tableView {
        font-size: 12px;
        margin-left: 10px;
        color: #018cb7;
        cursor: pointer;
      }
    }
  }
}

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

.mt {
  margin-top: 10px;
}

.color {
  color: #018CB7;
}

.team-container span {
  line-height: 1.5;
}

.ellipsis-span {
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 1500px;
  /* 根据需要设置最大宽度 */
}
</style>
<template>
  <el-row class="team-container">
    <edit-form ref="editForm" v-on:save-finished="getList" v-on:before-load="setLoad" v-on:after-load="resetLoad"
      :permission="permission"></edit-form>
    <el-dialog v-on:open="onDialogOpen" v-on:close="onDialogClose" v-loading="loading" :fullscreen="true"
      :title="dialogProps.title" :visible.sync="dialogProps.visible" :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog">
      <el-form ref="queryForm" :model="queryFormData" label-width="100px" class="dc-el-form_ElQueryForm">
        <el-row>
          <el-col :span="6">
            <el-form-item prop="" label="需求编号：" class="dc-el-form-item_SingleInput">
              <el-input v-model="queryFormData.requirementCode" :maxLength="-1" placeholder="请输入" clearable
                class="dc-el-input_SingleInput"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item prop="" label="需求名称：" class="dc-el-form-item_SingleInput">
              <el-input v-model="queryFormData.requirementName" :maxLength="-1" placeholder="请输入" clearable
                class="dc-el-input_SingleInput"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item prop="" label="所属里程碑:" class="dc-el-form-item_SelectInput">
              <el-select v-model="queryFormData.milestone.id" :style="{ width: '100%' }" placeholder="请选择" clearable
                value-key="id" filterable class="dc-el-select_SelectInput">
                <el-option v-for="(item, index) in milestoneData" :key="index" :label="item.milestoneName"
                  :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item prop="" label="所属日程：" class="dc-el-form-item_SelectInput">
              <el-select v-model="queryFormData.schedule.id" :style="{ width: '100%' }" placeholder="请选择" clearable
                value-key="id" filterable class="dc-el-select_SelectInput">
                <el-option v-for="(item, index) in scheduleData" :key="index" :label="item.scheduleName"
                  :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="24" class="dc-el-form-item_SelectInput flex flex-end"
            style="padding-bottom: 10px; border-bottom: 1px solid #d1caca ; padding-top: 10px;">

            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button @click="getList" style="margin-left:20px" icon="el-icon-search" class="dc-el-button_Button"
                type="primary">查询</el-button>
            </el-tooltip>
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button icon="el-icon-refresh" class="dc-el-button_Button" @click="handleReset">重置</el-button>
            </el-tooltip>
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button class="dc-el-button_Button" @click="dialogProps.visible = false">返回</el-button>
            </el-tooltip>
          </el-col>
        </el-row>
      </el-form>


      <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow flex flex-between"
        style="padding-bottom: 10px; border-bottom: 1px solid #d1caca ;padding-top: 10px; ">
        <el-tooltip v-show="permission.add" manual="true" class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
        </el-tooltip>
        <span class="ellipsis-span" @mouseover="isHovered = true" @mouseout="isHovered = false" :title="fullContent">
          项目名称：{{ editFormData.projectName }}

        </span>
      </el-row>
      <el-row v-loading="searchLoading">
        <div v-for="(item, index) in tableData" :key="item.id" class="reqTable">
          <div class="tableIndex" :style="{ 'background': handleBackground() }">{{ index < 9 ? '0' + (index + 1) : index + 1 }}
              </div>
              <div class="tableForm">
                <el-row style="width: 1200px">
                  <el-col :span="8" class="tableCol">
                    <span class="tableIcon"></span> <span class="tableColumn"> 需求名称：</span><span class="tableName">
                      {{ item.requirementName }}
                    </span>
                  </el-col>
                  <el-col :span="8" class="tableCol">
                    <span class="tableIcon"></span> <span class="tableColumn">
                      需求编号：</span><span class="tableName">
                      {{ item.requirementCode }}
                    </span>
                  </el-col>
                  <el-col :span="8" class="tableCol">
                    <span class="tableIcon"></span> <span class="tableColumn">
                      所属里程碑：</span><span class="tableName">
                      {{ item.milestone.milestoneName }}
                    </span>
                  </el-col>
                  <el-col :span="8" class="tableCol">
                    <span class="tableIcon"></span> <span class="tableColumn">
                      所属日程：</span><span class="tableName">
                      {{ item.schedule.scheduleName }}
                    </span>
                  </el-col>

                  <el-col :span="8" class="tableCol">
                    <span class="tableIcon"></span> <span class="tableColumn">
                      提出人：</span><span class="tableName">
                      {{ item.proposer }}
                    </span>
                  </el-col>
                  <el-col :span="8" class="tableCol">
                    <span class="tableIcon"></span> <span class="tableColumn">
                      提出时间：</span><span class="tableName">
                      {{ item['submissionDate'] }}
                    </span>
                  </el-col>
                  <el-col :span="24" class="tableCol">
                    <span class="tableIcon"></span> <span class="tableColumn">
                      需求描述：</span><span class="tableName">
                      {{ item.requirementDesc || '-' }}
                    </span>
                  </el-col>
                  <el-col :span="24" class="tableCol">
                    <span class="tableIcon"></span><span class="tableColumn">
                      附件：
                    </span>
                    <span class="tableName">
                      <template v-for="(it, index) in item.attachmentFile">
                        {{ it['name'] }}
                        <OperationIcon type='info' content='预览' placement='top-start' icon-name='el-icon-view'
                          @click='() => onViewFileList(index, it)'></OperationIcon>
                        <OperationIcon type='info' content='下载' placement='top-start' icon-name='el-icon-download'
                          @click='() => onUploadViewFile(index, it)'></OperationIcon>
                        &nbsp; &nbsp; &nbsp;
                      </template>
                    </span>
                  </el-col>
                </el-row>
              </div>
              <div style="width: 50px">
                <div @click="onEdit(item)" class="color">编辑</div>
                <div class="color mt" @click="handleDelete(item)">删除</div>
              </div>
          </div>
          <el-pagination :total="total" :page-size="queryFormData.limit" background :current-page="tableDataPage"
            :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
            v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
            class="dc-el-pagination_ElPagination"></el-pagination>
        </el-row>
    </el-dialog>
  </el-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import EditForm from './form'
import {
  listDictItemAll
} from '@/api/sys/dictItem.js'
/** 根据用户界面配置import组件 结束 */
import { treeDepartment } from '@/api/org/department.js'
import {
  getRequirementManageById,
  deleteRequirementManage,
  listRequirementManagePage,
  listMilestoneManage,
  listScheduleManage
} from '@/api/requirement/requirementManage'
import { getLocalToken } from '@/utils/auth'
import {
  getTeamMemberStat
} from '@/api/team/teamMember'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import { Base64 } from "js-base64";
import { fileUploadById } from '@/api/sys/sysFile'
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
  name: 'requirementManage-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */
    EditForm,
    OperationIcon
  },
  computed: {
    fullContent() {
      /**const roles = this.stat.teamRoleList.map(item => `${item.teamRoleName} ${item.teamRoleNameCount}`);*/
      return `项目名称：${this.editFormData.projectName}`;
    },
  },
  data() {
    return {
      searchLoading: false,
      /** 根据用户界面配置生成data数据 开始 */
      // statusData: [
      //   {id: '1', name: '已加入'},
      //   {id: '2', name: '已脱离'}
      // ],
      isHovered: false,
      tableData: [],
      current: 1,
      editFormData: {},
      queryFormData: {
        offset: 0,
        limit: 10,
        milestone: {
          id: null
        },
        schedule: {
          id: null
        },
        requirementCode: null,
        requirementName: null
      },
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '团队'
      },
      item: {
        attachmentFile: [
          // Your attachment data goes here
        ],
      },
      // stat: {
      //   teamRoleList: []
      // },
      total: 0,
      tableDataPage: 1,
      tableDataTotal: 0,
      dialogTitle: '团队',
      // 选项变量
      // 关联项目id选项
      projectOptions: [],
      // 关联用户id选项
      // userOptions: [],
      // 团队角色id选项
      // teamRoleOptions: [],
      // departmentOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      formattedAttachments: '',
      // 窗口操作类型 view/edit/add
      action: '',
      reqImg1: require('../../../assets/images/u232.svg'),
    }
  },
  watch: {
    // 观察里程碑ID的变化，当变化时执行查询日程的逻辑  
    'queryFormData.milestone.id': {
      handler(newVal, oldVal) {
        console.log(newVal);
        if (newVal !== oldVal) {
          if (newVal) {
            this.handleGetScheduleData(newVal).then(() => {
              console.log(this.scheduleData);
              this.$forceUpdate();
              this.queryFormData.schedule.id = null;
            });
          } else {
            this.scheduleData = []
          }
        }
      },
      immediate: true,
    },
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  methods: {
    handleGetStat() {
      const { id } = this.editFormData;
      getTeamMemberStat(id).then(data => {
        if (data.code == '100') {
          this.stat = data.data
        }
      })
    },


    handleGetMilestone() {
      const { id } = this.editFormData;
      listMilestoneManage({
        params: [
          {
            "columnName": "project_id",
            "queryType": "=",
            "value": id
          }
        ]
      }).then((responseData) => {
        if (responseData.code == 100) {
          this.milestoneData = responseData.data
        }
      })
    },

    handleGetScheduleData(milestoneId) {
      return new Promise((resolve, reject) => {
        listScheduleManage({
          params: [
            {
              "columnName": "milestone_id",
              "queryType": "=",
              "value": milestoneId
            }
          ]
        }).then((responseData) => {
          if (responseData.code == 100) {
            this.scheduleData = responseData.data
            resolve();
          } else {
            reject();
          }
        })
      });
    },



    handleDelete({ id, updateDate }) {
      this.$msgbox.confirm('确认删除该信息？\n' +
        '\n' +
        '删除后将无法恢复，请谨慎删除！', '提示').then(() => {
          deleteRequirementManage({ id, updateDate }).then(() => {
            this.$message('操作成功')
            this.getList()
          })
        })
    },
    onViewFileList(index, row) { // 预览
      console.log(row)
      let suffix = row.name.substring(row.name.lastIndexOf("."))
      let fileId = row.id + "" + suffix // 拼接参数，文件ID + 后缀名
      let token = getLocalToken()

      // 文件流访问路径
      let url = process.env.FILE_PREVIEW_URL + `sys/sysSeting/getFile/` + fileId + `?token=` + token
      window.open(process.env.KK_FILE_URL + encodeURIComponent(Base64.encode(url)));
    },
    onUploadViewFile(index, row) { // 下载
      console.log(index)
      console.log(row)
      fileUploadById(row.id).then(responseData => {
        const content = responseData;
        this.download(content, row.name);
      }).catch(error => {
        this.outputError(error)
      });
    },
    //下载方法
    download(content, fileName) {
      const blob = new Blob([content]);
      const url = window.URL.createObjectURL(blob);
      let dom = document.createElement("a");
      dom.style.display = "none";
      dom.href = url;
      dom.setAttribute("download", fileName);
      document.body.appendChild(dom);
      dom.click();
    },
    handleBackground() {
      const random = Math.floor(Math.random() * 26);
      return COLORS[random - 1] || COLORS[0];
    },
    getList() {
      let params = [
        {
          "columnName": "project_id",
          "queryType": "=",
          "value": `${this.editFormData.id}`
        }
      ]
      if (this.queryFormData.requirementCode) {
        params.push({
          "columnName": "requirement_code",
          "queryType": "like",
          "value": `%${this.queryFormData.requirementCode}%`
        })
      }
      if (this.queryFormData.requirementName) {
        params.push({
          "columnName": "requirement_name",
          "queryType": "like",
          "value": `%${this.queryFormData.requirementName}%`
        })
      }

      if (this.queryFormData.milestone.id) {
        params.push({
          "columnName": "milestone_id",
          "queryType": "=",
          "value": `${this.queryFormData.milestone.id}`
        })
      }

      if (this.queryFormData.schedule.id) {
        params.push({
          "columnName": "schedule_id",
          "queryType": "=",
          "value": `${this.queryFormData.schedule.id}`
        })
      }

      this.searchLoading = true
      listRequirementManagePage({
        ...this.queryFormData,
        params
      }).then(responseData => {
        if (responseData.code == 100) {
          this.tableData = responseData.data.rows || []
          this.total = responseData.data.total
        }
      }).finally(()=>{
        this.searchLoading = false
      })
    },
    handleReset() {
      this.queryFormData = {
        offset: 0,
        limit: 10,
        status: null,
        user: {
          name: '',
          phone: ''
        },
        milestone: {
          id: null
        },
        schedule: {
          id: null
        }
      }
      this.getList()
    },
    onCreate() {
      this.$refs.editForm.$emit('openAddDialog', this.editFormData)
    },
    onEdit(row) {
      this.$refs.editForm.$emit('openEditDialog', row)
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
        getRequirementManageById(id).then((responseData) => {
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

  mounted: function () {
    this.$nextTick(() => {
      // this.hadnleGetData()
      this.$on('openViewDialog', async function (row) {
        this.action = 'view'
        this.dialogProps.title = `需求管理详情`
        this.editFormData = {
          ...row
        }
        this.getList()
        this.handleGetStat()
        this.handleGetMilestone()
        this.dialogProps.visible = true
      })
    })
  },
}
</script>
