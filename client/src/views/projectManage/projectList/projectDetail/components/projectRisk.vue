<template>
  <div class="project-risk-container">
    <div class="project-risk-item" v-for="(item, index) in risks" :key="index">
      <div class="risk-index">{{index + 1}}</div>
      <div class="risk-info">
        <el-row>
          <el-col :span="8">
            <span class="risk-title">风险名称：</span>
            <span class="risk-value">{{item.riskName}}</span>
          </el-col>
          <el-col :span="8">
            <span class="risk-title">风险编号：</span>
            <span class="risk-value">{{item.riskCode}}</span>
          </el-col>
          <el-col :span="8">
            <span class="risk-title">所属里程碑：</span>
            <span class="risk-value">{{item.milestoneName}}</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <span class="risk-title">所属日程：</span>
            <span class="risk-value">{{item.scheduleName}}</span>
          </el-col>
          <el-col :span="8">
            <span class="risk-title">风险类型：</span>
            <span class="risk-value">{{item.riskTypeName}}</span>
          </el-col>
          <el-col :span="8">
            <span class="risk-title">影响程度：</span>
            <span class="risk-value">{{item.cd}}</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <span class="risk-title">风险内容：</span>
            <span class="risk-value">{{item.riskText}}</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <span class="risk-title">应对方案：</span>
            <span class="risk-value">{{item.riskOption}}</span>
          </el-col>
        </el-row>
<!--        <el-row>-->
<!--          <el-col :span="24">-->
<!--            <span class="risk-title">附件：</span>-->
<!--            <span class="risk-value">{{item.fj}}</span>-->
<!--          </el-col>-->
<!--        </el-row>-->
      </div>
    </div>
  </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import {listRiskManageAll} from "@/api/risk/riskManage";
export default {
  extends: BaseUI,
  name: 'project-risk',
  components: {
  },
  data() {
    return {
      project: {},
      risks: []
    }
  },
  props: {
  },
  computed: {},
  methods: {
    initRisks() {
      let search = {
        params: [{
          'columnName': 'project_id',
          'queryType': '=',
          'value': this.project.id
          // value: '1861953878566559821'
        }]
      }
      listRiskManageAll(search).then(responseData => {
        if (responseData.code == 100) {
          this.risks = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    init(project) {
      this.project = project
      this.initRisks()
    },
  },
  mounted() {

  }
}
</script>
<style lang="scss" scoped>
.project-risk-container {
  .project-risk-item {
    display: flex;
    border-bottom: 1px #ebebeb solid;
    margin-bottom: 20px;
    .risk-index {
      width: 50px;
      height: 50px;
      line-height: 40px;
      padding: 5px;
      text-align: center;
      border-radius: 6px;
      background: #597ef7;
      color: #FFFFFF;
      font-weight: 400;
      margin-right: 30px;
    }
    .risk-info {
      width: 100%;
      color: #AAAAAA;
      .el-row {
        line-height: 32px;
        height: 32px;
      }
      .risk-title::before {
        content: '';
        display: inline-block;
        width: 5px;
        height: 5px;
        border-radius: 50%;
        background-color: #409eff;
        margin: 0 5px 2px 0;
      }
      .risk-value {
        color: #555555;
      }
    }
  }
}
</style>
