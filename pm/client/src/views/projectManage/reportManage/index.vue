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
<!--    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">-->
<!--      <el-col :span="24">-->
<!--        <query-tag-->
<!--          ref="queryTag"-->
<!--          v-model="queryEditObject"-->
<!--          :moreParm="moreParm"-->
<!--          :routerId="$route && $route.meta.routerId"-->
<!--          :isQueryConditionPanel="isQueryConditionPanel"-->
<!--          v-on:queryTagPanel="onQueryTagPanel"-->
<!--          v-on:queryTagClick="onQueryTagClick"-->
<!--          v-on:search="onSearch"-->
<!--          class="dc-query-tag_ElConditionTag"-->
<!--        ></query-tag>-->
<!--      </el-col>-->
<!--      <el-col :span="6">-->
<!--        <div style="text-align: right">-->
<!--          <el-button-group class="dc-el-button-group_ButtonGroup">-->
<!--            <el-tooltip effect="dark" content="搜索" placement="top">-->
<!--              <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>-->
<!--            </el-tooltip>-->
<!--            <el-tooltip effect="dark" content="重置" placement="top">-->
<!--              <el-button-->
<!--                v-on:click="reset"-->
<!--                type="primary"-->
<!--                icon="el-icon-refresh-right"-->
<!--              ></el-button>-->
<!--            </el-tooltip>-->
<!--            <el-tooltip effect="dark" content="更多" placement="top">-->
<!--              <el-button v-on:click="onQueryConditionPanel('query')" type="primary" icon="el-icon-d-arrow-right"></el-button>-->
<!--            </el-tooltip>-->
<!--          </el-button-group>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--    <query-condition-panel-->
<!--      v-if="isQueryConditionPanel"-->
<!--      ref="conditionPanel"-->
<!--      :conditionColumns="-->
<!--        metadata.find((item) => {-->
<!--          return item.type == 'main'-->
<!--        }).conditionPanel-->
<!--      "-->
<!--      v-model="moreParm"-->
<!--      :routerId="$route.meta.routerId"-->
<!--      class="dc-query-condition-panel_ElConditionPanel"-->
<!--    ></query-condition-panel>-->
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="projectCode" label="项目编号" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.projectCode"
              :maxLength="300"
              placeholder="请输入项目编号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
              prop="projectId"
              label="项目名称"
              class="dc-el-form-item_SelectInput"
            >
              <el-select
                v-model="queryFormData.projectId"
                :style="{ width: '100%' }"
                placeholder="请选择项目名称"
                clearable
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in projectOptions" :key="index" :label="item.projectName" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
<!--          <el-form-item prop="projectName" label="项目名称" class="dc-el-form-item_SingleInput">-->
<!--            <el-input-->
<!--              v-model="queryFormData.projectName"-->
<!--              :maxLength="300"-->
<!--              placeholder="请输入项目名称"-->
<!--              clearable-->
<!--              class="dc-el-input_SingleInput"-->
<!--            ></el-input>-->
<!--          </el-form-item>-->
        </el-col>
        <el-col :span="6">
          <el-form-item prop="createBy" label="报告人" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.createBy"
              :maxLength="64"
              placeholder="请输入报告人"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="reportBegin" label="日期" class="dc-el-form-item_PickerTimeDateRange">
            <el-date-picker
              v-model="queryFormData.reportDate"
              :style="{ width: '100%' }"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDateRange"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow" style="margin-bottom: 5px;">
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.edit" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-edit" v-on:click="beforeEdit" class="dc-el-button_Button">编辑</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.remove" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-delete" v-on:click="bulkDelete" class="dc-el-button_Button">删除</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.export" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="handleExport" class="dc-el-button_Button">导出</el-button>
      </el-tooltip>
      <el-button type='primary' icon='el-icon-search' class='dc-el-button_Button' @click="onSearch">搜索</el-button>
      <el-button type='primary' icon='el-icon-refresh-right' class='dc-el-button_Button' @click="reset">重置</el-button>
    </el-row>
    <el-table ref="table" :data="tableData" border highlight-current-row @current-change="handleCurrentChange" @selection-change="handleSelectionChange" style="width: 100%">
      <el-table-column align="center" show-overflow-tooltip fixed="left" type="selection" width="55"></el-table-column>
      <el-table-column align="center" show-overflow-tooltip type="index" width="50" label="序号"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="createBy" label="报告人" min-width="120"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="teamRole" label="项目角色" min-width="100"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="reportType.name" label="报告类型" min-width="80"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="project.projectName" label="项目名称" min-width="180"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="project.projectCode" label="项目编号" min-width="120"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip label="时间" min-width="260">
        <template slot-scope="scope">
          <span>{{ getTableReportDate(scope.row) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="overtimeHour" label="加班工时" min-width="80"></el-table-column>
      <el-table-column align="left" show-overflow-tooltip property="createDate" label="创建时间" min-width="160"></el-table-column>
      <el-table-column align="center" label="操作" width="120" fixed="right">
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
          <OperationIcon
            v-on:click="onEdit(scope.row)"
            type="primary"
            v-show="permission.edit"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
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
          ></OperationIcon>
        </template>
      </el-table-column>
    </el-table>
<!--    <ux-grid-->
<!--      column-key-->
<!--      ref="table"-->
<!--      :data="tableData"-->
<!--      border-->
<!--      v-on:sort-change="onSortChange"-->
<!--      v-on:header-dragend="onWidthChange($refs.table)"-->
<!--      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"-->
<!--      class="dc-ux-grid_QueryTable"-->
<!--    >-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['146'].field"-->
<!--        :title="tableColumns['146'].title"-->
<!--        :width="tableColumns['146'].width"-->
<!--        :visible="tableColumns['146'].visible"-->
<!--        :params="{ sortId: '146', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="180px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['100'].field"-->
<!--        :title="tableColumns['100'].title"-->
<!--        :width="tableColumns['100'].width"-->
<!--        :visible="tableColumns['100'].visible"-->
<!--        :params="{ sortId: '100', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="180px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['133'].field"-->
<!--        :title="tableColumns['133'].title"-->
<!--        :width="tableColumns['133'].width"-->
<!--        :visible="tableColumns['133'].visible"-->
<!--        :params="{ sortId: '133', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['130'].field"-->
<!--        :title="tableColumns['130'].title"-->
<!--        :width="tableColumns['130'].width"-->
<!--        :visible="tableColumns['130'].visible"-->
<!--        :params="{ sortId: '130', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['129'].field"-->
<!--        :title="tableColumns['129'].title"-->
<!--        :width="tableColumns['129'].width"-->
<!--        :visible="tableColumns['129'].visible"-->
<!--        :params="{ sortId: '129', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['137'].field"-->
<!--        :title="tableColumns['137'].title"-->
<!--        :width="tableColumns['137'].width"-->
<!--        :visible="tableColumns['137'].visible"-->
<!--        :params="{ sortId: '137', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="right"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      >-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{getTableReportDate(scope)}}</span>-->
<!--        </template>-->
<!--      </ux-table-column>-->
<!--&lt;!&ndash;      <ux-table-column&ndash;&gt;-->
<!--&lt;!&ndash;        :field="tableColumns['138'].field"&ndash;&gt;-->
<!--&lt;!&ndash;        :title="tableColumns['138'].title"&ndash;&gt;-->
<!--&lt;!&ndash;        :width="tableColumns['138'].width"&ndash;&gt;-->
<!--&lt;!&ndash;        :visible="tableColumns['138'].visible"&ndash;&gt;-->
<!--&lt;!&ndash;        :params="{ sortId: '138', summary: false }"&ndash;&gt;-->
<!--&lt;!&ndash;        tree-node&ndash;&gt;-->
<!--&lt;!&ndash;        resizable&ndash;&gt;-->
<!--&lt;!&ndash;        min-width="120px"&ndash;&gt;-->
<!--&lt;!&ndash;        align="right"&ndash;&gt;-->
<!--&lt;!&ndash;        header-align="center"&ndash;&gt;-->
<!--&lt;!&ndash;        sortable&ndash;&gt;-->
<!--&lt;!&ndash;        show-overflow&ndash;&gt;-->
<!--&lt;!&ndash;        class="dc-ux-table-column_ElTableColumn"&ndash;&gt;-->
<!--&lt;!&ndash;      ></ux-table-column>&ndash;&gt;-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['142'].field"-->
<!--        :title="tableColumns['142'].title"-->
<!--        :width="tableColumns['142'].width"-->
<!--        :visible="tableColumns['142'].visible"-->
<!--        :params="{ sortId: '142', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="80px"-->
<!--        align="right"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['147'].field"-->
<!--        :title="tableColumns['147'].title"-->
<!--        :width="tableColumns['147'].width"-->
<!--        :visible="tableColumns['147'].visible"-->
<!--        :params="{ sortId: '147', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="right"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      >-->
<!--        <template slot-scope="{ row, rowIndex }">-->
<!--          <div-->
<!--            v-html="-->
<!--              ((value, row, rowIndex) => {-->
<!--                return value.replace(/(\d{4})(\d{2})(\d{2})/g, '$1-$2-$3')-->
<!--              })(row.createDate, row, rowIndex)-->
<!--            "-->
<!--          ></div>-->
<!--        </template>-->
<!--      </ux-table-column>-->
<!--&lt;!&ndash;      <ux-table-column&ndash;&gt;-->
<!--&lt;!&ndash;        :field="tableColumns['144'].field"&ndash;&gt;-->
<!--&lt;!&ndash;        :title="tableColumns['144'].title"&ndash;&gt;-->
<!--&lt;!&ndash;        :width="tableColumns['144'].width"&ndash;&gt;-->
<!--&lt;!&ndash;        :visible="tableColumns['144'].visible"&ndash;&gt;-->
<!--&lt;!&ndash;        :params="{ sortId: '144', summary: false }"&ndash;&gt;-->
<!--&lt;!&ndash;        tree-node&ndash;&gt;-->
<!--&lt;!&ndash;        resizable&ndash;&gt;-->
<!--&lt;!&ndash;        min-width="180px"&ndash;&gt;-->
<!--&lt;!&ndash;        align="left"&ndash;&gt;-->
<!--&lt;!&ndash;        header-align="center"&ndash;&gt;-->
<!--&lt;!&ndash;        sortable&ndash;&gt;-->
<!--&lt;!&ndash;        show-overflow&ndash;&gt;-->
<!--&lt;!&ndash;        class="dc-ux-table-column_ElTableColumn"&ndash;&gt;-->
<!--&lt;!&ndash;      ></ux-table-column>&ndash;&gt;-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['136'].field"-->
<!--        :title="tableColumns['136'].title"-->
<!--        :width="tableColumns['136'].width"-->
<!--        :visible="tableColumns['136'].visible"-->
<!--        :params="{ sortId: '136', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['139'].field"-->
<!--        :title="tableColumns['139'].title"-->
<!--        :width="tableColumns['139'].width"-->
<!--        :visible="tableColumns['139'].visible"-->
<!--        :params="{ sortId: '139', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="80px"-->
<!--        align="right"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['140'].field"-->
<!--        :title="tableColumns['140'].title"-->
<!--        :width="tableColumns['140'].width"-->
<!--        :visible="tableColumns['140'].visible"-->
<!--        :params="{ sortId: '140', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="right"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['141'].field"-->
<!--        :title="tableColumns['141'].title"-->
<!--        :width="tableColumns['141'].width"-->
<!--        :visible="tableColumns['141'].visible"-->
<!--        :params="{ sortId: '141', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="right"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['143'].field"-->
<!--        :title="tableColumns['143'].title"-->
<!--        :width="tableColumns['143'].width"-->
<!--        :visible="tableColumns['143'].visible"-->
<!--        :params="{ sortId: '143', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="180px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['145'].field"-->
<!--        :title="tableColumns['145'].title"-->
<!--        :width="tableColumns['145'].width"-->
<!--        :visible="tableColumns['145'].visible"-->
<!--        :params="{ sortId: '145', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="180px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['148'].field"-->
<!--        :title="tableColumns['148'].title"-->
<!--        :width="tableColumns['148'].width"-->
<!--        :visible="tableColumns['148'].visible"-->
<!--        :params="{ sortId: '148', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="180px"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        :field="tableColumns['149'].field"-->
<!--        :title="tableColumns['149'].title"-->
<!--        :width="tableColumns['149'].width"-->
<!--        :visible="tableColumns['149'].visible"-->
<!--        :params="{ sortId: '149', summary: false }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        min-width="120px"-->
<!--        align="right"-->
<!--        header-align="center"-->
<!--        sortable-->
<!--        show-overflow-->
<!--        class="dc-ux-table-column_ElTableColumn"-->
<!--      ></ux-table-column>-->
<!--      <ux-table-column-->
<!--        title="操作"-->
<!--        :params="{ sortId: '114' }"-->
<!--        tree-node-->
<!--        resizable-->
<!--        width="100px"-->
<!--        min-width="100px"-->
<!--        fixed="right"-->
<!--        align="left"-->
<!--        header-align="center"-->
<!--        class="dc-ux-table-column_ElTableOptColumn"-->
<!--      >-->
<!--        <template v-slot:header="scope">-->
<!--          <span>操作</span>-->
<!--          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10" class="dc-el-popover_ElPopover">-->
<!--            <view-columns-select-->
<!--              v-model="tableColumns"-->
<!--              ref="viewColumnsSelect"-->
<!--              v-on:save-column-view="saveColumn($event, 'table')"-->
<!--              v-on:show-all-column="showAllColumn($event, 'table')"-->
<!--              v-on:show-default-column="showDefaultColumn('table')"-->
<!--              class="dc-view-columns-select_ViewColumnsSelect"-->
<!--            ></view-columns-select>-->
<!--            <template slot="reference">-->
<!--              <OperationIcon type="primary" content="自定义列" placement="top" icon-name="el-icon-tickets"></OperationIcon>-->
<!--            </template>-->
<!--          </el-popover>-->
<!--          <OperationIcon-->
<!--            v-on:click="onExport(tableData)"-->
<!--            type="primary"-->
<!--            v-show="permission.export"-->
<!--            content="导出excel文件"-->
<!--            placement="top"-->
<!--            icon-name="el-icon-download"-->
<!--            class="dc-OperationIcon_IconButton"-->
<!--          ></OperationIcon>-->
<!--        </template>-->
<!--        <template slot-scope="scope">-->
<!--          <OperationIcon-->
<!--            v-on:click="onView(scope)"-->
<!--            type="info"-->
<!--            v-show="permission.view"-->
<!--            content="查看"-->
<!--            placement="top"-->
<!--            icon-name="el-icon-view"-->
<!--            class="dc-OperationIcon_IconButton"-->
<!--          ></OperationIcon>-->
<!--          <OperationIcon-->
<!--            v-on:click="onEdit(scope)"-->
<!--            type="primary"-->
<!--            v-show="permission.edit"-->
<!--            content="编辑"-->
<!--            placement="top"-->
<!--            icon-name="el-icon-edit"-->
<!--            class="dc-OperationIcon_IconButton"-->
<!--          ></OperationIcon>-->
<!--&lt;!&ndash;          <OperationIcon&ndash;&gt;-->
<!--&lt;!&ndash;            v-on:click="onCopy(scope)"&ndash;&gt;-->
<!--&lt;!&ndash;            type="primary"&ndash;&gt;-->
<!--&lt;!&ndash;            v-show="permission.add"&ndash;&gt;-->
<!--&lt;!&ndash;            content="复制"&ndash;&gt;-->
<!--&lt;!&ndash;            placement="top"&ndash;&gt;-->
<!--&lt;!&ndash;            icon-name="el-icon-document"&ndash;&gt;-->
<!--&lt;!&ndash;            class="dc-OperationIcon_IconButton"&ndash;&gt;-->
<!--&lt;!&ndash;          ></OperationIcon>&ndash;&gt;-->
<!--          <OperationIcon-->
<!--            v-on:click="onDelete(scope)"-->
<!--            type="danger"-->
<!--            v-show="permission.remove"-->
<!--            content="删除"-->
<!--            placement="top"-->
<!--            icon-name="el-icon-delete"-->
<!--            class="dc-OperationIcon_IconButton"-->
<!--          ></OperationIcon>-->
<!--          <OperationIcon-->
<!--            v-on:click="onShowHistory(scope)"-->
<!--            type="info"-->
<!--            v-show="permission.view"-->
<!--            content="历史记录"-->
<!--            placement="top"-->
<!--            icon-name="el-icon-info"-->
<!--            class="dc-OperationIcon_IconButton"-->
<!--          ></OperationIcon>-->
<!--        </template>-->
<!--      </ux-table-column>-->
<!--    </ux-grid>-->
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

import { listReportManagePage, bulkDeleteReportManage } from '@/api/projectManage/reportManage'

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
import {listProjectListAll} from "@/api/projectManage/projectList";
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
        projectCode: '', // 项目编号
        projectId: '', // 项目名称
        createBy: '', // 报告人
        reportDate: [] // 日期
      },
      tableData: [],
      table: {
        selected: null,
        selection: []
      },
      // 查询表格列头
      tableColumns: {
        1: {
          title: '报告人',
          field: 'createBy',
          visible: true,
          order: 0
        },
        2: {
          title: '项目角色',
          field: 'teamRole',
          visible: true,
          order: 0
        },
        3: {
          title: '报告类型',
          field: 'reportType.name',
          visible: true,
          order: 1
        },
        4: {
          title: '项目名称',
          field: 'project.projectName',
          visible: true,
          order: 2
        },
        5: {
          title: '项目编号',
          field: 'project.projectCode',
          visible: true,
          order: 3
        },
        6: {
          title: '时间',
          field: 'reportDate',
          visible: true,
          order: 4
        },
        7: {
          title: '加班工时',
          field: 'overtimeHour',
          visible: true,
          order: 6
        },
        8: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 7
        },
        // // 144: {
        // //   title: '附件',
        // //   field: 'annex',
        // //   visible: true,
        // //   order: 8
        // // },
        // 136: {
        //   title: '加班类型',
        //   field: 'overtimeType.name',
        //   visible: false,
        //   order: 9
        // },
        // 139: {
        //   title: '报告时长',
        //   field: 'reportDay',
        //   visible: false,
        //   order: 10
        // },
        // 140: {
        //   title: '加班开始时间',
        //   field: 'overtimeBegin',
        //   visible: false,
        //   order: 11
        // },
        // 141: {
        //   title: '加班结束时间',
        //   field: 'overtimeEnd',
        //   visible: false,
        //   order: 12
        // },
        // 143: {
        //   title: '工作内容',
        //   field: 'workContent',
        //   visible: false,
        //   order: 13
        // },
        // 145: {
        //   title: '备注信息',
        //   field: 'remarks',
        //   visible: false,
        //   order: 14
        // },
        // 148: {
        //   title: '更新者',
        //   field: 'updateBy',
        //   visible: false,
        //   order: 15
        // },
        // 149: {
        //   title: '更新时间',
        //   field: 'updateDate',
        //   visible: false,
        //   order: 16
        // },
        // 114: {
        //   title: '操作',
        //   width: '140px',
        //   visible: true,
        //   order: 17
        // }
      },
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
      tableId: '1740772218121938242',
      schemeId: '1740772218121938240',
      projectOptions: []
    }
  },
  computed: {},
  methods: {
    handleExport() {
      let data = this.tableData.map(item => {
        return {
          createBy: item.createBy,
          teamRole: item.teamRole,
          reportTypeName: item.reportType.name,
          projectProjectName: item.project.projectName,
          projectProjectCode: item.project.projectCode,
          reportDate: this.getTableReportDate(item),
          overtimeHour: item.overtimeHour,
          createDate: item.createDate,
        }
      })
      console.log(data)
      this.onExport(data)
    },
    handleSelectionChange(selection) {
      this.table.selection = selection
    },
    handleCurrentChange(row) {
      this.table.selected = row
    },
    getHeads(){
      return ['项目角色', '项目编号', '项目名称', '报告类型', '加班工时', '报告人']
    },
    reset() {
      this.isQueryConditionPanel ? this.$refs.conditionPanel.reset() : this.$refs.queryForm.resetFields()
      this.queryFormData.reportDate = []
      this.getList()
    },
    getTableReportDate(row) {
      let {reportBegin, reportEnd} = row
      return `${reportBegin} - ${reportEnd}`
    },
    listProjectOptions() {
      let search_List = {
        params: []
      }
      // 数据权限: 项目清单project_list
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937934')
      listProjectListAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.projectOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'project.project_code',
          queryType: 'like',
          value: !validatenull(this.queryFormData.projectCode) ? this.queryFormData.projectCode : null
        })
        this.search.params.push({
          columnName: 'project.id',
          queryType: '=',
          value: !validatenull(this.queryFormData.projectId) ? this.queryFormData.projectId : null
        })
        this.search.params.push({
          columnName: 'create_by',
          queryType: 'like',
          value: !validatenull(this.queryFormData.createBy) ? this.queryFormData.createBy : null
        })
        if (!validatenull(this.queryFormData.reportDate)) {
          let rd = [...this.queryFormData.reportDate]
          rd = [`${rd[0]} 00:00:00`, `${rd[1]} 23:59:59`]
          this.search.params.push(
            {
              queryType: "("
            },
            {
              logic: '',
              columnName: 'report_begin',
              queryType: 'between',
              value: !validatenull(rd) ? rd : null
            },
            {
              logic: "OR",
              columnName: 'report_end',
              queryType: 'between',
              value: !validatenull(rd) ? rd : null
            },
            {
              queryType: ")"
            }
          )
        }
      }
      // this.search.extra = currentUser.id
      // 数据权限: 周报管理report_manage
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listReportManagePage(this.search)
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
              return item.permission === 'reportManage:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'reportManage:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'reportManage:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'reportManage:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'reportManage:delete'
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
      this.onDelete(this.table.selection)
    },
    onDelete(reports) {
      this.$confirm('确认删除该信息？删除后将无法恢复，请谨慎删除！', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          bulkDeleteReportManage(reports)
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
    initOptions(This) {
      this.listProjectOptions()
    }
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
