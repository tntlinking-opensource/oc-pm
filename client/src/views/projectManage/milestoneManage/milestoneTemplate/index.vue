<template>
  <el-row class="dc-container" v-loading="loading">
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow" style="margin-bottom: 5px;">
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-edit" class="dc-el-button_Button" @click="beforeEdit">编辑</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-delete" class="dc-el-button_Button" @click="beforeDelete">删除</el-button>
      </el-tooltip>
    </el-row>
    <el-table ref="table" :data="tableData" border highlight-current-row @current-change="handleCurrentChange" @selection-change="handleSelectionChange" style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index" width="50" label="序号"></el-table-column>
      <el-table-column show-overflow-tooltip property="templateName" label="模板名称" min-width="120"></el-table-column>
      <el-table-column show-overflow-tooltip property="remarks" label="备注信息" min-width="120"></el-table-column>
      <el-table-column show-overflow-tooltip property="status.name" label="状态" min-width="120"></el-table-column>
      <el-table-column show-overflow-tooltip property="createBy" label="创建人" min-width="120"></el-table-column>
      <el-table-column show-overflow-tooltip property="createDate" label="创建时间" min-width="120"></el-table-column>
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

import { listMilestoneTemplatePage, bulkDeleteMilestoneTemplate } from '@/api/projectManage/milestoneTemplate'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import EditForm from './form'
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
    EditForm,
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
      version: 4,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1740772218121938467',
      schemeId: '1740772218121938465',
      table: {
        selected: null,
        selection: []
      }
    }
  },
  computed: {},
  methods: {
    handleSelectionChange(selection) {
      this.table.selection = selection
    },
    handleCurrentChange(row) {
      this.table.selected = row
      this.$emit('load-item', row)
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      // 数据权限: 里程碑模板milestone_template
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listMilestoneTemplatePage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total
            this.tableData = responseData.data.rows ? responseData.data.rows : []
            if (this.tableData.length > 0) {
              // 默认选中第一行数据
              let row = this.tableData[0]
              this.$refs.table.setCurrentRow(row)
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
              return item.permission === 'milestoneTemplate:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplate:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplate:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplate:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'milestoneTemplate:delete'
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
      if (this.table.selection.length === 0) {
        this.$alert('请选择数据后操作', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.onDelete(this.table.selection)
    },
    onDelete(templates) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        bulkDeleteMilestoneTemplate(templates)
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
      }).catch(() => {})
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
