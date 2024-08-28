<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="80%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <div class="milestone-template-title">
        <div></div>
        <span>模板信息</span>
      </div>
      <el-row>
        <milestone-template ref="template" @load-item="loadItem"></milestone-template>
      </el-row>
      <div class="milestone-template-title">
        <div></div>
        <span>模板详情</span>
      </div>
      <el-row>
        <milestone-template-item ref="templateItem"></milestone-template-item>
      </el-row>

<!--      <span slot="footer" class="dialog-footer">-->
<!--        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>-->
<!--        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>-->
<!--        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>-->
<!--      </span>-->
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import BaseUI from '@/views/components/baseUI'
import MilestoneTemplate from '../milestoneTemplate'
import MilestoneTemplateItem from '../milestoneTemplateItem'
export default {
  extends: BaseUI,
  name: 'milestone-template-manage',
  components: {
    MilestoneTemplate,
    MilestoneTemplateItem
  },
  data() {
    return {
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '管理里程碑模板'
      }
    }
  },
  props: {
  },
  computed: {},
  methods: {
    /**
     * 加载模板详情
     * @author Yogaa
     * @date 16:29 2023/12/23
     */
    loadItem(template) {
      this.$refs.templateItem.loadTable(template)
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    openDialog() {
      this.dialogProps.visible = true
    }
  },
  mounted() {
  }
}
</script>
<style lang="scss" scoped>
  .milestone-template-title {
    width: 100%;
    line-height: 32px;
    height: 32px;
    background-color: #f1f5fd;
    margin-bottom: 5px;
    padding: 6px 10px;
    display: flex;
    align-items: center;
    font-size: 12px;
    div {
      width: 5px;
      height: 100%;
      background-color: #2277e0;
      display: inline-block;
      margin-right: 8px;
    }
  }
</style>
