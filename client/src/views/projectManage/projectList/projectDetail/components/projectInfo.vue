<template>
  <div class="project-info">
    <div class="dc-divide" style="margin-top: 0">
      <div>基本信息</div>
    </div>
    <el-row>
      <el-col :span="8">
        <span class="info-title">项目名称：</span>
        <span class="info-value">{{projectInfo.projectName}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">项目简称：</span>
        <span class="info-value">{{projectInfo.projectShortName}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">项目编号：</span>
        <span class="info-value">{{projectInfo.projectCode}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="info-title">客户名称：</span>
        <span class="info-value">{{projectInfo.custom ? projectInfo.custom.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">所属部门：</span>
        <span class="info-value">{{projectInfo.department ? projectInfo.department.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">项目类型：</span>
        <span class="info-value">{{projectInfo.projectType ? projectInfo.projectType.name : ''}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="info-title">项目经理：</span>
        <span class="info-value">{{projectInfo.projectManager ? projectInfo.projectManager.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">计划日期：</span>
        <span class="info-value">{{projectInfo.planBeginDate}}-{{projectInfo.planEndDate}}</span>
      </el-col>
    </el-row>
    <div class="dc-divide">
      <div>启动信息</div>
    </div>
    <el-row>
      <el-col :span="8">
        <span class="info-title">申请人：</span>
        <span class="info-value">{{projectInfo.applyBy}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">申请部门：</span>
        <span class="info-value">{{projectInfo.applyDepartment ? projectInfo.applyDepartment.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">申请日期：</span>
        <span class="info-value">{{projectInfo.applyDate}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="info-title">立项商机：</span>
        <span class="info-value">{{projectInfo.projectBusiOpp ? projectInfo.projectBusiOpp.name : ''}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">商机编号：</span>
        <span class="info-value">{{projectInfo.projectBusiOpp ? projectInfo.projectBusiOpp.value : ''}}</span>
      </el-col>
    </el-row>
    <div class="dc-divide">
      <div>项目干系人</div>
    </div>
    <el-row>
      <el-col :span="8">
        <span class="info-title">项目核心成员：</span>
        <span class="info-value">{{projectInfo.projectCoreMember}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">市场负责人：</span>
        <span class="info-value">{{projectInfo.marketManager}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">客户姓名：</span>
        <span class="info-value">{{projectInfo.customName}}</span>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <span class="info-title">客户角色：</span>
        <span class="info-value">{{projectInfo.customRole}}</span>
      </el-col>
    </el-row>
    <div class="dc-divide">
      <div>项目人月投入计划</div>
    </div>
    <el-row>
      <el-col :span="8">
        <span class="info-title">项目总预算：</span>
        <span class="info-value">{{projectInfo.projectBudget}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">项目计划投入总人月：</span>
        <span class="info-value">{{projectInfo.projectBudgetPersonMonth}}</span>
      </el-col>
      <el-col :span="8">
        <span class="info-title">计划投入人力成本：</span>
        <span class="info-value">{{projectInfo.planLaborCost}}</span>
      </el-col>
    </el-row>
    <div class="dc-divide">
      <div>其他信息</div>
    </div>
    <el-row>
      <el-col :span="24">
        <span class="info-title">项目描述：</span>
        <span class="info-value">{{projectInfo.projectDesc}}</span>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
export default {
  extends: BaseUI,
  name: 'project-info',
  components: {
  },
  data() {
    return {
      projectInfo: {}
    }
  },
  props: {
  },
  computed: {},
  methods: {
    init(projectInfo) {
      this.projectInfo = projectInfo
      console.log(projectInfo)
    },
  },
  mounted() {

  }
}
</script>
<style lang="scss" scoped>
.project-info {
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
  .info-title {
    color: #AAAAAA;
    width: 141px;
    display: inline-block;
    text-align: right;
  }
  .info-value {
    color: #555555;
    white-space: nowrap;
  }
}
</style>
