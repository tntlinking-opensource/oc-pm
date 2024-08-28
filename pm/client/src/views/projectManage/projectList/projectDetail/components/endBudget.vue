<template>
  <div class="project-end-budget">
    <div class="dc-divide" style="margin-top: 0">
      <div>基本信息</div>
    </div>
    <el-row>
      <el-col :span="8">
        <span class="end-budget-title">项目名称：</span>
        <span class="end-budget-value">{{project.projectName}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">项目简称：</span>
        <span class="end-budget-value">{{project.projectShortName}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">项目编号：</span>
        <span class="end-budget-value">{{project.projectCode}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="end-budget-title">客户名称：</span>
        <span class="end-budget-value">{{project.custom ? project.custom.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">所属部门：</span>
        <span class="end-budget-value">{{project.department ? project.department.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">项目类型：</span>
        <span class="end-budget-value">{{project.projectType ? project.projectType.name : ''}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="end-budget-title">项目经理：</span>
        <span class="end-budget-value">{{project.projectManager ? project.projectManager.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">计划开始日期：</span>
        <span class="end-budget-value">{{project.planBeginDate}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">计划结束日期：</span>
        <span class="end-budget-value">{{project.planEndDate}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="end-budget-title">实际开始日期：</span>
        <span class="end-budget-value">{{project.planBeginDate}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">实际结束日期：</span>
        <span class="end-budget-value">{{project.planEndDate}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">项目状态：</span>
        <span class="end-budget-value">{{project.projectStatus ? project.projectStatus.name : ''}}</span>
      </el-col>
    </el-row>
    <div class="dc-divide">
      <div>项目预算投入</div>
    </div>
    <el-row>
      <el-col :span="8">
        <span class="end-budget-title">项目总预算：</span>
        <span class="end-budget-value">{{project.projectBudget}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">项目计划投入总人月：</span>
        <span class="end-budget-value">{{project.projectBudgetPersonMonth}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">计划投入人力成本：</span>
        <span class="end-budget-value">{{project.planLaborCost}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="end-budget-title">项目实际投入：</span>
        <span class="end-budget-value">{{project.totalCostReal}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">实际投入总人月：</span>
        <span class="end-budget-value">{{project.perInvestReal}}</span>
      </el-col>
      <el-col :span="8">
        <span class="end-budget-title">实际投入人力成本：</span>
        <span class="end-budget-value">{{project.perCostReal}}</span>
      </el-col>
    </el-row>
    <div class="dc-divide">
      <div>完成情况</div>
    </div>
    <el-row>
      <el-col :span="24">
        <span class="end-budget-title">完成情况：</span>
        <span class="end-budget-value">{{project.completeDesc}}</span>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
export default {
  extends: BaseUI,
  name: 'end-budget',
  components: {
  },
  data() {
    return {
      project: {}
    }
  },
  props: {
  },
  computed: {},
  methods: {
    init(project) {
      this.project = project
      console.log(project)
    },
  },
  mounted() {
    console.log(this.project)
  }
}
</script>
<style lang="scss" scoped>
.project-end-budget {
  .dc-divide {
    width: 100%;
    background-color: #ecf5ff;
    padding: 5px 10px;
    margin: 10px 0;
    border-radius: 4px;
    div {
      border-left: 4px #409eff solid;
      padding-left: 6px;
    }
  }
  .el-row {
    line-height: 32px;
    height: 32px;
  }
  .end-budget-title {
    color: #AAAAAA;
    width: 141px;
    display: inline-block;
    text-align: right;
  }
  .end-budget-value {
    color: #555555;
    white-space: nowrap;
  }
}
</style>
