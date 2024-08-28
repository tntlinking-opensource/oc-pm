<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>

    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="documentName" label="文档名称" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.documentName"
              :maxLength="50"
              placeholder="请输入文档名称"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="projectName" label="项目名称" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.projectName"
              :maxLength="21"
              placeholder="请输入项目名称"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12" style="display: flex; justify-content: flex-end">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-search" v-on:click="onSearch">查询</el-button>
          </el-tooltip>

          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button
              type="primary"
              icon="el-icon-refresh-right"
              v-on:click="reset">
              重置
            </el-button>
          </el-tooltip>
        </el-col>
        <el-col :span="3">

        </el-col>
      </el-row>
    </el-form>
    <el-row >
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-edit" v-on:click="onEditbut" class="dc-el-button_Button">编辑</el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-delete" v-on:click="onDeletebut" class="dc-el-button_Button">
          删除
        </el-button>
      </el-tooltip>
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      @selection-change="handleRowClick"
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        :params="{ sortId: '160', summary: false }"
        type="checkbox"
        tree-node
        resizable
        min-width="80px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableCheckBoxColumn"
      ></ux-table-column>
      <ux-table-column
        title="序号"
        :params="{ sortId: '161', summary: false }"
        type="index"
        tree-node
        resizable
        min-width="80px"
        fixed="left"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableNumColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['129'].field"
        :title="tableColumns['129'].title"
        :width="tableColumns['129'].width"
        :visible="tableColumns['129'].visible"
        :params="{ sortId: '129', summary: false }"
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
        :field="tableColumns['130'].field"
        :title="tableColumns['130'].title"
        :width="tableColumns['130'].width"
        :visible="tableColumns['130'].visible"
        :params="{ sortId: '130', summary: false }"
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
        :field="tableColumns['131'].field"
        :title="tableColumns['131'].title"
        :width="tableColumns['131'].width"
        :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }"
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
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
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
        :field="tableColumns['133'].field"
        :title="tableColumns['133'].title"
        :width="tableColumns['133'].width"
        :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }"
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
        :field="tableColumns['134'].field"
        :title="tableColumns['134'].title"
        :width="tableColumns['134'].width"
        :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }"
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
        :field="tableColumns['135'].field"
        :title="tableColumns['135'].title"
        :width="tableColumns['135'].width"
        :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }"
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
        :field="tableColumns['137'].field"
        :title="tableColumns['137'].title"
        :width="tableColumns['137'].width"
        :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
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
        :field="tableColumns['138'].field"
        :title="tableColumns['138'].title"
        :width="tableColumns['138'].width"
        :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['136'].field"
        :title="tableColumns['136'].title"
        :width="tableColumns['136'].width"
        :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row }">
          <span style="cursor: pointer; color: #018CB7" @click='onUploadViewFile(1, row)'>{{ row.annexName }}</span>
        </template>
      </ux-table-column>
      <ux-table-column
        title="操作"
        :params="{ sortId: '114' }"
        tree-node
        resizable
        width="140px"
        min-width="140px"
        fixed="right"
        align="left"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template v-slot:header="scope">
          <span>操作</span>
          <OperationIcon
            v-on:click="onExport(tableData)"
            type="primary"
            v-show="permission.export"
            content="导出excel文件"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
        </template>
        <template slot-scope="scope">
          <OperationIcon
            v-on:click="onView(scope)"
            type="info"
            v-show="permission.view"
            content="查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
         <!-- <OperationIcon
            v-on:click="onEdit(scope)"
            type="primary"
            v-show="permission.edit"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onCopy(scope)"
            type="primary"
            v-show="permission.add"
            content="复制"
            placement="top"
            icon-name="el-icon-document"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="permission.remove"
            content="删除"
            placement="top"
            icon-name="el-icon-delete"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="info"
            v-show="permission.view"
            content="历史记录"
            placement="top"
            icon-name="el-icon-info"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>-->
        </template>
      </ux-table-column>
    </ux-grid>
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

import { listDocumentManagePage, deleteDocumentManage } from '@/api/documentManage/documentManage'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import EditForm from './form'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
import { fileUploadById } from '@/api/sys/sysFile'
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
      queryFormData: {
        documentName: '', // 文档名称
        projectName: '' // 项目名称
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        160: {
          isShow: true,
          visible: true,
          order: 0
        },
        161: {
          title: '序号',
          isShow: true,
          visible: true,
          order: 1
        },
        129: {
          title: '文档名称',
          field: 'documentName',
          visible: true,
          order: 0
        },
        130: {
          title: '项目名称',
          field: 'projectName',
          visible: true,
          order: 1
        },
        131: {
          title: '所属里程碑',
          field: 'milestoneName',
          visible: true,
          order: 2
        },
        132: {
          title: '所属日程',
          field: 'scheduleName',
          visible: true,
          order: 3
        },
        133: {
          title: '文档类型',
          field: 'documenttypeName',
          visible: true,
          order: 4
        },
        134: {
          title: '关联需求',
          field: 'requirementName',
          visible: true,
          order: 5
        },
        135: {
          title: '文档描述',
          field: 'documentDescription',
          visible: true,
          order: 6
        },
        137: {
          title: '创建人',
          field: 'createBy',
          visible: false,
          order: 7
        },
        138: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 8
        },
        136: {
          title: '附件',
          field: 'annexName',
          visible: true,
          order: 9
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
      version: 6,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1740772218121938429',
      schemeId: '1740772218121938427',
      selections: [],
    }
  },
  computed: {},
  methods: {
    onUploadViewFile(index, row) { // 下载
      const fileids = row.annexId.split(',');
      const annexNames = row.annexName.split(',');
      console.log(fileids)
      for(let a = 0; a < fileids.length; a++){
        console.log(fileids[a])
        fileUploadById(fileids[a]).then(responseData => {
        const content = responseData;
        this.download(content, annexNames[a]);
      }).catch(error => {
        this.outputError(error)
      });
      }
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
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'document_name',
          queryType: 'like',

          value: !validatenull(this.queryFormData.documentName) ? this.queryFormData.documentName : null
        })

        this.search.params.push({
          columnName: 'pl.project_Name',
          queryType: 'like',

          value: !validatenull(this.queryFormData.projectName) ? this.queryFormData.projectName : null
        })
      }
      // 数据权限: 文档管理document_manage
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listDocumentManagePage(this.search)
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
      this.selections = [];
    },
    reset() {
      this.isQueryConditionPanel ? this.$refs.conditionPanel.reset() : this.$refs.queryForm.resetFields()
      this.getList()
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
      this.selections = [];
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
              return item.permission === 'documentManage:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'documentManage:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'documentManage:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'documentManage:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'documentManage:delete'
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
    onEdit(scope) {
      this.$refs.editForm.$emit('openEditDialog', scope.row.id)
    },
    handleRowClick (row) {
      this.selections = row;
    },
    onEditbut(){
      if (!this.selections.length) {
        /*this.showMessage({
          type: 'error',
          msg: '请先选择项目'
        });*/
        this.$message({
          type: 'warning',
          message: '请先选择项目'
        });
        return;
      }
      if (this.selections.length > 1) {
        /*this.showMessage({
          type: 'error',
          msg: '请选择单个数据编辑'
        });*/
        this.$message({
          type: 'warning',
          message: '请选择单个数据编辑'
        });

        return;
      }
      this.$refs.editForm.$emit('openEditDialog',this.selections[0].id)
    },
    onCopy(scope) {
      this.$refs.editForm.$emit('openCopyDialog', scope.row.id)
    },
    onDelete(scope) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          deleteDocumentManage(scope.row)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.getList()
                /*this.showMessage({
                  type: 'success',
                  msg: '删除成功'
                })*/
                this.$message({
                  type: 'success',
                  message: '删除成功'
                });
                this.selections = [];
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

    onDeletebut(){
      let lg = 0;
      console.log(this.selections.length)
      if (!this.selections.length) {
        /*this.showMessage({
          type: 'error',
          msg: '请先选择项目'
        });*/
        this.$message({
          type: 'warning',
          message: '请先选择项目'
        });
        return;
      }
      this.$confirm('确定删除所选数据？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          for (let i = 0; i < this.selections.length; i++) {
            deleteDocumentManage(this.selections[i])
            .then((responseData) => {
                if (responseData.code == 100) {
                  lg += 1;
                }else {
                  this.showMessage(responseData)
                }
                if (i == (this.selections.length-1)){
                  this.getList()
                  /*this.showMessage({
                    type: 'success',
                    msg: '删除成功'
                  })*/
                  this.$message({
                    type: 'success',
                    message: '删除成功'
                  });
                }
              this.resetLoad()
              })
                .catch((error) => {
                  this.outputError(error)
                })
          }
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
