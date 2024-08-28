<template>
  <div class="team-member-container">
    <div class="team-member-item" v-for="(item, index) in members" :key="index">
      <div class="member-post">{{item.teamRole.name}}</div>
      <div class="member-info">
        <el-row style="color: #555555">
          <span>{{item.user.name}}</span>
          <span class="member-info-divide">|</span>
          <span>{{item.user.sex}}</span>
          <span class="member-info-divide">|</span>
          <span>{{item.joinTime}}</span>
          <span style="margin-left: 50px" v-html="getMemberStatus(item)"></span>
        </el-row>
        <el-row>
          <i class="el-icon-message"></i>
          {{item.user.email}}
          <span class="member-info-divide">|</span>
          <i class="el-icon-phone"></i>
          {{item.user.phone}}
        </el-row>
        <el-row>
          <el-col :span="8">所属部门：{{item.user.department.name}}</el-col>
          <el-col :span="8">进入项目时间：{{item.joinTime}}</el-col>
          <el-col :span="8">离开项目时间：{{item.leaveTime}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="8">单价：¥ {{item.unitPriceCost}}</el-col>
          <el-col :span="8">计划人天：{{item.planHumanDay}}</el-col>
          <el-col :span="8">计划成本：¥ {{item.planCost}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="24">职责描述：{{item.remarks}}</el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import {listTeamMemberAll} from "@/api/team/teamMember";

export default {
  extends: BaseUI,
  name: 'team-member',
  components: {
  },
  data() {
    return {
      project: {},
      members: []
    }
  },
  props: {
  },
  computed: {},
  methods: {
    getMemberStatus(member) {
      let status = member.memberStatus
      let isLevel = status == 2
      return `<span style="color: ${isLevel ? '#D9001B' : '#02A7F0'}">${isLevel ? '已脱离' : '已加入'}</span>`
    },
    initMembers() {
      let search = {
        params: [{
          'columnName': 'project_id',
          'queryType': '=',
          'value': this.project.id
        }]
      }
      listTeamMemberAll(search).then(responseData => {
        if (responseData.code == 100) {
          this.members = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    init(project) {
      this.project = project
      this.initMembers()
    },
  },
  mounted() {

  }
}
</script>
<style lang="scss" scoped>
.team-member-container {
  .team-member-item {
    display: flex;
    border-bottom: 1px #ebebeb solid;
    margin-bottom: 20px;
    .member-post {
      width: 60px;
      height: 60px;
      line-height: 25px;
      padding: 5px;
      text-align: center;
      border-radius: 6px;
      background: #597ef7;
      color: #FFFFFF;
      font-weight: 400;
      margin-right: 30px;
    }
    .member-info {
      width: 100%;
      color: #AAAAAA;
      .el-row {
        line-height: 32px;
        height: 32px;
      }
      .member-info-divide {
        margin: 0 5px;
      }
    }
  }
}
</style>
