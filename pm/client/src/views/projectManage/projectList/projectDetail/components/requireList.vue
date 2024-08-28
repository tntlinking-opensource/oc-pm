<template>
  <div>
    <el-table ref="table" :data="tableData" border highlight-current-row style="width: 100%">
      <el-table-column type="index" width="50" label="序号"></el-table-column>
      <el-table-column v-for="(item, index) in tableColumns" :key="index" align="left" show-overflow-tooltip :property="item.property" :label="item.label" :min-width="item.width"></el-table-column>
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
  </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import {listRequirementManagePage} from "@/api/requirement/requirementManage";
export default {
  extends: BaseUI,
  name: 'require-list',
  components: {
  },
  data() {
    return {
      project: {},
      search: {
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      tableData: [],
      tableDataPage: 1,
      tableDataTotal: 0,
      tableColumns: [
        {property: 'requirementName', label: '需求名称', width: 120},
        {property: 'requirementCode', label: '需求编号', width: 120},
        {property: 'requirementDesc', label: '需求描述', width: 120},
        {property: 'milestone.milestoneName', label: '所属里程碑', width: 120},
        {property: 'schedule.scheduleName', label: '所属日程', width: 120},
        {property: 'createBy', label: '提出人', width: 120},
        {property: 'createDate', label: '提出时间', width: 120},
        // {property: '', label: '附件', width: 120}
      ]
    }
  },
  props: {
  },
  computed: {},
  methods: {
    init(project) {
      this.project = project
      this.getList()
    },
    getList() {
      this.setLoad()
      this.search.params = [{
        'columnName': 'project_id',
        'queryType': '=',
        'value': this.project.id
      }]
      listRequirementManagePage(this.search)
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
  },
  mounted() {

  }
}
</script>
<style lang="scss" scoped>
</style>
