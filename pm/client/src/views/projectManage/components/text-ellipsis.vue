<template>
  <el-tooltip class="text-ellipsis-tooltip" :disabled="!tooltip || !showTooltip" :content="text" transfer placement="right-start">
    <span ref="textEllipsis" class="text-ellipsis" :style="{maxWidth: maxWidth}" @mouseover="mouseover">
      <slot>
        {{text}}
      </slot>
    </span>
  </el-tooltip>
</template>

<script>

export default {
  name: "text-ellipsis",
  props: {
    // 显示的文字
    text: {
      type: String | Number,
      required: false
    },
    tooltip: {
      type: Boolean,
      default: true
    },
    // tooltip的max-width
    ttmWidth: {
      type: Number,
      default: 300
    },
    // 文本展示的最大宽度，超出后则展示为...
    width: {
      type: Number | String,
      default: 300
    }
  },
  data() {
    return {
      showTooltip: true
    }
  },
  methods: {
    mouseover() {
      let width = this.$refs.textEllipsis.offsetWidth
      this.showTooltip = (width === this.width)
      console.log(this.showTooltip)
    }
  },
  computed: {
    maxWidth() {
      return `${this.width}px`
    }
  }
}
</script>

<style scoped>
.text-ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: inline-block;
  font-size: 12px;
}
/*.text-ellipsis-tooltip, .text-ellipsis-tooltip .ivu-tooltip-rel {*/
/*  height: 100%;*/
/*}*/
</style>
