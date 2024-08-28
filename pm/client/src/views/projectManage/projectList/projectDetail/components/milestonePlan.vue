<template>
  <div class="milestone-plan-container">
    <div class="milestone-plan-item" v-for="(item, index) in milestones" :key="index">
      <div class="milestone-plan-index">{{index + 1}}</div>
      <div class="milestone-plan-info">
        <el-row>
          <el-col :span="12">
            <div style="color: #555555">{{item.milestoneName}}</div>
            <div>{{item.milestoneDesc}}</div>
          </el-col>
          <el-col :span="4">
            <div>开始时间</div>
            <div>{{item.planBegin}}</div>
          </el-col>
          <el-col :span="8" style="padding: 24px 0">
            <el-progress :color="item.color" :percentage="item.rate"></el-progress>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import {listMilestoneManageAll} from "@/api/projectManage/milestoneManage";
export default {
  extends: BaseUI,
  name: 'milestone-plan',
  components: {
  },
  data() {
    return {
      project: {},
      milestones: []
    }
  },
  props: {
  },
  computed: {},
  methods: {
    /**
     * 处理里程碑进度百分比
     * @author Yogaa
     * @date 14:45 2024/1/3
     */
    wrapperMilestones(milestones) {
      milestones.forEach(item => {
        let rate = 0
        // 里程碑下的日程数量
        let scheduleCnt = item.scheduleCnt
        if (scheduleCnt === 0) {
          let status = item.milestoneStatus.value
          // 里程碑下无日程，则根据里程碑状态判断，状态=已保存时进度比=0%；状态=进行中时进度比=50%；状态=已完成时进度比=100%
          rate = (status == 1 ? 50 : status == 2 ? 100 : 0)
        } else {
          // 里程碑下的已完成日程数量
          let scheduleCompletedCnt = item.scheduleCompletedCnt
          rate = parseInt(scheduleCompletedCnt * 100 / scheduleCnt)
        }
        let color = rate === 0 ? '#999999' : rate === 100 ? '#52C41A' : '#1890FF'
        item.rate = rate
        item.color = color
      })
    },
    initMilestone() {
      let search = {
        params: [{
          'columnName': 'project_id',
          'queryType': '=',
          'value': this.project.id
        }]
      }
      listMilestoneManageAll(search).then(responseData => {
        if (responseData.code == 100) {
          let data = responseData.data
          this.wrapperMilestones(data)
          this.milestones = data
          console.log(this.milestones)
        } else {
          this.showMessage(responseData)
        }
      })
    },
    init(project) {
      this.project = project
      this.initMilestone()
    },
  },
  mounted() {

  }
}
</script>
<style lang="scss" scoped>
.milestone-plan-container {
  .milestone-plan-item {
    display: flex;
    align-items: center;
    border-bottom: 1px #ebebeb solid;
    margin-bottom: 10px;
    padding-bottom: 10px;
    .milestone-plan-index {
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
    .milestone-plan-info {
      width: 100%;
      color: #AAAAAA;
      .el-row {
        line-height: 32px;
        height: 32px;
      }
      .milestone-plan-info-divide {
        margin: 0 5px;
      }
    }
  }
}
</style>
