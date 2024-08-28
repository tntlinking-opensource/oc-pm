<template>
  <el-row class="dc-container">
    <el-dialog v-on:open="onDialogOpen" v-on:close="onDialogClose" v-loading="loading" width="80%" :fullscreen="false"
      title="人力资源统筹绩效管理项目" :visible.sync="visible" :close-on-click-modal="false" class="dc-el-dialog_ElDialog">
      <h3 style="position: absolute;top: 3px;left: 300px;">{{ tableForm.startAndEndDate }}</h3>
      <el-row style="position: relative;top: -20px;">
        <el-col :span="8">可支配预算金额：{{ tableForm.projectBudget }}</el-col>
        <el-col :span="8">已预算金额：{{ allBudgetAmount }}</el-col>
        <el-col :span="8">实际支出金额：{{ allPaidAmount }}</el-col>
      </el-row>
      <ux-grid border show-overflow ref="plxTable" :data="tableData" keep-source height="400" :highlightCurrentRow="false"
        :edit-config="{ trigger: 'click', mode: 'cell' }" :span-method="objectSpanMethod">
        <ux-table-column field="name" width="160" align="center" header-align="center">
        </ux-table-column>
        <ux-table-column v-for="(item, index) in tableForm.costBudgetList" :key="item.id" :title="item.budgetDate"
          align="center" header-align="center">
          <ux-table-column title="预算" edit-render min-width="140" align="center" header-align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.type === 'investment'">{{
                scope.row.costBudgetList[index].perInvestBudget }}</span>
              <span v-if="scope.row.type === 'cost'">{{
                scope.row.costBudgetList[index].perCostBudget }}</span>
              <span v-if="scope.row.type === 'expenditure'">{{ scope.row.costBudgetList[index].shopPaidBudget }}</span>
              <span v-if="scope.row.type === 'other'">{{ scope.row.costBudgetList[index].otherPaidBudget }}</span>
              <span v-if="scope.row.type === 'subtotal'">{{ scope.row.costBudgetList[index].totalBudgetAmount }}</span>
              <span v-if="scope.row.type === 'diversity'">{{ scope.row.costBudgetList[index].diffScale }}</span>
            </template>
            <template v-slot:edit="scope">
              <el-input-number v-model="scope.row.costBudgetList[index].perInvestBudget"
                v-if="scope.row.type === 'investment'"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].perCostBudget"
                v-if="scope.row.type === 'cost'" @change="updateTotalValue(scope.row.costBudgetList[index].perCostBudget,scope.row,index,item)"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].shopPaidBudget"
                v-if="scope.row.type === 'expenditure'" @change="updateTotalValue(scope.row.costBudgetList[index].perCostBudget,scope.row,index,item)"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].otherPaidBudget"
                v-if="scope.row.type === 'other'" @change="updateTotalValue(scope.row.costBudgetList[index].perCostBudget,scope.row,index,item)"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].totalBudgetAmount"
                v-if="scope.row.type === 'subtotal'"></el-input-number>
              <el-input v-model="scope.row.costBudgetList[index].diffScale"
                v-if="scope.row.type === 'diversity'"></el-input>
            </template>
          </ux-table-column>
          <ux-table-column title="实际" min-width="140" edit-render align="center" header-align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.type === 'investment'">{{
                scope.row.costBudgetList[index].perInvestReal }}</span>
              <span v-if="scope.row.type === 'cost'">{{
                scope.row.costBudgetList[index].perCostReal }}</span>
              <span v-if="scope.row.type === 'expenditure'">{{ scope.row.costBudgetList[index].shopPaidReal }}</span>
              <span v-if="scope.row.type === 'other'">{{ scope.row.costBudgetList[index].otherPaidReal }}</span>
              <span v-if="scope.row.type === 'subtotal'">{{ scope.row.costBudgetList[index].totalPaidAmount }}</span>
            </template>
            <template v-slot:edit="scope">
              <el-input-number v-model="scope.row.costBudgetList[index].perInvestReal" type='number'
                v-if="scope.row.type === 'investment'"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].perCostReal"
                v-if="scope.row.type === 'cost'" @change="updateTotalValue(scope.row.costBudgetList[index].perCostBudget,scope.row,index,item)"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].shopPaidReal"
                v-if="scope.row.type === 'expenditure'" @change="updateTotalValue(scope.row.costBudgetList[index].perCostBudget,scope.row,index,item)"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].otherPaidReal"
                v-if="scope.row.type === 'other'" @change="updateTotalValue(scope.row.costBudgetList[index].perCostBudget,scope.row,index,item)"></el-input-number>
              <el-input-number v-model="scope.row.costBudgetList[index].totalPaidAmount"
                v-if="scope.row.type === 'subtotal'"></el-input-number>
            </template>
          </ux-table-column>
        </ux-table-column>
      </ux-grid>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import { getByProjectId, bulkSave } from '@/api/code/codeManage'
export default {
  extends: BaseUI,
  name: 'documentManage-form',
  components: {
  },
  data() {
    return {
      visible: false,
      action: '',
      tableData: [],
      tableForm: {
        costBudgetList: []
      }
    }

  },
  props: {
  },
  computed: {
    allBudgetAmount() {
      if (this.tableForm && this.tableForm.costBudgetList.length > 0) {
        let num = 0
        this.tableForm.costBudgetList.forEach(item => {
          num += item.totalBudgetAmount
        })
        return num
      }
      return 0
    },
    allPaidAmount() {
      if (this.tableForm && this.tableForm.costBudgetList.length > 0) {
        let num = 0
        this.tableForm.costBudgetList.forEach(item => {
          num += item.totalPaidAmount
        })
        return num
      }
      return 0
    }
  },
  methods: {
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (row.type === 'diversity' && columnIndex === 0 && column.property !== 'name') {
        return {
          rowspan: 1,
          colspan: 2
        }
      } else if (row.type === 'diversity' && columnIndex !== 0 && column.property !== 'name') {
        return {
          rowspan: 1,
          colspan: 0
        }
      }
    },

    onSubmit() {
      this.setLoad()
      let params = this.tableForm.costBudgetList
      params.map(item => {
        if (!item.id) {
          item.id = ''
        }
        return item
      })
      bulkSave(params)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.$emit('save-finished')
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onDialogClose() {
      this.visible = false
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
    },
    updateTotalValue(num,scope,index,item){
      // console.log(this.$refs.plxTable,scope,index/*,item*/)
      // console.log(parseFloat(item.otherPaidBudget)+parseFloat(item.perCostBudget)+parseFloat(item.shopPaidBudget));
      scope.costBudgetList[index].totalBudgetAmount = parseFloat(item.otherPaidBudget)+parseFloat(item.perCostBudget)+parseFloat(item.shopPaidBudget);
      scope.costBudgetList[index].totalPaidAmount = parseFloat(item.otherPaidReal)+parseFloat(item.perCostReal)+parseFloat(item.shopPaidReal);
      // console.log(this.tableData);
      this.$refs.plxTable.reloadData(this.tableData);
    },
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (row) {
        this.action = 'view'
        this.visible = true
        this.getTable(row)
      })

      this.$on('openEditDialog', async function (row) {
        this.action = 'edit'
        this.visible = true
        this.getTable(row)
      })
      this.$on('openAddDialog', function (data) {
        this.action = 'add'
        this.visible = true
        this.tableForm = JSON.parse(JSON.stringify(data))
        this.setTableData()
      })

    })
  }
}
</script>
