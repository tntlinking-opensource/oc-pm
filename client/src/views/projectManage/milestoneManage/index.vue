<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref='history' :bussObject="curentRow">
    </history>
    <edit-form ref='editForm' v-on:save-finished="getList" v-on:before-load="setLoad" v-on:after-load="resetLoad"
               :permission="permission" :project="selectProject || {}">
    </edit-form>
    <milestone-template-manage ref="templateManage"></milestone-template-manage>
    <el-container direction='horizontal' class='dc-el-container_ElContainer'>
      <el-aside width='200px' height='60px'>
        <el-row>
          <el-input placeholder="请输入项目" v-model="leftSearch.projectName">
            <i class="el-icon-search el-input__icon" slot="suffix" @click="doProjectSearch"></i>
          </el-input>
        </el-row>
        <div v-for="(item, index) in opts.project" :key="index" @click="handleProjectClick(item)" :class="{'project-title': true, 'project-title-active': item.id === selectProject.id}">
          <text-ellipsis :text="item.projectName" :width="180"></text-ellipsis>
        </div>
<!--        <div :class="{'project-title': true, 'project-title-active': item.id === selectProject.id}" v-for="(item, index) in opts.project" :key="index" @click="handleProjectClick(item)">{{item.projectName}}</div>-->
      </el-aside>
      <el-main>
        <el-row>
<!--          <el-row gutter='0' type='flex' justify='start' align='top' class='dc-el-row_ElRow'>-->
<!--            <el-col :span="24">-->
<!--              <query-tag ref='queryTag' v-model='queryEditObject' :moreParm='moreParm'-->
<!--                         :routerId='$route && $route.meta.routerId' :isQueryConditionPanel='isQueryConditionPanel'-->
<!--                         v-on:queryTagPanel='onQueryTagPanel' v-on:queryTagClick='onQueryTagClick'-->
<!--                         v-on:search='onSearch' class='dc-query-tag_ElConditionTag'></query-tag>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <div style="text-align: left">-->
<!--                <el-button-group class='dc-el-button-group_ButtonGroup'>-->
<!--                  <el-tooltip effect='dark' content='搜索' placement='top'>-->
<!--                    <el-button v-on:click='onSearch' type='primary' icon='el-icon-search'></el-button>-->
<!--                  </el-tooltip>-->
<!--                  <el-tooltip effect='dark' content='重置' placement='top'>-->
<!--                    <el-button-->
<!--                      v-on:click='reset'-->
<!--                      type='primary' icon='el-icon-refresh-right'></el-button>-->
<!--                  </el-tooltip>-->
<!--                  <el-tooltip effect='dark' content='更多' placement='top'>-->
<!--                    <el-button v-on:click='onQueryConditionPanel("query")' type='primary'-->
<!--                               icon='el-icon-d-arrow-right'></el-button>-->
<!--                  </el-tooltip>-->
<!--                </el-button-group>-->
<!--              </div>-->
<!--            </el-col>-->
<!--          </el-row>-->
<!--          <query-condition-panel v-if='isQueryConditionPanel' ref='conditionPanel'-->
<!--                                 :conditionColumns='(metadata.find(item => {return item.type == "main"})).conditionPanel'-->
<!--                                 v-model='moreParm' :routerId='$route.meta.routerId'-->
<!--                                 class='dc-query-condition-panel_ElConditionPanel'></query-condition-panel>-->
          <el-form ref='queryForm' :model='queryFormData' label-width='100px' v-if='!isQueryConditionPanel'
                   class='dc-el-form_ElQueryForm'>
            <el-row>
              <el-col :span="6">
                <el-form-item prop="milestoneName" label='里程碑名称' class='dc-el-form-item_SingleInput'>
                  <el-input v-model='queryFormData.milestoneName' :maxLength='50' placeholder='请输入里程碑名称' clearable
                            class='dc-el-input_SingleInput'></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-row gutter='0' type='flex' justify='start' align='top' class='dc-el-row_ElRow' style="margin-bottom: 5px;">
            <el-tooltip v-show='permission.add' disabled class='dc-el-tooltip_Button'>
              <el-button type='primary' icon='el-icon-plus' v-on:click='onCreate'>新增</el-button>
            </el-tooltip>
            <el-tooltip disabled class='dc-el-tooltip_Button'>
              <el-button type='primary' icon='el-icon-edit' class='dc-el-button_Button' @click="beforeEdit">编辑</el-button>
            </el-tooltip>
            <el-tooltip disabled class='dc-el-tooltip_Button'>
              <el-button type='primary' icon='el-icon-delete' class='dc-el-button_Button' @click="bulkDelete">删除</el-button>
            </el-tooltip>
            <el-tooltip disabled class='dc-el-tooltip_Button'>
              <el-button type='primary' icon='el-icon-setting' class='dc-el-button_Button' @click="onManageTemplate">管理模板</el-button>
            </el-tooltip>
            <el-button type='primary' icon='el-icon-search' class='dc-el-button_Button' @click="onSearch">搜索</el-button>
            <el-button type='primary' icon='el-icon-refresh-right' class='dc-el-button_Button' @click="reset">重置</el-button>
          </el-row>
          <el-table ref="table" :data="tableData" border highlight-current-row @current-change="handleCurrentChange" @selection-change="handleSelectionChange" style="width: 100%">
            <el-table-column align="center" fixed="left" type="selection" width="55"></el-table-column>
            <el-table-column align="center" type="index" width="50" label="序号"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="milestoneName" label="里程碑名称" min-width="180"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="milestoneStatus.name" label="状态" min-width="80"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="planBegin" label="计划开始日期" min-width="110"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="planEnd" label="计划结束日期" min-width="110"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="realBegin" label="实际开始日期" min-width="110"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="realEnd" label="实际结束日期" min-width="110"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="progressRate" label="进度占比" min-width="80">
              <template slot-scope="scope">
                <span style="display: inline-block; width: 100%; text-align: left">{{scope.row.progressRate}}%</span>
              </template>
            </el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="participants" label="参与人员" min-width="120"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="milestoneDesc" label="里程碑描述" min-width="180"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="sort" label="顺序" min-width="50"></el-table-column>
            <el-table-column align="left" show-overflow-tooltip property="annexName" label="附件" min-width="120"></el-table-column>
            <el-table-column align="center" label="操作" width="110" fixed="right">
              <template slot-scope="scope">
                <OperationIcon v-on:click='onView(scope)' type='info' v-show='permission.view' content='查看'
                               placement='top' icon-name='el-icon-view'
                               class='dc-OperationIcon_IconButton'></OperationIcon>
                <OperationIcon v-on:click='onEdit(scope.row)' type='primary' v-show='permission.edit' content='编辑'
                               placement='top' icon-name='el-icon-edit'
                               class='dc-OperationIcon_IconButton'></OperationIcon>
<!--                <OperationIcon v-on:click='onCopy(scope)' type='primary' v-show='permission.add' content='复制'-->
<!--                               placement='top' icon-name='el-icon-document'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
                <OperationIcon v-on:click='onDelete(scope)' type='danger' v-show='permission.remove' content='删除'
                               placement='top' icon-name='el-icon-delete'
                               class='dc-OperationIcon_IconButton'></OperationIcon>
<!--                <OperationIcon v-on:click='onShowHistory(scope)' type='info' v-show='permission.view' content='历史记录'-->
<!--                               placement='top' icon-name='el-icon-info'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
              </template>
            </el-table-column>
          </el-table>
<!--          <ux-grid column-key ref='table' :data='tableData' border v-on:sort-change='onSortChange'-->
<!--                   v-on:header-dragend='onWidthChange($refs.table)'-->
<!--                   :tree-config='{"children":"","indent":20,"accordion":false,"line":false,"showIcon":true,"iconOpen":"","iconClose":""}'-->
<!--                   class='dc-ux-grid_QueryTable'>-->
<!--            <ux-table-column :field="tableColumns['129'].field"-->
<!--                             :title="tableColumns['129'].title"-->
<!--                             :width="tableColumns['129'].width"-->
<!--                             :visible="tableColumns['129'].visible"-->
<!--                             :params="{sortId: '129', summary: false}" tree-node resizable min-width='180px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['130'].field"-->
<!--                             :title="tableColumns['130'].title"-->
<!--                             :width="tableColumns['130'].width"-->
<!--                             :visible="tableColumns['130'].visible"-->
<!--                             :params="{sortId: '130', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='center' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['131'].field"-->
<!--                             :title="tableColumns['131'].title"-->
<!--                             :width="tableColumns['131'].width"-->
<!--                             :visible="tableColumns['131'].visible"-->
<!--                             :params="{sortId: '131', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'>-->
<!--            </ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['132'].field"-->
<!--                             :title="tableColumns['132'].title"-->
<!--                             :width="tableColumns['132'].width"-->
<!--                             :visible="tableColumns['132'].visible"-->
<!--                             :params="{sortId: '132', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'>-->
<!--            </ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['133'].field"-->
<!--                             :title="tableColumns['133'].title"-->
<!--                             :width="tableColumns['133'].width"-->
<!--                             :visible="tableColumns['133'].visible"-->
<!--                             :params="{sortId: '133', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'>-->
<!--            </ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['134'].field"-->
<!--                             :title="tableColumns['134'].title"-->
<!--                             :width="tableColumns['134'].width"-->
<!--                             :visible="tableColumns['134'].visible"-->
<!--                             :params="{sortId: '134', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'>-->
<!--            </ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['135'].field"-->
<!--                             :title="tableColumns['135'].title"-->
<!--                             :width="tableColumns['135'].width"-->
<!--                             :visible="tableColumns['135'].visible"-->
<!--                             :params="{sortId: '135', summary: false}" tree-node resizable min-width='80px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'>-->
<!--              <template slot-scope='{row, rowIndex}'>-->
<!--                <div v-html='((value, row, rowIndex) => { return `${value}%` })(row.progressRate, row, rowIndex)'></div>-->
<!--              </template>-->
<!--            </ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['136'].field"-->
<!--                             :title="tableColumns['136'].title"-->
<!--                             :width="tableColumns['136'].width"-->
<!--                             :visible="tableColumns['136'].visible"-->
<!--                             :params="{sortId: '136', summary: false}" tree-node resizable min-width='180px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['137'].field"-->
<!--                             :title="tableColumns['137'].title"-->
<!--                             :width="tableColumns['137'].width"-->
<!--                             :visible="tableColumns['137'].visible"-->
<!--                             :params="{sortId: '137', summary: false}" tree-node resizable min-width='180px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['138'].field"-->
<!--                             :title="tableColumns['138'].title"-->
<!--                             :width="tableColumns['138'].width"-->
<!--                             :visible="tableColumns['138'].visible"-->
<!--                             :params="{sortId: '138', summary: false}" tree-node resizable min-width='80px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['139'].field"-->
<!--                             :title="tableColumns['139'].title"-->
<!--                             :width="tableColumns['139'].width"-->
<!--                             :visible="tableColumns['139'].visible"-->
<!--                             :params="{sortId: '139', summary: false}" tree-node resizable min-width='180px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['127'].field"-->
<!--                             :title="tableColumns['127'].title"-->
<!--                             :width="tableColumns['127'].width"-->
<!--                             :visible="tableColumns['127'].visible"-->
<!--                             :params="{sortId: '127', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['128'].field"-->
<!--                             :title="tableColumns['128'].title"-->
<!--                             :width="tableColumns['128'].width"-->
<!--                             :visible="tableColumns['128'].visible"-->
<!--                             :params="{sortId: '128', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['140'].field"-->
<!--                             :title="tableColumns['140'].title"-->
<!--                             :width="tableColumns['140'].width"-->
<!--                             :visible="tableColumns['140'].visible"-->
<!--                             :params="{sortId: '140', summary: false}" tree-node resizable min-width='180px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['141'].field"-->
<!--                             :title="tableColumns['141'].title"-->
<!--                             :width="tableColumns['141'].width"-->
<!--                             :visible="tableColumns['141'].visible"-->
<!--                             :params="{sortId: '141', summary: false}" tree-node resizable min-width='180px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['142'].field"-->
<!--                             :title="tableColumns['142'].title"-->
<!--                             :width="tableColumns['142'].width"-->
<!--                             :visible="tableColumns['142'].visible"-->
<!--                             :params="{sortId: '142', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['143'].field"-->
<!--                             :title="tableColumns['143'].title"-->
<!--                             :width="tableColumns['143'].width"-->
<!--                             :visible="tableColumns['143'].visible"-->
<!--                             :params="{sortId: '143', summary: false}" tree-node resizable min-width='180px'-->
<!--                             align='left' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column :field="tableColumns['144'].field"-->
<!--                             :title="tableColumns['144'].title"-->
<!--                             :width="tableColumns['144'].width"-->
<!--                             :visible="tableColumns['144'].visible"-->
<!--                             :params="{sortId: '144', summary: false}" tree-node resizable min-width='120px'-->
<!--                             align='right' header-align='center' sortable show-overflow-->
<!--                             class='dc-ux-table-column_ElTableColumn'></ux-table-column>-->
<!--            <ux-table-column title='操作' :params="{sortId: '119'}" tree-node resizable width='160px' min-width='160px'-->
<!--                             fixed='right' align='left' header-align='center'-->
<!--                             class='dc-ux-table-column_ElTableOptColumn'>-->
<!--              <template v-slot:header="scope">-->
<!--                <span>操作</span>-->
<!--                <el-popover ref='popover' width='10' trigger='click' placement='bottom' offset='10'-->
<!--                            class='dc-el-popover_ElPopover'>-->
<!--                  <view-columns-select v-model='tableColumns' ref='viewColumnsSelect'-->
<!--                                       v-on:save-column-view='saveColumn($event, "table")'-->
<!--                                       v-on:show-all-column='showAllColumn($event, "table")'-->
<!--                                       v-on:show-default-column='showDefaultColumn("table")'-->
<!--                                       class='dc-view-columns-select_ViewColumnsSelect'></view-columns-select>-->
<!--                  <template slot="reference">-->
<!--                    <OperationIcon type='primary' content='自定义列' placement='top'-->
<!--                                   icon-name='el-icon-tickets'></OperationIcon>-->
<!--                  </template>-->
<!--                </el-popover>-->
<!--                <OperationIcon v-on:click='onExport(tableData)' type='primary' v-show='permission.export'-->
<!--                               content='导出excel文件' placement='top' icon-name='el-icon-download'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
<!--              </template>-->
<!--              <template slot-scope="scope">-->
<!--                <OperationIcon v-on:click='onView(scope)' type='info' v-show='permission.view' content='查看'-->
<!--                               placement='top' icon-name='el-icon-view'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
<!--                <OperationIcon v-on:click='onEdit(scope)' type='primary' v-show='permission.edit' content='编辑'-->
<!--                               placement='top' icon-name='el-icon-edit'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
<!--                <OperationIcon v-on:click='onCopy(scope)' type='primary' v-show='permission.add' content='复制'-->
<!--                               placement='top' icon-name='el-icon-document'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
<!--                <OperationIcon v-on:click='onDelete(scope)' type='danger' v-show='permission.remove' content='删除'-->
<!--                               placement='top' icon-name='el-icon-delete'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
<!--                <OperationIcon v-on:click='onShowHistory(scope)' type='info' v-show='permission.view' content='历史记录'-->
<!--                               placement='top' icon-name='el-icon-info'-->
<!--                               class='dc-OperationIcon_IconButton'></OperationIcon>-->
<!--              </template>-->
<!--            </ux-table-column>-->
<!--          </ux-grid>-->
          <el-pagination :total='tableDataTotal' :page-size='search.limit' background :current-page='tableDataPage'
                         :page-sizes='[10,20,30,40,50,100]' layout='total, sizes, prev, pager, next, jumper'
                         v-on:size-change='onSizeChange' v-on:current-change='onCurrentChange'
                         class='dc-el-pagination_ElPagination'></el-pagination>
        </el-row>
      </el-main>
    </el-container>
  </el-row>
</template>
<script>
import {validatenull} from '@/utils/validate'
import {listMilestoneManagePage, bulkDeleteMilestoneManage} from '@/api/projectManage/milestoneManage'

import {listResourcePermission} from '@/api/admin/common/permission'
/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history"
import EditForm from "./form"
import QueryTag from "@/views/components/queryTag"
import QueryConditionPanel from "@/views/components/queryConditionPanel"
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect"
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import MilestoneTemplateManage from './compnents/milestoneTemplateManage'
import TextEllipsis from '../components/text-ellipsis'
import {listProjectListAll} from "../../../api/projectManage/projectList";

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
    MilestoneTemplateManage,
    TextEllipsis
  },
  data() {
    return {
      // 左侧项目搜索条件
      leftSearch: {
        projectName: ''
      },
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
        milestoneName: '', // 里程碑名称
      },
      tableData: [],
      table: {
        selected: null,
        selection: []
      },
// 查询表格列头
      tableColumns: {
        129: {
          title: '里程碑名称',
          field: 'milestoneName',
          visible: true,
          order: 0
        },
        130: {
          title: '状态',
          field: 'milestoneStatus.name',
          visible: true,
          order: 1
        },
        131: {
          title: '计划开始日期',
          field: 'planBegin',
          visible: true,
          order: 2
        },
        132: {
          title: '计划结束日期',
          field: 'planEnd',
          visible: true,
          order: 3
        },
        133: {
          title: '实际开始日期',
          field: 'realBegin',
          visible: true,
          order: 4
        },
        134: {
          title: '实际结束日期',
          field: 'realEnd',
          visible: true,
          order: 5
        },
        135: {
          title: '进度占比',
          field: 'progressRate',
          visible: true,
          order: 6
        },
        136: {
          title: '参与人员',
          field: 'participants',
          visible: true,
          order: 7
        },
        137: {
          title: '里程碑描述',
          field: 'milestoneDesc',
          visible: true,
          order: 8
        },
        138: {
          title: '顺序',
          field: 'sort',
          visible: true,
          order: 9
        },
        139: {
          title: '附件',
          field: 'annex',
          visible: true,
          order: 10
        },
        127: {
          title: '项目编号',
          field: 'project.projectCode',
          visible: false,
          order: 11
        },
        128: {
          title: '项目名称',
          field: 'project.projectName',
          visible: false,
          order: 12
        },
        140: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 13
        },
        141: {
          title: '创建者',
          field: 'createBy',
          visible: false,
          order: 14
        },
        142: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 15
        },
        143: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 16
        },
        144: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 17
        },
        119: {
          title: '操作',
          width: '160px',
          isShow: true,
          visible: true,
          order: 18
        },
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
        columnName: '',      // 排序字段名
        order: ''            // 排序
      },
      searchList: [],
      tableId: '1740772218121938361',
      schemeId: '1740772218121938359',
      opts: {
        project: []
      },
      // 选中的项目
      selectProject: null
    }
  },
  computed: {},
  methods: {
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
    /**
     * 打开管理模板弹窗
     * @author Yogaa
     * @date 15:48 2023/12/23
     */
    onManageTemplate() {
      this.$refs.templateManage.openDialog()
    },
    /**
     * 左侧项目点击事件
     * @author Yogaa
     * @date 9:53 2023/12/23
     */
    handleProjectClick(data) {
      console.log(data)
      this.selectProject = data
      this.getList()
    },
    /**
     * 左侧项目搜索执行
     * @author Yogaa
     * @date 0:47 2023/12/21
     */
    doProjectSearch() {
      this.loadProjectOptions()
    },
    loadProjectOptions() {
      let search = {
        params: this.leftSearch.projectName ? [
          {'columnName':'project_name', 'queryType': 'like', 'value': this.leftSearch.projectName}
        ] : []
      }
      listProjectListAll(search).then((responseData) => {
        if (responseData.code == 100) {
          let data = responseData.data
          this.opts.project = data
          if (data.length > 0) {
            this.selectProject = data[0]
            this.getList()
          }
        } else {
          this.showMessage(responseData)
        }
      })
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (validatenull(this.selectProject)) {
        this.$alert('请选择左侧项目', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.search.params.push({
        columnName: 'project_id',
        queryType: '=',
        value: this.selectProject.id
      })
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      }
      // 数据权限: 里程碑管理milestone_manage
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listMilestoneManagePage(this.search).then(responseData => {
        if (responseData.code == 100) {
          this.tableDataTotal = responseData.data.total
          this.tableData = responseData.data.rows ? responseData.data.rows : []
          // 处理参与人

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
      this.initOptions(this.queryModel)
      this.setLoad()
      listResourcePermission(this.$route.meta.routerId).then(responseData => {
        if (responseData.code == 100) {
          this.permission.view = responseData.data.find(item => {
            return item.permission === 'milestoneManage:read'
          })
          this.permission.export = responseData.data.find(item => {
            return item.permission === 'milestoneManage:export'
          })

          this.permission.add = responseData.data.find(item => {
            return item.permission === 'milestoneManage:create'
          })
          this.permission.edit = responseData.data.find(item => {
            return item.permission === 'milestoneManage:update'
          })
          this.permission.remove = responseData.data.find(item => {
            return item.permission === 'milestoneManage:delete'
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
      this.$refs.editForm.$emit('openViewDialog', scope.row.id)
    },

    onCreate() {
      if (validatenull(this.selectProject)) {
        this.$alert('请选择左侧项目', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      let row = {}
      this.$refs.editForm.$emit('openAddDialog', row)
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
    checkMilestoneDelete(milestone) {
      if (milestone.milestoneStatus.value != 0) {
        this.$alert('当前里程碑无法删除。', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      if (milestone.scheduleCnt > 0) {
        this.$alert('当前里程碑下存在日程信息，无法删除。', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      return true
    },
    beforeDelete(row) {
      // 校验不通过
      if (!this.checkMilestoneDelete(row)) {
        return
      }
      this.onDelete([row])
    },
    onEdit(row) {
      this.$refs.editForm.$emit('openEditDialog', row.id)
    },
    onCopy(scope) {
      this.$refs.editForm.$emit('openCopyDialog', scope.row.id)
    },
    bulkDelete() {
      if (this.table.selection.length === 0) {
        this.$alert('请选择数据后操作', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      // 验证所选里程碑下是否有不能删除的情况
      let flag = true
      for (let i = 0; i < this.table.selection.length; i++) {
        let milestone = this.table.selection[i]
        if (!this.checkMilestoneDelete(milestone)) {
          flag = false
          break
        }
      }
      if (!flag) {
        return;
      }
      this.onDelete(this.table.selection)
    },
    onDelete(milestones) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        bulkDeleteMilestoneManage(milestones).then(responseData => {
          if (responseData.code == 100) {
            this.getList()
            this.showMessage({type: 'success', msg: '删除成功'})
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch(error => {
          this.outputError(error)
        })
      }).catch(() => {
      })
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


    // 初始化自定义类型选择框选项变量
    initOptions(This) {
    },
  },
  watch: {},
  mounted() {
    this.pageInit()
    this.loadProjectOptions()
    this.columnDrop(this.$refs.table)
  }
}
</script>

<style scoped lang="scss">
  .dc-container {
    .project-title {
      width: 100%;
      line-height: 32px;
      height: 32px;
      margin-top: 5px;
      border-radius: 4px;
      padding: 0 10px;
      cursor: pointer;
      font-size: 14px;
      color: #515a6e;
      &:hover {
        background-color: #E7F1FCFF;
      }
    }
    .project-title-active {
      background-color: #d5e8fc;

    }
  }
</style>
