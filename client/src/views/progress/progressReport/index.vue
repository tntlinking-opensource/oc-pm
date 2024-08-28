<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>

    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      class="dc-el-form_ElQueryForm"
    >
    <el-col :span="6">
          <el-form-item prop="" label="项目编号：" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.projectCode"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="" label="项目名称：" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.projectName"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="" label="汇报人：" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.reporter"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6" class="dc-el-form-item_SelectInput">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="getList" style="margin-left:20px" icon="el-icon-search"
                       class="dc-el-button_Button" type="primary">查询</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button icon="el-icon-refresh" class="dc-el-button_Button" @click="handleReset">重置</el-button>
          </el-tooltip>
        </el-col>
    </el-form>


    <el-row gutter="0" type="flex" align="top" style="top: 40px;margin: 10px 0;" class="dc-el-row_ElRow">
      <process-panel
        ref="processPanel"
        :bizAdd="permission.add"
        :tenantId="currentUser.company.id"
        v-on:save-finished="getList"
        keyPrefix="PROC_PROGRESS_"
        procName="项目汇报审批流程"
        formKey="wf/common/wfForm.vue#progress/progressReport/form.vue"
        procTitile="${start_user_name}发起${form.name}进度汇报审批"
        v-on:v-loading="onVLoading"
        class="dc-process-panel_ProcessPanel"
      ></process-panel>
    </el-row>


    <ux-grid
      column-key
      ref="table"
      style="margin-top: 20px;"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable"
    >

    <ux-table-column
        title="序号"
        :params="{ sortId: '136', summary: false }"
        type="index"
        tree-node
        resizable
        min-width="80px"
        fixed="left"
        align="left"
        header-align="center"
        class="dc-ux-table-column_ElTableNumColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['137'].field"
        :title="tableColumns['137'].title"
        :width="tableColumns['137'].width"
        :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['138'].field"
        :title="tableColumns['138'].title"
        :width="tableColumns['138'].width"
        :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['139'].field"
        :title="tableColumns['139'].title"
        :width="tableColumns['139'].width"
        :visible="tableColumns['139'].visible"
        :params="{ sortId: '139', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="scope">
          <span style="cursor: pointer;color:#018CB7;" @click="onView(scope)">{{ scope.row.reportingTheme }}</span>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['140'].field"
        :title="tableColumns['140'].title"
        :width="tableColumns['140'].width"
        :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['141'].field"
        :title="tableColumns['141'].title"
        :width="tableColumns['141'].width"
        :visible="tableColumns['141'].visible"
        :params="{ sortId: '141', summary: false }"
        tree-node
        resizable
        min-width="80px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['142'].field"
        :title="tableColumns['142'].title"
        :width="tableColumns['142'].width"
        :visible="tableColumns['142'].visible"
        :params="{ sortId: '142', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['143'].field"
        :title="tableColumns['143'].title"
        :width="tableColumns['143'].width"
        :visible="tableColumns['143'].visible"
        :params="{ sortId: '143', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['144'].field"
        :title="tableColumns['144'].title"
        :formatter="formatProgressStatus"
        :width="tableColumns['144'].width"
        :visible="tableColumns['144'].visible"
        :params="{ sortId: '144', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['145'].field"
        :title="tableColumns['145'].title"
        :width="tableColumns['145'].width"
        :visible="tableColumns['145'].visible"
        :params="{ sortId: '145', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['146'].field"
        :title="tableColumns['146'].title"
        :width="tableColumns['146'].width"
        :visible="tableColumns['146'].visible"
        :params="{ sortId: '146', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      
      <ux-table-column
        title="操作"
        :params="{ sortId: '147' }"
        tree-node
        resizable
        width="100px"
        min-width="100px"
        fixed="right"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template v-slot:header="scope">
          <span>操作</span>
          <!-- <OperationIcon
            v-on:click="onExport(tableData)"
            type="primary"
            v-show="permission.export"
            content="导出excel文件"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> -->
        </template>
        <template slot-scope="scope">
          <OperationIcon v-on:click='onView(scope)' type='info' v-show='permission.view' content='查看' placement='top'
                         icon-name='el-icon-view' class='dc-OperationIcon_IconButton'></OperationIcon>
          <OperationIcon v-on:click='onShowHistory(scope)' type='info' v-show='permission.view' content='历史记录'
                         placement='top' icon-name='el-icon-info' class='dc-OperationIcon_IconButton'></OperationIcon>
        </template>
      </ux-table-column>
    </ux-grid>
    <el-pagination
      :total="tableDataTotal"
      :page-size="search.limit"
      background
      :current-page="tableDataPage"
      :page-sizes="[10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
    <comment ref="commentForm" class="dc-comment_ElImport"></comment>
    <component
      ref="wfForm"
      v-on:save-finished="getTaskList()"
      v-on:pro-create="getList()"
      v-on:after-wfForm-load="afterWfFormload()"
      :is="wfForm"
      class="dc-component_ElImport"
    ></component>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { listProgressReportPage, deleteProgressReport ,getProjectProgressByProjectId} from '@/api/progress/progressReport'

import { listResourcePermission } from '@/api/admin/common/permission'
import { getHistProcInstance } from '@/api/wf/procinst'
import { getProcessByKey, getStartForm } from '@/api/wf/processdefinition'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import processPanel from '@/views/wf/common/processPanel'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
import Comment from '@/views/wf/common/comment'
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import WfMainUI from '@/views/wf/common/wfMainUI'
import OperationIcon from '@/components/OperationIcon'
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
    OperationIcon
  },
  data() {
    return {
      wfForm: null, // 工作流表单
      currentRow: null, // 当前查看申请

      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false
      },
      metadata,

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {},
      tableData: [],
      // 查询表格列头
      tableColumns: {
        136: {
          title: '序号',
          isShow: true,
          visible: true,
          order: 1
        },
        137: {
          title: '项目名称',
          field: 'project.projectName',
          visible: true,
          width:150,
          order: 1
        },
        138: {
          title: '项目编号',
          field: 'project.projectCode',
          visible: true,
          order: 2
        },
        139: {
          title: '汇报主题',
          field: 'reportingTheme',
          width:200,
          visible: true,
          order: 3
        },
        140: {
          title: '项目状态',
          field: 'project.projectStatus.name',
          visible: true,
          order: 4
        },
        141: {
          title: '项目经理',
          field: 'project.projectManager.name',
          visible: true,
          order: 5
        },
        142: {
          title: '项目计划开始日期',
          field: 'project.planBeginDate',
          width:150,
          visible: true,
          order: 6
        },
        143: {
          title: '项目计划结束日期',
          field: 'project.planEndDate',
          width:150,
          visible: true,
          order: 7
        },
        144: {
          title: '汇报状态',
          field: 'progressStatus',
          width:80,
          visible: true,
          order: 8
        },
        145: {
          title: '汇报人',
          field: 'reporter.name',
          visible: true,
          width:80,
          order: 8
        },
        146: {
          title: '汇报时间',
          field: 'createDate',
          visible: true,
          width:150,
          order: 9
        }

      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 2,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1740772218121938609',
      schemeId: '1740772218121938607'
    }
  },
  computed: {},
  methods: {
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
          this.showMessage({
            type: 'warning',
            msg: '流程未配置form,请联系管理员。'
          })
          }
        } else {
          this.showMessage(startFormResp)
        }
        this.resetLoad()
        })
      } else {
        this.showMessage({
        type: 'warning',
        msg: '流程实例没有找到流程定义。'
        })
      }
      } else {
      this.showMessage(responseData)
      }
      this.resetLoad()
    }).catch(error => {
      this.outputError(error)
    })
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []


      // 数据权限: 进度汇报progress_report
      // this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)

      if (this.queryFormData.projectCode) {
        this.search.params.push({
          "columnName": "project.project_code",
          "queryType": "like",
          "value": `%${this.queryFormData.projectCode}%`
        })
      }
      if (this.queryFormData.projectName) {
        this.search.params.push({
          "columnName": "project.project_name",
          "queryType": "like",
          "value": `${this.queryFormData.projectName}`
        })
      }
      if (this.queryFormData.reporter) {
        this.search.params.push({
          "columnName": "reporter.name",
          "queryType": "=",
          "value": `${this.queryFormData.reporter}`
        })
      }

      listProgressReportPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total
            this.tableData = responseData.data.rows ? responseData.data.rows : []
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onSearch() {
      if (this.isQueryConditionPanel) {
        this.search.offset = 0
        this.tableDataPage = 1
        this.getList()
      } else {
        this.$refs['queryForm'].validate((valid) => {
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
      this.search.limit = val
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
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === 'progressReport:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'progressReport:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'progressReport:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'progressReport:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'progressReport:delete'
            })
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onView(scope) {
      this.setLoad()
      getHistProcInstance(scope.row.procInst)
        .then((responseData) => {
          if (responseData.hasOwnProperty('processDefinitionId')) {
            let definitionId = responseData.processDefinitionId
            if (definitionId) {
              getStartForm(definitionId).then((startFormResp) => {
                if (startFormResp.hasOwnProperty('key')) {
                  if (startFormResp.key) {
                    this.currentRow = {
                      formKey: startFormResp.key,
                      definitionId: definitionId,
                      row: scope.row
                    }
                    this.loadWfForm(startFormResp.key)
                  } else {
                    this.showMessage({
                      type: 'warning',
                      msg: '流程未配置form,请联系管理员。'
                    })
                  }
                } else {
                  this.showMessage(startFormResp)
                }
                this.resetLoad()
              })
            } else {
              this.showMessage({
                type: 'warning',
                msg: '流程实例没有找到流程定义。'
              })
            }
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
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
      console.log(loading, '------------');
      loading ? this.setLoad() : this.resetLoad()
    },
    onDelete(scope) {

      const { id, updateDate, progressStatus } = scope.row
      if (String(progressStatus) !== '0') {
        this.$message({
            type: 'warning',
            message: '无法删除当前汇报！'
          })
      } else {
        this.$msgbox.confirm('确认删除该信息？\n' +
          '\n' +
          '删除后将无法恢复，请谨慎删除！', '提示').then(() => {
          deleteProgressReport({id, updateDate}).then(() => {
            this.$emit('save-finished')
            this.$message('操作成功')
            this.getList()
          })
        })
      }
       
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

    formatProgressStatus(row) {
      console.log(row.cellValue);
      switch(String(row.cellValue)) {
        case '0':
          return '已申请';
        case '1':
          return '已上报';
        default:
          return row.cellValue;
      }
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {}
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
