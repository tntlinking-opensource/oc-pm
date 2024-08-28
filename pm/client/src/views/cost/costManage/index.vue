<template>
  <el-row class="dc-container" v-loading="loading">
    <edit-form ref="editForm" v-on:save-finished="getList" v-on:before-load="setLoad"
      v-on:after-load="resetLoad"></edit-form>
    <el-form ref="queryForm" :model="queryFormData" label-width="100px" class="dc-el-form_ElQueryForm">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="projectCode" label="项目编号" class="dc-el-form-item_SingleInput">
            <el-input v-model="queryFormData.projectCode" :maxLength="50" placeholder="请输入项目编号" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="projectName" label="项目名称" class="dc-el-form-item_SingleInput">
            <el-input v-model="queryFormData.projectName" :maxLength="21" placeholder="请输入项目名称" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="projectManager" label="项目经理" class="dc-el-form-item_SingleInput">
            <el-input v-model="queryFormData.projectManager" :maxLength="21" placeholder="请输入项目经理" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-search" v-on:click="onSearch"
            style="margin-left: 10px;">查询</el-button>
        </el-tooltip>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button icon="el-icon-refresh-right" v-on:click="reset">
            重置
          </el-button>
        </el-tooltip>
      </el-row>
    </el-form>
    <ux-grid column-key ref="table" :data="tableData" border v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable">
      <ux-table-column title="序号" :params="{ sortId: '161', summary: false }" type="index" tree-node resizable
        min-width="80px" fixed="left" align="center" header-align="center"
        class="dc-ux-table-column_ElTableNumColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['130'].field" :title="tableColumns['130'].title"
        :width="tableColumns['130'].width" :visible="tableColumns['130'].visible"
        :params="{ sortId: '130', summary: false }" tree-node resizable min-width="180px" align="left"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['131'].field" :title="tableColumns['131'].title"
        :width="tableColumns['131'].width" :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }" tree-node resizable min-width="180px" align="left"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"> <template
          slot-scope="scope">
          <span style="color: #018cb7;cursor: pointer;" @click="onView(scope)">{{ scope.row.projectCode }}</span>
        </template></ux-table-column>
      <ux-table-column :field="tableColumns['132'].field" :title="tableColumns['132'].title"
        :width="tableColumns['132'].width" :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }" tree-node resizable min-width="180px" align="left"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn">
      </ux-table-column>
      <ux-table-column :field="tableColumns['133'].field" :title="tableColumns['133'].title"
        :width="tableColumns['133'].width" :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }" tree-node resizable min-width="180px" align="left"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['134'].field" :title="tableColumns['134'].title"
        :width="tableColumns['134'].width" :visible="tableColumns['134'].visible" min-width="180px"
        :params="{ sortId: '134', summary: false }" tree-node resizable align="left" header-align="center" sortable
        show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['135'].field" :title="tableColumns['135'].title" min-width="180px"
        :width="tableColumns['135'].width" :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }" tree-node resizable align="left" header-align="center" sortable
        show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['137'].field" :title="tableColumns['137'].title" min-width="180px"
        :width="tableColumns['137'].width" :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }" tree-node resizable align="left" header-align="center" sortable
        show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['138'].field" :title="tableColumns['138'].title"
        :width="tableColumns['138'].width" :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }" tree-node resizable min-width="180px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column title="操作" :params="{ sortId: '114' }" tree-node resizable width="140px" min-width="140px"
        fixed="right" align="left" header-align="center" class="dc-ux-table-column_ElTableOptColumn">
        <template v-slot:header="scope">
          <span>操作</span>
          <OperationIcon v-on:click="onExport(tableData)" type="primary" content="导出excel文件" placement="top"
            icon-name="el-icon-download" class="dc-OperationIcon_IconButton"></OperationIcon>
        </template>
        <template slot-scope="scope">
          <OperationIcon v-on:click="onCreate(scope)" type="primary" content="新增" placement="top"
            icon-name="el-icon-plus" class="dc-OperationIcon_IconButton"></OperationIcon>
          <OperationIcon v-on:click="onEdit(scope)" type="primary" content="编辑" placement="top" icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"></OperationIcon>
        </template>
      </ux-table-column>
    </ux-grid>
    <el-pagination :total="tableDataTotal" :page-size="search.limit" background :current-page="tableDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"></el-pagination>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { projectList, getByProjectIdWithCheck } from '@/api/code/codeManage'
/** 根据用户界面配置import组件 开始 */
import EditForm from './form'
/** 根据用户界面配置import组件 结束 */
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    EditForm,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    ViewColumnsSelect
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        projectCode: '', // 项目编号
        projectName: '', // 项目名称
        projectManager: ''
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        161: {
          title: '序号',
          isShow: true,
          visible: true,
          order: 1
        },
        130: {
          title: '项目名称',
          field: 'projectName',
          visible: true,
          order: 1
        },
        131: {
          title: '项目编号',
          field: 'projectCode',
          visible: true,
          order: 2
        },
        132: {
          title: '项目简称',
          field: 'projectShortName',
          visible: true,
          order: 3
        },
        133: {
          title: '项目状态',
          field: 'projectStatus.name',
          visible: true,
          order: 4
        },
        134: {
          title: '项目经理',
          field: 'projectManager.name',
          visible: true,
          order: 5
        },
        135: {
          title: '可支配预算金额',
          field: 'projectBudget',
          visible: true,
          order: 6
        },
        137: {
          title: '项目计划开始日期',
          field: 'planBeginDate',
          visible: true,
          order: 7
        },
        138: {
          title: '项目计划结束日期',
          field: 'planEndDate',
          visible: true,
          order: 8
        },
        114: {
          title: '操作',
          width: '140px',
          visible: true,
          order: 10
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 1,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '',
      schemeId: '',
      selections: [],
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.queryFormData.projectCode) {
        this.search.params.push({
          "columnName": "project_code",
          "queryType": "like",
          "value": `%${this.queryFormData.projectCode}%`
        },)
      }
      if (this.queryFormData.projectName) {
        this.search.params.push({
          "columnName": "project_name",
          "queryType": "=",
          value: this.queryFormData.projectName
        },)
      }
      if (this.queryFormData.projectManager) {
        this.search.params.push({
          "columnName": "project_manager",
          "queryType": "like",
          "value": `%${this.queryFormData.projectManager}%`
        })
      }
      this.search.params.push({
        "columnName": "project_status",
        "queryType": "in",
        "value": [2, 3, 4]
      })
      projectList(this.search)
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
      this.search.offset = 0
      this.tableDataPage = 1
      this.getList()
    },
    reset() {
      this.queryFormData = {};
      this.getList();
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
      this.initOptions()
      this.onSearch()
    },
    onView(scope) {
      this.$refs.editForm.$emit('openViewDialog', scope.row)
    },
    onCreate(scope) {
      getByProjectIdWithCheck(scope.row.id).then(res => {
        if (res.code == '100') {
          this.$refs.editForm.$emit('openAddDialog', res.data)
        } else {
          this.$message.warning(res.msg);
        }
      })
    },
    onEdit(scope) {
      this.$refs.editForm.$emit('openEditDialog', scope.row)
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

    // 初始化自定义类型选择框选项变量
    initOptions(This) { }
  },
  watch: {},
  mounted() {
    this.pageInit()
    this.columnDrop(this.$refs.table)
  }
}
</script>
