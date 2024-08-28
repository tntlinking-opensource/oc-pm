<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref='history' :bussObject="curentRow">
    </history>
    <!-- 项目详情弹窗 -->
    <ProjectDetail ref="projectDetail"></ProjectDetail>
    <Completed ref="completed" @save-finished="getList"></Completed>
<!--    <el-row gutter='0' type='flex' justify='start' align='top' class='dc-el-row_ElRow'>-->
<!--      <el-col :span="24">-->
<!--        <query-tag ref='queryTag' v-model='queryEditObject' :moreParm='moreParm'-->
<!--                   :routerId='$route && $route.meta.routerId' :isQueryConditionPanel='isQueryConditionPanel'-->
<!--                   v-on:queryTagPanel='onQueryTagPanel' v-on:queryTagClick='onQueryTagClick' v-on:search='onSearch'-->
<!--                   class='dc-query-tag_ElConditionTag'></query-tag>-->
<!--      </el-col>-->
<!--      <el-col :span="6">-->
<!--        <div style="text-align: right">-->
<!--          <el-button-group class='dc-el-button-group_ButtonGroup'>-->
<!--            <el-tooltip effect='dark' content='搜索' placement='top'>-->
<!--              <el-button v-on:click='onSearch' type='primary' icon='el-icon-search'></el-button>-->
<!--            </el-tooltip>-->
<!--            <el-tooltip effect='dark' content='重置' placement='top'>-->
<!--              <el-button-->
<!--                v-on:click='reset'-->
<!--                type='primary' icon='el-icon-refresh-right'></el-button>-->
<!--            </el-tooltip>-->
<!--            <el-tooltip effect='dark' content='更多' placement='top'>-->
<!--              <el-button v-on:click='onQueryConditionPanel("query")' type='primary'-->
<!--                         icon='el-icon-d-arrow-right'></el-button>-->
<!--            </el-tooltip>-->
<!--          </el-button-group>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--    <query-condition-panel v-if='isQueryConditionPanel' ref='conditionPanel'-->
<!--                           :conditionColumns='(metadata.find(item => {return item.type == "main"})).conditionPanel'-->
<!--                           v-model='moreParm' :routerId='$route.meta.routerId'-->
<!--                           class='dc-query-condition-panel_ElConditionPanel'></query-condition-panel>-->
    <el-form ref='queryForm' :model='queryFormData' label-width='100px' v-if='!isQueryConditionPanel'
             class='dc-el-form_ElQueryForm'>
      <el-row>
        <el-col :span="6">
          <el-form-item prop="projectCode" label='项目编号' class='dc-el-form-item_SingleInput'>
            <el-input v-model='queryFormData.projectCode' :maxLength='14' placeholder='请输入项目编号' clearable
                      class='dc-el-input_SingleInput'></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="projectName" label='项目名称' class='dc-el-form-item_SingleInput'>
            <el-input v-model='queryFormData.projectName' :maxLength='50' placeholder='请输入项目名称' clearable
                      class='dc-el-input_SingleInput'></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="projectType" label='项目类型' class='dc-el-form-item_SelectInput'>
            <el-select v-model='queryFormData.projectType' :style='{"width":"100%"}' placeholder='请选择项目类型' clearable
                       value-key='id' filterable class='dc-el-select_SelectInput'>
              <el-option
                v-for="(item, index) in projectTypeOptions"
                :key="index"
                :label="item.name"
                :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="projectStatus" label='项目状态' class='dc-el-form-item_SelectInput'>
            <el-select v-model='queryFormData.projectStatus' :style='{"width":"100%"}' placeholder='请选择项目状态' clearable
                       value-key='id' filterable class='dc-el-select_SelectInput'>
              <el-option
                v-for="(item, index) in projectStatusOptions"
                :key="index"
                :label="item.name"
                :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="department.id" label='所属部门' class='dc-el-form-item_CascaderInput'>
            <el-cascader ref='cascader191' :options='departmentOptions' v-model='queryFormData.department.id'
                         :style='{"width":"100%"}' placeholder='请选择所属部门'
                         :props='{"label":"name","value":"id","children":"children","checkStrictly":true,"emitPath":false}'
                         clearable filterable separator='/' class='dc-el-cascader_CascaderInput'></el-cascader>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter='0' type='flex' justify='end' align='top' class='dc-el-row_ElRow'>
      <process-panel ref='processPanel' :bizAdd='permission.add' :tenantId='currentUser.company.id' keyPrefix='PROC_'
                     procName='项目审批流程' formKey='wf/common/wfForm.vue#XX/XX/form.vue'
                     procTitile='${start_user_name}发起${form.name}项目审批' v-on:v-loading='onVLoading'
                     class='dc-process-panel_ProcessPanel'></process-panel>
    </el-row>
    <el-row gutter='0' type='default' class='dc-el-row_ElRow' style="margin-bottom: 5px;">
<!--      <el-tooltip disabled class='dc-el-tooltip_Button'>-->
<!--        <el-button type='primary' icon='el-icon-plus' class='dc-el-button_Button'>新增项目</el-button>-->
<!--      </el-tooltip>-->
<!--      <el-tooltip disabled class='dc-el-tooltip_Button'>-->
<!--        <el-button type='primary' icon='el-icon-delete' class='dc-el-button_Button'>删除</el-button>-->
<!--      </el-tooltip>-->
      <el-tooltip disabled class='dc-el-tooltip_Button'>
        <el-button type='primary' icon='el-icon-circle-check' class='dc-el-button_Button' @click="handleProjectCompleted">项目结项</el-button>
      </el-tooltip>
      <el-button type='primary' icon='el-icon-search' class='dc-el-button_Button' @click="onSearch">搜索</el-button>
      <el-button type='primary' icon='el-icon-refresh-right' class='dc-el-button_Button' @click="reset">重置</el-button>
    </el-row>
    <el-table ref="table" :data="tableData" border highlight-current-row @current-change="handleCurrentChange" @selection-change="handleSelectionChange" style="width: 100%">
<!--      <el-table-column align="center" fixed="left" type="selection" width="55"></el-table-column>-->
      <el-table-column align="center" type="index" width="50" label="序号"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="projectName" label="项目名称" min-width="180"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="projectCode" label="项目编号" min-width="120">
        <template slot-scope='{row}'>
          <span style="color: #409eff; cursor: pointer" @click="openProjectDetail(row)">{{ row.projectCode }}</span>
        </template>
      </el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="projectShortName" label="项目简称" min-width="120"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="projectStatus.name" label="项目状态" min-width="60"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="department.name" label="所属部门" min-width="60"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="planBeginDate" label="项目计划开始时间" min-width="100"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="planEndDate" label="项目计划结束时间" min-width="100"></el-table-column>
      <el-table-column align="center" label="操作" width="90" fixed="right">
        <template slot-scope="scope">
          <OperationIcon v-on:click='onView(scope)' type='info' v-show='permission.view' content='查看' placement='top'
                         icon-name='el-icon-view' class='dc-OperationIcon_IconButton'></OperationIcon>
          <OperationIcon v-on:click='onShowHistory(scope)' type='info' v-show='permission.view' content='历史记录'
                         placement='top' icon-name='el-icon-info' class='dc-OperationIcon_IconButton'></OperationIcon>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :total='tableDataTotal' :page-size='search.limit' background :current-page='tableDataPage'
                   :page-sizes='[10,20,30,40,50,100]' layout='total, sizes, prev, pager, next, jumper'
                   v-on:size-change='onSizeChange' v-on:current-change='onCurrentChange'
                   class='dc-el-pagination_ElPagination'></el-pagination>
    <comment ref='commentForm' class='dc-comment_ElImport'></comment>
    <component ref='wfForm' v-on:save-finished="getTaskList()" v-on:after-wfForm-load="afterWfFormload()" :is="wfForm" class='dc-component_ElImport'></component>
  </el-row>
</template>
<script>
import {validatenull} from '@/utils/validate'

import {listProjectListPage, deleteProjectList} from '@/api/projectManage/projectList'

import {listResourcePermission} from '@/api/admin/common/permission'
import {getHistProcInstance} from '@/api/wf/procinst'
import {getProcessByKey, getStartForm} from '@/api/wf/processdefinition'

/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history"
import QueryTag from "@/views/components/queryTag"
import QueryConditionPanel from "@/views/components/queryConditionPanel"
import {listDictItemAll} from '@/api/sys/dictItem.js'
import {treeDepartment} from '@/api/org/department.js'
import processPanel from "@/views/wf/common/processPanel"
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect"
import Comment from "@/views/wf/common/comment"
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import WfMainUI from '@/views/wf/common/wfMainUI'
import OperationIcon from '@/components/OperationIcon'
import ProjectDetail from './projectDetail'
import Completed from './completed'
import {checkCompleted} from "@/api/projectManage/projectList";

export default {
  extends: WfMainUI,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    QueryTag,
    QueryConditionPanel,
    processPanel,
    ViewColumnsSelect,
    Comment,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    // 项目详情
    ProjectDetail,
    // 项目完结
    Completed
  },
  data() {
    return {
      wfForm: null,    // 工作流表单
      currentRow: null,      // 当前查看申请

      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false
      },
      metadata,

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        projectCode: '', // 项目编号
        projectName: '', // 项目名称
        projectType: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null,
        },
        projectStatus: {
          id: null,
          name: null,
          value: null,
          dictTypeId: null,
        },
// 所属部门
        department: {
          id: null,
          name: null,
          parentId: null,
          ids: null,
          code: null,
          companyId: null,
        },
      },
      tableData: [],
      table: {
        selected: null,
        selection: []
      },
// 查询表格列头
      tableColumns: {
        136: {
          title: '项目名称',
          field: 'projectName',
          visible: true,
          order: 0
        },
        138: {
          title: '项目编号',
          field: 'projectCode',
          visible: true,
          order: 1
        },
        137: {
          title: '项目简称',
          field: 'projectShortName',
          visible: true,
          order: 2
        },
        141: {
          title: '项目状态',
          field: 'projectStatus.name',
          visible: true,
          order: 3
        },
        148: {
          title: '所属部门',
          field: 'department.name',
          visible: true,
          order: 4
        },
        155: {
          title: '项目计划开始时间',
          field: 'planBeginDate',
          visible: true,
          order: 5
        },
        156: {
          title: '项目计划结束时间',
          field: 'planEndDate',
          visible: true,
          order: 6
        },
        144: {
          title: '客户',
          field: 'custom.name',
          visible: false,
          order: 7
        },
        146: {
          title: '部门.编号',
          field: 'department.code',
          visible: false,
          order: 8
        },
        151: {
          title: '项目类型',
          field: 'projectType.name',
          visible: false,
          order: 9
        },
        154: {
          title: '项目经理',
          field: 'projectManager.name',
          visible: false,
          order: 10
        },
        157: {
          title: '申请人',
          field: 'applyBy',
          visible: false,
          order: 11
        },
        158: {
          title: '申请部门',
          field: 'applyDepartmentId',
          visible: false,
          order: 12
        },
        159: {
          title: '申请日期',
          field: 'applyDate',
          visible: false,
          order: 13
        },
        162: {
          title: '立项商机',
          field: 'projectBusiOpp.name',
          visible: false,
          order: 14
        },
        163: {
          title: '项目核心成员',
          field: 'projectCoreMember',
          visible: false,
          order: 15
        },
        164: {
          title: '市场负责人',
          field: 'marketManager',
          visible: false,
          order: 16
        },
        165: {
          title: '客户姓名',
          field: 'customName',
          visible: false,
          order: 17
        },
        166: {
          title: '客户角色',
          field: 'customRole',
          visible: false,
          order: 18
        },
        167: {
          title: '项目总预算',
          field: 'projectBudget',
          visible: false,
          order: 19
        },
        168: {
          title: '项目计划投入总人月',
          field: 'projectBudgetPersonMonth',
          visible: false,
          order: 20
        },
        169: {
          title: '计划投入人力成本',
          field: 'planLaborCost',
          visible: false,
          order: 21
        },
        171: {
          title: '项目描述',
          field: 'projectDesc',
          visible: false,
          order: 22
        },
        176: {
          title: '流程状态',
          field: 'procStatus.name',
          visible: false,
          order: 23
        },
        177: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 24
        },
        178: {
          title: '创建者',
          field: 'createBy',
          visible: false,
          order: 25
        },
        179: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 26
        },
        180: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 27
        },
        181: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 28
        },
        114: {
          title: '操作',
          width: '140px',
          visible: true,
          order: 29
        },
      },
// 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 项目类型选项
      projectTypeOptions: [],
// 项目状态选项
      projectStatusOptions: [],
// 所属部门选项
      departmentOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 3,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '',      // 排序字段名
        order: ''            // 排序
      },
      searchList: [],
      tableId: '1740772218121937934',
      schemeId: '1740772218121937932',
      show: {
        projectDetail: false
      }
    }
  },
  computed: {},
  methods: {
    /**
     * 项目结项
     * @author Yogaa
     * @date 21:52 2024/1/3
     */
    handleProjectCompleted() {
      if (!this.table.selected) {
        this.$alert('请选择单条数据操作', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      checkCompleted(this.table.selected.id).then((responseData) => {
        if (responseData.code == 100) {
          if (!responseData.data) {
            this.$alert('选中项目中存在未完成的里程碑。', '提示', {
              confirmButtonText: '确定',
              type: 'info'
            })
          } else {
            this.$refs.completed.$emit('openDialog', this.table.selected.id)
          }
        } else {
          this.showMessage(responseData)
        }
      }).catch((error) => {
        this.outputError(error)
      })
    },
    handleSelectionChange(selection) {
      this.table.selection = selection
    },
    handleCurrentChange(row) {
      this.table.selected = row
    },
    reset() {
      this.isQueryConditionPanel ? this.$refs.conditionPanel.reset() : this.$refs.queryForm.resetFields()
      this.getList()
    },
    openProjectDetail(data) {
       this.$refs.projectDetail.init(data)
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {


        this.search.params.push({
          columnName: 'project_code',
          queryType: 'like',

          value: !validatenull(this.queryFormData.projectCode) ? this.queryFormData.projectCode : null

        })


        this.search.params.push({
          columnName: 'project_name',
          queryType: 'like',

          value: !validatenull(this.queryFormData.projectName) ? this.queryFormData.projectName : null

        })


        this.search.params.push({
          columnName: 'project_type',
          queryType: '=',

          value: !validatenull(this.queryFormData.projectType.id) ? this.queryFormData.projectType.id : null

        })


        this.search.params.push({
          columnName: 'project_status',
          queryType: '=',

          value: !validatenull(this.queryFormData.projectStatus.id) ? this.queryFormData.projectStatus.id : null

        })


        this.search.params.push({
          columnName: 'department_id',
          queryType: '=',

          value: !validatenull(this.queryFormData.department.id) ? this.queryFormData.department.id : null

        })


      }
      // 数据权限: 项目清单project_list
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listProjectListPage(this.search).then(responseData => {
        if (responseData.code == 100) {
          this.tableDataTotal = responseData.data.total
          this.tableData = responseData.data.rows ? responseData.data.rows : []
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    onSearch() {
      if (this.isQueryConditionPanel) {
        this.search.offset = 0
        this.tableDataPage = 1
        this.getList()
      } else {
        this.$refs['queryForm'].validate(valid => {
          if (valid) {
            this.search.offset = 0
            this.tableDataPage = 1
            this.getList()
          } else {
            return false
          }
        })
      }
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val;
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    async pageInit() {
      this.setLoad()
      this.initOptions(this.queryModel)
      this.onSearch()
      listResourcePermission(this.$route.meta.routerId).then(responseData => {
        if (responseData.code == 100) {
          this.permission.view = responseData.data.find(item => {
            return item.permission === 'projectList:read'
          })
          this.permission.export = responseData.data.find(item => {
            return item.permission === 'projectList:export'
          })

          this.permission.add = responseData.data.find(item => {
            return item.permission === 'projectList:create'
          })
          this.permission.edit = responseData.data.find(item => {
            return item.permission === 'projectList:update'
          })
          this.permission.remove = responseData.data.find(item => {
            return item.permission === 'projectList:delete'
          })

        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    onView(scope) {
      this.setLoad()
      getHistProcInstance(scope.row.procInst).then(responseData => {
        if (responseData.hasOwnProperty('processDefinitionId')) {
          let definitionId = responseData.processDefinitionId
          if (definitionId) {
            getStartForm(definitionId).then(startFormResp => {
              if (startFormResp.hasOwnProperty('key')) {
                if (startFormResp.key) {
                  this.currentRow = {
                    formKey: startFormResp.key,
                    definitionId: definitionId,
                    row: scope.row
                  }
                  this.loadWfForm(startFormResp.key)
                } else {
                  this.showMessage({type: 'warning', msg: '流程未配置form，请联系管理员。'})
                }
              } else {
                this.showMessage(startFormResp)
              }
              this.resetLoad()
            })
          } else {
            this.showMessage({type: 'warning', msg: '流程实例没有找到流程定义。'})
          }
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    // 重写dcMain的方法
    onShowHistory(scope) {
      this.$refs.commentForm.$emit('openComment', scope.row.procInst)
    },
    afterWfFormload() {
      this.$refs.wfForm.$emit('openViewBizDialog', this.currentRow.formKey, this.currentRow.definitionId, this.currentRow.row)
    },
    onVLoading(loading) {
      loading ? this.setLoad() : this.resetLoad()
    },
    onSortChange(orderby) {
      if (validatenull(orderby.prop)) {
        this.search.columnName = ''
        this.search.order = ''
      } else {
        this.search.columnName = orderby.prop
        this.search.order = orderby.order
      }
      this.getList()
    },


    listProjectTypeOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{
        'columnName': 'dict_type_id',
        'queryType': '=',
        'value': '1831069644912500819'
      }])


      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1740772218121937856")


      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.projectTypeOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


    listProjectStatusOptions() {

      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{
        'columnName': 'dict_type_id',
        'queryType': '=',
        'value': '1830767055876546591'
      }])


      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1740772218121937856")


      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.projectStatusOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


    listDepartmentOptions() {

      let search_List = {
        params: [{
          columnName: 'company_id',
          queryType: '=',
          value: currentUser.company.id === '0' ? '' : currentUser.company.id
        }]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])


      // 数据权限: 部门org_department
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1740772218121937911")


      treeDepartment(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.departmentOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },


    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.listProjectTypeOptions()
      this.listProjectStatusOptions()
      this.listDepartmentOptions()
    },
  },
  watch: {},
  mounted() {
    this.pageInit()
    // this.columnDrop(this.$refs.table)
  }
}
</script>
