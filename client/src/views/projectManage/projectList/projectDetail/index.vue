<template>
  <el-row class="dc-container">
    <el-dialog
      @open="onDialogOpen"
      @close="onDialogClose"
      v-loading="loading"
      width="90%"
      :fullscreen="true"
      title="项目详情"
      :visible.sync="show.dialog"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <div style="display: flex">
        <div style="width: 100%; margin-right: 190px;" v-if="show.dialog">
          <ProjectInfo ref="projectInfo" v-show="activeIndex === 0"></ProjectInfo>
          <TeamMember ref="teamMember" v-show="activeIndex === 1"></TeamMember>
          <RequireList ref="requireList" v-show="activeIndex === 2"></RequireList>
          <MilestonePlan ref="milestonePlan" v-show="activeIndex === 3"></MilestonePlan>
          <ProjectRisk ref="projectRisk" v-show="activeIndex === 4"></ProjectRisk>
          <ProjectDoc ref="projectDoc" v-show="activeIndex === 5"></ProjectDoc>
          <CostBudget ref="costBudget" v-show="activeIndex === 6"></CostBudget>
          <EndBudget ref="endBudget" v-show="activeIndex === 7"></EndBudget>
        </div>
        <div style="width: 200px; position: fixed; right: 10px; top: 105px;">
          <el-timeline>
            <el-timeline-item
              v-for="(item, index) in detailInfos"
              :key="index"
              :color="activeIndex === index ? '#409eff' : ''">
              <div style="cursor: pointer;" @click="activeIndex = index">{{item.content}}</div>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import ProjectInfo from './components/projectInfo'
import TeamMember from './components/teamMember'
import RequireList from "./components/requireList"
import MilestonePlan from "./components/milestonePlan";
import ProjectRisk from "./components/projectRisk";
import ProjectDoc from "./components/projectDoc";
import CostBudget from "./components/costBudget";
import EndBudget from "./components/endBudget";
export default {
  // extends: BaseUI,
  name: 'project-detail',
  components: {
    ProjectInfo,
    TeamMember,
    RequireList,
    MilestonePlan,
    ProjectRisk,
    ProjectDoc,
    CostBudget,
    EndBudget
  },
  data() {
    return {
      show: {
        dialog: false
      },
      activeIndex: 0,
      detailInfos: [
        {content: '项目信息'},
        {content: '团队成员'},
        {content: '需求清单'},
        {content: '里程碑计划'},
        {content: '项目风险'},
        {content: '项目文档'},
        {content: '成本预算'},
        {content: '结项信息'}
      ]
    }
  },
  props: {
  },
  computed: {},
  methods: {
    init(projectInfo) {
      this.onDialogOpen(projectInfo)
    },
    initComponents(projectInfo) {
      let refs = ['projectInfo', 'teamMember', 'requireList', 'milestonePlan', 'projectRisk', 'projectDoc', 'costBudget', 'endBudget']
      refs.forEach(ref => {
        this.$nextTick(() => {
          this.$refs[ref].init(projectInfo)
        })
      })
    },
    onDialogOpen(projectInfo) {
      if (!projectInfo) {
        return
      }
      this.show.dialog = true
      this.activeIndex = 0
      this.initComponents(projectInfo)
    },
    onDialogClose() {
      this.show.dialog = false
    }
  },
  mounted() {
  }
}
</script>
<style>
</style>
