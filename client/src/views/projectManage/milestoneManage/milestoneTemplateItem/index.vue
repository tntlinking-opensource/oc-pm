<template>
  <el-row class="dc-container" v-loading="loading">
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
      :template="template || {}"
    ></edit-form>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow" style="margin-bottom: 5px;">
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-edit" v-on:click="beforeEdit">编辑</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-delete" v-on:click="beforeDelete">删除</el-button>
      </el-tooltip>
    </el-row>
    <el-table :data="tableData" border highlight-current-row @current-change="handleCurrentChange" @selection-change="handleSelectionChange" style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index" width="50" label="序号"></el-table-column>
      <el-table-column show-overflow-tooltip property="milestoneName" label="里程碑名称" min-width="120"></el-table-column>
      <el-table-column show-overflow-tooltip property="progressRate" label="进度占比" min-width="120"></el-table-column>
      <el-table-column show-overflow-tooltip property="milestoneDesc" label="描述" min-width="120"></el-table-column>
      <el-table-column show-overflow-tooltip property="sort" label="顺序" min-width="120"></el-table-column>
      <el-table-column label="操作" width="90">
        <template slot-scope="scope">
          <OperationIcon
            v-on:click="onView(scope)"
            type="info"
            content="查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onEdit(scope.row)"
            type="primary"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onDelete([scope.row])"
            type="danger"
            content="删除"
            placement="top"
            icon-name="el-icon-delete"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :total="tableDataTotal"
      :page-size="search.limit"
      background
      :current-page="tableDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import {
  listMilestoneTemplateItemPage,
  bulkDeleteMilestoneTemplateItem
} from '@/api/projectManage/milestoneTemplateItem'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import EditForm from './form'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    EditForm,
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon
  },
  data() {
    return {
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
      tableId: '1740772218121938502',
      schemeId: '1740772218121938500',
      table: {
        selected: null,
        selection: []
      },
      template: null
    }
  },
  computed: {},
  methods: {
    loadTable(template) {
      this.template = template
      this.getList()
    },
    handleSelectionChange(selection) {
      this.table.selection = selection
    },
    /**
     * 表格行点击事件
     * @author Yogaa
     * @date 16:42 2023/12/23
     */
    handleCurrentChange(row) {
      this.table.selected = row
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      // 没有模板，不加载模板详情
      if (!this.template) {
        this.resetLoad()
        return
      }
      this.search.params.push({
        'columnName': 'template_id',
        'queryType': '=',
        'value': this.template.id
      })
      // 数据权限: 里程碑模板详情milestone_template_item
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listMilestoneTemplateItemPage(this.search)
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
      this.getList()
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
              return item.permission === 'milestoneTemplateItem:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplateItem:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplateItem:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplateItem:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplateItem:delete'
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
      this.$refs.editForm.$emit('openViewDialog', scope.row.id)
    },
    onCreate() {
      if (validatenull(this.template)) {
        this.$alert('请选择模板', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.$refs.editForm.$emit('openAddDialog')
    },
    beforeEdit() {
      if (this.table.selection.length !== 1) {
        this.$alert('请选择单条数据操作', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.onEdit(this.table.selection[0])
    },
    onEdit(row) {
      this.$refs.editForm.$emit('openEditDialog', row.id)
    },
    beforeDelete() {
      if (this.table.selection.length !== 1) {
        this.$alert('请选择单条数据操作', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.onDelete(this.table.selection)
    },
    onDelete(items) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          bulkDeleteMilestoneTemplateItem(items)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.getList()
                this.showMessage({
                  type: 'success',
                  msg: '删除成功'
                })
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        })
        .catch(() => {})
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
    initOptions(This) {}
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
