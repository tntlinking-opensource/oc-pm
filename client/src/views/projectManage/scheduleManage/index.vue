<template>
  <el-row class="dc-container schedule-manage" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
      :data="selectMilestone || {}"
    ></edit-form>
    <el-container direction="horizontal" class="dc-el-container_ElContainer">
      <el-aside width="200px" height="60px">
        <el-row>
          <el-input placeholder="请输入项目" v-model="leftSearch.name">
            <i class="el-icon-search el-input__icon" slot="suffix" @click="doTreeSearch"></i>
          </el-input>
        </el-row>
        <el-row>
          <el-tree ref="tree" :data="projects" :props="treeProps" :render-content="treeRender" @node-click="handleTreeNodeClick" :filter-node-method="filterNode"></el-tree>
        </el-row>
      </el-aside>
      <el-main>
        <el-row>
<!--          <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">-->
<!--            <el-col :span="24">-->
<!--              <query-tag-->
<!--                ref="queryTag"-->
<!--                v-model="queryEditObject"-->
<!--                :moreParm="moreParm"-->
<!--                :routerId="$route && $route.meta.routerId"-->
<!--                :isQueryConditionPanel="isQueryConditionPanel"-->
<!--                v-on:queryTagPanel="onQueryTagPanel"-->
<!--                v-on:queryTagClick="onQueryTagClick"-->
<!--                v-on:search="onSearch"-->
<!--                class="dc-query-tag_ElConditionTag"-->
<!--              ></query-tag>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <div style="text-align: left">-->
<!--                <el-button-group class="dc-el-button-group_ButtonGroup">-->
<!--                  <el-tooltip effect="dark" content="搜索" placement="top">-->
<!--                    <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>-->
<!--                  </el-tooltip>-->
<!--                  <el-tooltip effect="dark" content="重置" placement="top">-->
<!--                    <el-button-->
<!--                      v-on:click="reset"-->
<!--                      type="primary"-->
<!--                      icon="el-icon-refresh-right"-->
<!--                    ></el-button>-->
<!--                  </el-tooltip>-->
<!--                  <el-tooltip effect="dark" content="更多" placement="top">-->
<!--                    <el-button-->
<!--                      v-on:click="onQueryConditionPanel('query')"-->
<!--                      type="primary"-->
<!--                      icon="el-icon-d-arrow-right"-->
<!--                    ></el-button>-->
<!--                  </el-tooltip>-->
<!--                </el-button-group>-->
<!--              </div>-->
<!--            </el-col>-->
<!--          </el-row>-->
<!--          <query-condition-panel-->
<!--            v-if="isQueryConditionPanel"-->
<!--            ref="conditionPanel"-->
<!--            :conditionColumns="-->
<!--              metadata.find((item) => {-->
<!--                return item.type == 'main'-->
<!--              }).conditionPanel-->
<!--            "-->
<!--            v-model="moreParm"-->
<!--            :routerId="$route.meta.routerId"-->
<!--            class="dc-query-condition-panel_ElConditionPanel"-->
<!--          ></query-condition-panel>-->
          <el-form
            ref="queryForm"
            :model="queryFormData"
            label-width="100px"
            v-if="!isQueryConditionPanel"
            class="dc-el-form_ElQueryForm"
          >
            <el-row>
              <el-col :span="6">
                <el-form-item prop="scheduleName" label="日程名称" class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="queryFormData.scheduleName"
                    :maxLength="50"
                    placeholder="请输入日程名称"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow" style="margin-bottom: 5px;">
            <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
            </el-tooltip>
            <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-edit" v-on:click="beforeEdit">编辑</el-button>
            </el-tooltip>
            <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-delete" v-on:click="bulkDelete">删除</el-button>
            </el-tooltip>
            <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
              <el-button type="primary" v-on:click="handleExecSchedule">执行任务</el-button>
            </el-tooltip>
            <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
              <el-button type="danger" v-on:click="handleCancelSchedule">取消执行</el-button>
            </el-tooltip>
            <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
              <el-button type="primary" v-on:click="handleCompleteSchedule">完成任务</el-button>
            </el-tooltip>
            <el-button type='primary' icon='el-icon-search' class='dc-el-button_Button' @click="onSearch">搜索</el-button>
            <el-button type='primary' icon='el-icon-refresh-right' class='dc-el-button_Button' @click="reset">重置</el-button>
          </el-row>
          <el-table ref="table" :data="tableData" border highlight-current-row @current-change="handleCurrentChange" @selection-change="handleSelectionChange" style="width: 100%">
            <el-table-column align="center" fixed="left" type="selection" width="55"></el-table-column>
            <el-table-column align="center" type="index" width="50" label="序号"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="scheduleName" label="日程名称" min-width="180"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="scheduleStatus.name" label="状态" min-width="60"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="scheduleType.name" label="日程类型" min-width="80"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="planBegin" label="计划开始日期" min-width="100"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="planEnd" label="计划结束日期" min-width="100"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="realBegin" label="实际开始日期" min-width="100"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="realEnd" label="实际结束日期" min-width="100"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="participants" label="参与人" min-width="100"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="scheduleDesc" label="日程描述" min-width="160"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="createBy" label="创建人" min-width="120"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="createDate" label="创建时间" min-width="160"></el-table-column>
            <el-table-column align="center" label="操作" width="90" fixed="right">
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
                  v-on:click="beforeDelete(scope.row)"
                  type="danger"
                  content="删除"
                  placement="top"
                  icon-name="el-icon-delete"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
              </template>
            </el-table-column>
          </el-table>
<!--          <ux-grid-->
<!--            column-key-->
<!--            ref="table"-->
<!--            :data="tableData"-->
<!--            border-->
<!--            v-on:sort-change="onSortChange"-->
<!--            v-on:header-dragend="onWidthChange($refs.table)"-->
<!--            :tree-config="{-->
<!--              children: '',-->
<!--              indent: 20,-->
<!--              accordion: false,-->
<!--              line: false,-->
<!--              showIcon: true,-->
<!--              iconOpen: '',-->
<!--              iconClose: ''-->
<!--            }"-->
<!--            class="dc-ux-grid_QueryTable"-->
<!--          >-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['129'].field"-->
<!--              :title="tableColumns['129'].title"-->
<!--              :width="tableColumns['129'].width"-->
<!--              :visible="tableColumns['129'].visible"-->
<!--              :params="{ sortId: '129', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="180px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['132'].field"-->
<!--              :title="tableColumns['132'].title"-->
<!--              :width="tableColumns['132'].width"-->
<!--              :visible="tableColumns['132'].visible"-->
<!--              :params="{ sortId: '132', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['135'].field"-->
<!--              :title="tableColumns['135'].title"-->
<!--              :width="tableColumns['135'].width"-->
<!--              :visible="tableColumns['135'].visible"-->
<!--              :params="{ sortId: '135', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['136'].field"-->
<!--              :title="tableColumns['136'].title"-->
<!--              :width="tableColumns['136'].width"-->
<!--              :visible="tableColumns['136'].visible"-->
<!--              :params="{ sortId: '136', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="right"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['137'].field"-->
<!--              :title="tableColumns['137'].title"-->
<!--              :width="tableColumns['137'].width"-->
<!--              :visible="tableColumns['137'].visible"-->
<!--              :params="{ sortId: '137', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="right"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['138'].field"-->
<!--              :title="tableColumns['138'].title"-->
<!--              :width="tableColumns['138'].width"-->
<!--              :visible="tableColumns['138'].visible"-->
<!--              :params="{ sortId: '138', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="right"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['139'].field"-->
<!--              :title="tableColumns['139'].title"-->
<!--              :width="tableColumns['139'].width"-->
<!--              :visible="tableColumns['139'].visible"-->
<!--              :params="{ sortId: '139', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="right"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['141'].field"-->
<!--              :title="tableColumns['141'].title"-->
<!--              :width="tableColumns['141'].width"-->
<!--              :visible="tableColumns['141'].visible"-->
<!--              :params="{ sortId: '141', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="180px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['140'].field"-->
<!--              :title="tableColumns['140'].title"-->
<!--              :width="tableColumns['140'].width"-->
<!--              :visible="tableColumns['140'].visible"-->
<!--              :params="{ sortId: '140', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="180px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['143'].field"-->
<!--              :title="tableColumns['143'].title"-->
<!--              :width="tableColumns['143'].width"-->
<!--              :visible="tableColumns['143'].visible"-->
<!--              :params="{ sortId: '143', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="180px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['144'].field"-->
<!--              :title="tableColumns['144'].title"-->
<!--              :width="tableColumns['144'].width"-->
<!--              :visible="tableColumns['144'].visible"-->
<!--              :params="{ sortId: '144', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="right"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['142'].field"-->
<!--              :title="tableColumns['142'].title"-->
<!--              :width="tableColumns['142'].width"-->
<!--              :visible="tableColumns['142'].visible"-->
<!--              :params="{ sortId: '142', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="180px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['145'].field"-->
<!--              :title="tableColumns['145'].title"-->
<!--              :width="tableColumns['145'].width"-->
<!--              :visible="tableColumns['145'].visible"-->
<!--              :params="{ sortId: '145', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="180px"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              :field="tableColumns['146'].field"-->
<!--              :title="tableColumns['146'].title"-->
<!--              :width="tableColumns['146'].width"-->
<!--              :visible="tableColumns['146'].visible"-->
<!--              :params="{ sortId: '146', summary: false }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              min-width="120px"-->
<!--              align="right"-->
<!--              header-align="center"-->
<!--              sortable-->
<!--              show-overflow-->
<!--              class="dc-ux-table-column_ElTableColumn"-->
<!--            ></ux-table-column>-->
<!--            <ux-table-column-->
<!--              title="操作"-->
<!--              :params="{ sortId: '119' }"-->
<!--              tree-node-->
<!--              resizable-->
<!--              width="160px"-->
<!--              min-width="160px"-->
<!--              fixed="right"-->
<!--              align="left"-->
<!--              header-align="center"-->
<!--              class="dc-ux-table-column_ElTableOptColumn"-->
<!--            >-->
<!--              <template v-slot:header="scope">-->
<!--                <span>操作</span>-->
<!--                <el-popover-->
<!--                  ref="popover"-->
<!--                  width="10"-->
<!--                  trigger="click"-->
<!--                  placement="bottom"-->
<!--                  offset="10"-->
<!--                  class="dc-el-popover_ElPopover"-->
<!--                >-->
<!--                  <view-columns-select-->
<!--                    v-model="tableColumns"-->
<!--                    ref="viewColumnsSelect"-->
<!--                    v-on:save-column-view="saveColumn($event, 'table')"-->
<!--                    v-on:show-all-column="showAllColumn($event, 'table')"-->
<!--                    v-on:show-default-column="showDefaultColumn('table')"-->
<!--                    class="dc-view-columns-select_ViewColumnsSelect"-->
<!--                  ></view-columns-select>-->
<!--                  <template slot="reference">-->
<!--                    <OperationIcon-->
<!--                      type="primary"-->
<!--                      content="自定义列"-->
<!--                      placement="top"-->
<!--                      icon-name="el-icon-tickets"-->
<!--                    ></OperationIcon>-->
<!--                  </template>-->
<!--                </el-popover>-->
<!--                <OperationIcon-->
<!--                  v-on:click="onExport(tableData)"-->
<!--                  type="primary"-->
<!--                  v-show="permission.export"-->
<!--                  content="导出excel文件"-->
<!--                  placement="top"-->
<!--                  icon-name="el-icon-download"-->
<!--                  class="dc-OperationIcon_IconButton"-->
<!--                ></OperationIcon>-->
<!--              </template>-->
<!--              <template slot-scope="scope">-->
<!--                <OperationIcon-->
<!--                  v-on:click="onView(scope)"-->
<!--                  type="info"-->
<!--                  v-show="permission.view"-->
<!--                  content="查看"-->
<!--                  placement="top"-->
<!--                  icon-name="el-icon-view"-->
<!--                  class="dc-OperationIcon_IconButton"-->
<!--                ></OperationIcon>-->
<!--                <OperationIcon-->
<!--                  v-on:click="onEdit(scope)"-->
<!--                  type="primary"-->
<!--                  v-show="permission.edit"-->
<!--                  content="编辑"-->
<!--                  placement="top"-->
<!--                  icon-name="el-icon-edit"-->
<!--                  class="dc-OperationIcon_IconButton"-->
<!--                ></OperationIcon>-->
<!--                <OperationIcon-->
<!--                  v-on:click="onCopy(scope)"-->
<!--                  type="primary"-->
<!--                  v-show="permission.add"-->
<!--                  content="复制"-->
<!--                  placement="top"-->
<!--                  icon-name="el-icon-document"-->
<!--                  class="dc-OperationIcon_IconButton"-->
<!--                ></OperationIcon>-->
<!--                <OperationIcon-->
<!--                  v-on:click="onDelete(scope)"-->
<!--                  type="danger"-->
<!--                  v-show="permission.remove"-->
<!--                  content="删除"-->
<!--                  placement="top"-->
<!--                  icon-name="el-icon-delete"-->
<!--                  class="dc-OperationIcon_IconButton"-->
<!--                ></OperationIcon>-->
<!--                <OperationIcon-->
<!--                  v-on:click="onShowHistory(scope)"-->
<!--                  type="info"-->
<!--                  v-show="permission.view"-->
<!--                  content="历史记录"-->
<!--                  placement="top"-->
<!--                  icon-name="el-icon-info"-->
<!--                  class="dc-OperationIcon_IconButton"-->
<!--                ></OperationIcon>-->
<!--              </template>-->
<!--            </ux-table-column>-->
<!--          </ux-grid>-->
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
        </el-row>
      </el-main>
    </el-container>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { listScheduleManagePage, bulkDeleteScheduleManage, modifyScheduleStatus } from '@/api/projectManage/scheduleManage'

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
import TextEllipsis from '../components/text-ellipsis'
import {listProjectListAllWithMilestone} from "@/api/projectManage/projectList";
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
    OperationIcon,
    TextEllipsis
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
      parentTable: {},
      queryFormData: {
        scheduleName: '' // 日程名称
      },
      tableData: [],
      table: {
        selected: null,
        selection: []
      },
      // 查询表格列头
      tableColumns: {
        129: {
          title: '日程名称',
          field: 'scheduleName',
          visible: true,
          order: 0
        },
        132: {
          title: '状态',
          field: 'scheduleStatus.name',
          visible: true,
          order: 1
        },
        135: {
          title: '日程类型',
          field: 'scheduleType.name',
          visible: true,
          order: 2
        },
        136: {
          title: '计划开始日期',
          field: 'planBegin',
          visible: true,
          order: 3
        },
        137: {
          title: '计划结束日期',
          field: 'planEnd',
          visible: true,
          order: 4
        },
        138: {
          title: '实际开始日期',
          field: 'realBegin',
          visible: true,
          order: 5
        },
        139: {
          title: '实际结束日期',
          field: 'realEnd',
          visible: true,
          order: 6
        },
        141: {
          title: '参与人',
          field: 'participants',
          visible: true,
          order: 7
        },
        140: {
          title: '日程描述',
          field: 'scheduleDesc',
          visible: true,
          order: 8
        },
        143: {
          title: '创建者',
          field: 'createBy',
          visible: true,
          order: 9
        },
        144: {
          title: '创建时间',
          field: 'createDate',
          visible: true,
          order: 10
        },
        142: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 11
        },
        145: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 12
        },
        146: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 13
        },
        119: {
          title: '操作',
          width: '160px',
          isShow: true,
          visible: true,
          order: 14
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
      tableId: '1740772218121938535',
      schemeId: '1740772218121938533',
      projects: [],
      selectMilestone: null,
      treeProps: {
        children: 'milestones',
        label: 'milestoneName'
      },
      leftSearch: {
        name: ''
      }
    }
  },
  computed: {},
  methods: {
    reset() {
      this.isQueryConditionPanel ? this.$refs.conditionPanel.reset() : this.$refs.queryForm.resetFields()
      this.getList()
    },
    doTreeSearch() {
      this.selectMilestone = null
      this.$nextTick(() => {
        this.$refs.tree.filter(this.leftSearch.name)
      })
    },
    /**
     * 树节点过滤规则
     * @author Yogaa
     * @date 17:05 2023/12/28
     */
    filterNode(value, data) {
      // if (!value) return true;
      let flag = data.milestoneName.indexOf(value) !== -1
      // 搜索后默认选中里程碑第一项
      if (flag && !data.disabled && !this.selectMilestone) {
        this.selectMilestone = data
      }
      return flag;
    },
    /**
     * 修改日程状态
     * @author Yogaa
     * @date 17:05 2023/12/28
     */
    modifyScheduleStatus(status) {
      if (this.table.selection.length === 0) {
        this.$alert('请选择数据后操作', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.$confirm('确认执行该操作？执行后日程状态将更新，请谨慎操作！', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        let params = {
          ids: this.table.selection.map(item => item.id).join(','),
          milestoneId: this.selectMilestone.id,
          status
        }
        modifyScheduleStatus(params)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.getList()
              this.showMessage({
                type: 'success',
                msg: '更新成功'
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
    /**
     * 执行任务
     * @author Yogaa
     * @date 15:16 2023/12/28
     */
    handleExecSchedule() {
      let nonValidArr = this.table.selection.filter(item => item.scheduleStatus.value != 0)
      if (nonValidArr.length > 0) {
        this.$alert('选中日程存在已执行或完成，请勿重复操作。', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      let status = '1859848726116008004'
      this.modifyScheduleStatus(status)
    },
    /**
     * 取消执行
     * @author Yogaa
     * @date 15:16 2023/12/28
     */
    handleCancelSchedule() {
      let nonValidArr = this.table.selection.filter(item => item.scheduleStatus.value != 1)
      if (nonValidArr.length > 0) {
        this.$alert('选中日程无法操作取消执行。', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      let status = '1859848726116008003'
      this.modifyScheduleStatus(status)
    },
    /**
     * 完成任务
     * @author Yogaa
     * @date 15:16 2023/12/28
     */
    handleCompleteSchedule() {
      let nonValidArr = this.table.selection.filter(item => item.scheduleStatus.value != 1)
      if (nonValidArr.length > 0) {
        this.$alert('选中日程无法操作完成。', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      let status = '1859848726116008005'
      this.modifyScheduleStatus(status)
    },
    handleSelectionChange(selection) {
      this.table.selection = selection
    },
    handleCurrentChange(row) {
      this.table.selected = row
    },
    treeRender(h, { node, data, store }) {
      return h('div', {
        class: {
          'tree-title': true,
          'tree-title-active': this.selectMilestone && this.selectMilestone.id === data.id
        },
        on: {
          click: () => {
            if (data.disabled) {
              return
            }
            this.selectMilestone = data
            this.getList()
          }
        }
      }, data.milestoneName)
    },
    /**
     * 左侧树节点点击事件
     * @author Yogaa
     * @date 18:30 2023/12/27
     */
    handleTreeNodeClick() {

    },
    getList() {
      this.setLoad()
      this.table.selected = null
      this.table.selection = []
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (validatenull(this.selectMilestone)) {
        this.$alert('请选择左侧里程碑', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.search.params.push({
        columnName: 'milestone_id',
        queryType: '=',
        value: this.selectMilestone.id
      })

      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'schedule_name',
          queryType: 'like',
          value: !validatenull(this.queryFormData.scheduleName) ? this.queryFormData.scheduleName : null
        })
      }
      // 数据权限: 日程管理schedule_manage
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listScheduleManagePage(this.search)
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
      this.initOptions(this.queryModel)
      this.setLoad()
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === 'scheduleManage:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'scheduleManage:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'scheduleManage:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'scheduleManage:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'scheduleManage:delete'
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
    beforeDelete(row) {
      if (row.scheduleStatus.value != 0) {
        this.$alert('当前日程无法删除。', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.onDelete([row])
    },
    bulkDelete() {
      if (this.table.selection.length === 0) {
        this.$alert('请选择数据后操作', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      // 判断所选日程的状态是否都为已保存
      let nonValidArr = this.table.selection.filter(item => item.scheduleStatus.value != 0)
      if (nonValidArr.length > 0) {
        this.$alert('选中数据中存在进行中或已完成的日程，无法删除！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.onDelete(this.table.selection)
    },
    onCreate() {
      if (validatenull(this.selectMilestone)) {
        this.$alert('请选择左侧里程碑', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      let row = {}
      this.$refs.editForm.$emit('openAddDialog', row)
    },
    onEdit(scope) {
      this.$refs.editForm.$emit('openEditDialog', scope.id)
    },
    onCopy(scope) {
      this.$refs.editForm.$emit('openCopyDialog', scope.row.id)
    },
    onDelete(schedules) {
      this.$confirm('确认删除该信息？删除后将无法恢复，请谨慎删除！', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          bulkDeleteScheduleManage(schedules)
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

    onChangeCurrentRow(currentRow, oldCurrentRow) {
      if (currentRow != oldCurrentRow) {
        this.parentTable = currentRow
        this.initOptions(this.queryModel)
        this.onSearch()
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
    initProjectOption() {
      let search = {
        extra: currentUser.id,
        params: []
      }
      listProjectListAllWithMilestone(search).then((responseData) => {
        if (responseData.code == 100) {
          let data = responseData.data
          this.projects = data
          // 默认选中首个项目下首个里程碑
          this.projects.forEach(project => {
            // 项目不能被选中
            project.disabled = true
            project.milestoneName = project.projectName
            let milestones = project.milestones
            if (!this.selectMilestone && milestones.length > 0) {
              this.selectMilestone = milestones[0]
              this.getList()
            }
          })
        } else {
          this.showMessage(responseData)
        }
      })
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.initProjectOption()
    }
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
<style scoped>

</style>

<style lang="scss">
  .schedule-manage {
    .el-tree-node:focus>.el-tree-node__content, .el-tree-node__content:hover {
      background-color: transparent;
    }
    .el-tree-node__content {
      height: 32px;
    }
    .tree-title {
      width: 100%;
      height: 32px;
      line-height: 32px;
      padding: 0 5px;
      border-radius: 4px;
      font-size: 12px;
    }
    .tree-title-active {
      background-color: #d5e7fc;
    }
  }
</style>
