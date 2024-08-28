<template>
  <el-row class="dc-container" style="margin-right: 190px">
    <el-card class="box-card" style="margin-bottom: 10px">
      <el-row style="line-height: 32px; height: 32px;">
        <span style="color: #333333; font-size: 18px; font-weight: 650; margin-right: 20px">{{project.projectName}}</span>
        <span>{{ tableForm.startAndEndDate }}</span>
      </el-row>
      <el-row style="line-height: 32px; height: 32px;">
        <el-col :span="8">
          可支配预算金额：{{ tableForm.projectBudget }}
        </el-col>
        <el-col :span="8">
          已预算金额：{{ tableForm.allBudgetAmount }}
        </el-col>
        <el-col :span="8">
          实际支出金额：{{ tableForm.allPaidAmount }}
        </el-col>
      </el-row>
    </el-card>
<!--    <h3 style="position: absolute;top: 3px;left: 300px;">{{ tableForm.startAndEndDate }}</h3>-->
<!--    <el-row style="position: relative;top: -20px;">-->
<!--      <el-col :span="8">可支配预算金额：{{ tableForm.projectBudget }}</el-col>-->
<!--      <el-col :span="8">已预算金额：{{ tableForm.allBudgetAmount }}</el-col>-->
<!--      <el-col :span="8">实际支出金额：{{ tableForm.allPaidAmount }}</el-col>-->
<!--    </el-row>-->
    <ux-grid border show-overflow ref="plxTable" :data="tableData" keep-source height="400" :highlightCurrentRow="false" :edit-config="{ trigger: 'click', mode: 'cell' }">
      <ux-table-column field="name" width="160" align="center" header-align="center">
      </ux-table-column>
      <ux-table-column v-for="(item, index) in tableForm.costBudgetList" :key="item.id" :title="item.budgetDate" align="center" header-align="center">
        <ux-table-column title="预算" min-width="140" align="center" header-align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.type === 'investment'">{{scope.row.costBudgetList[index].perInvestBudget }}</span>
            <span v-if="scope.row.type === 'cost'">{{scope.row.costBudgetList[index].perCostBudget }}</span>
            <span v-if="scope.row.type === 'expenditure'">{{ scope.row.costBudgetList[index].shopPaidBudget }}</span>
            <span v-if="scope.row.type === 'other'">{{ scope.row.costBudgetList[index].otherPaidBudget }}</span>
            <span v-if="scope.row.type === 'subtotal'">{{ scope.row.costBudgetList[index].totalBudgetAmount }}</span>
            <span v-if="scope.row.type === 'diversity'">{{ scope.row.costBudgetList[index].diffScale }}</span>
          </template>
<!--          <template v-slot:edit="scope">-->
<!--            <el-input v-model="scope.row.costBudgetList[index].perInvestBudget" v-if="scope.row.type === 'investment'"></el-input>-->
<!--            <el-input v-model="scope.row.costBudgetList[index].perCostBudget" v-if="scope.row.type === 'cost'"></el-input>-->
<!--            <el-input v-model="scope.row.costBudgetList[index].shopPaidBudget" v-if="scope.row.type === 'expenditure'"></el-input>-->
<!--            <el-input v-model="scope.row.costBudgetList[index].otherPaidBudget" v-if="scope.row.type === 'other'"></el-input>-->
<!--            <el-input v-model="scope.row.costBudgetList[index].totalBudgetAmount" v-if="scope.row.type === 'subtotal'"></el-input>-->
<!--            <el-input v-model="scope.row.costBudgetList[index].diffScale" v-if="scope.row.type === 'diversity'"></el-input>-->
<!--          </template>-->
        </ux-table-column>
        <ux-table-column title="实际" min-width="140" edit-render align="center" header-align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.type === 'investment'">{{scope.row.costBudgetList[index].perInvestReal }}</span>
            <span v-if="scope.row.type === 'cost'">{{scope.row.costBudgetList[index].perCostReal }}</span>
            <span v-if="scope.row.type === 'expenditure'">{{ scope.row.costBudgetList[index].shopPaidReal }}</span>
            <span v-if="scope.row.type === 'other'">{{ scope.row.costBudgetList[index].otherPaidReal }}</span>
            <span v-if="scope.row.type === 'subtotal'">{{ scope.row.costBudgetList[index].totalPaidAmount }}</span>
          </template>
          <template v-slot:edit="scope">
            <el-input v-model="scope.row.costBudgetList[index].perInvestReal" v-if="scope.row.type === 'investment'"></el-input>
            <el-input v-model="scope.row.costBudgetList[index].perCostReal" v-if="scope.row.type === 'cost'"></el-input>
            <el-input v-model="scope.row.costBudgetList[index].shopPaidReal" v-if="scope.row.type === 'expenditure'"></el-input>
            <el-input v-model="scope.row.costBudgetList[index].otherPaidReal" v-if="scope.row.type === 'other'"></el-input>
            <el-input v-model="scope.row.costBudgetList[index].totalPaidAmount" v-if="scope.row.type === 'subtotal'"></el-input>
          </template>
        </ux-table-column>
      </ux-table-column>
    </ux-grid>
  </el-row>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import { getByProjectId, bulkSave } from '@/api/code/codeManage'
export default {
  extends: BaseUI,
  name: 'cost-budget',
  components: {
  },
  data() {
    return {
      tableData: [],
      tableForm: {
        costBudgetList: []
      },
      project: {}
    }
  },
  computed: {},
  methods: {
    init(project) {
      this.project = project
      this.getTable(project)
    },
    getTable(row) {
      getByProjectId(row.id).then(res => {
        this.tableForm = res.data
        this.setTableData()
      })
    },
    setTableData() {
      this.tableData = [{ name: '人员投入【人月】', type: 'investment' }, { name: '人员成本【万元】', type: 'cost' }, { name: '采购支出【万元】', type: 'expenditure' },
      { name: '其他支出【万元】', type: 'other' }, { name: '小计【万元】', type: 'subtotal' }, { name: '差异比', type: 'diversity' }]
      this.tableData.map(item => {
        item.costBudgetList = this.tableForm.costBudgetList
        return item
      })
    }
  },
}
</script>
