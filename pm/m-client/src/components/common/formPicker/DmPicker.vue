<template>
  <div>
    <van-field
      :value="valueText"
      :clickable="!disabled"
      readonly
      is-link
      v-bind="$attrs"
      v-on="$listeners"
      @click="disabled ? '' : showPicker = true"
    />
    <van-popup v-model="showPicker" position="bottom" v-bind="$attrs" v-on="$listeners" @open="onOpen">
      <van-picker
        ref="picker"
        show-toolbar
        :value-key="valueKey"
        @cancel="onCancel"
        @confirm="onConfirm"
        @change="onChange"
        :columns="columns"
        :default-index="defaultIndex"
        v-bind="$attrs"
      />
    </van-popup>
  </div>
</template>

<script>
export default {
  name: 'DmPicker',
  model: {
    prop: 'value',
    event: 'oninput'
  },
  props: {
    value: {
      type: [String, Number]
    },
    valueKey: {
      type: String,
      default: () => {
        return 'label'
      }
    },
    columns: Array,
    disabled: Boolean,
    defaultIndex: {
      type: [String, Number]
    }
  },
  data() {
    return {
      keyName: '',
      valueText: '',
      currentValue: this.value,
      showPicker: false
    }
  },
  watch: {
    value(val, old) {
      this.handleValue(val)
    },
    columns(val, old) {
      this.handleValue(this.value)
    }
  },
  mounted() {
    this.handleValue(this.value)
  },
  methods: {
    handleValue(value) {
      if (value) {
        if (Object.prototype.toString.call(value) === '[object Object]' && this.valueKey) {
          this.valueText = value[this.valueKey]
          this.currentValue = value
        } else {
          const keyObj = this.handleKeyObj(value)
          if (keyObj) {
            this.valueText = keyObj.obj[this.valueKey]
            this.currentValue = keyObj.obj[keyObj.keyName]
          }
        }
      }

    },
    handleKeyObj(value) {
      let obj = this.columns.find((item, index) => {
        for (let key in item) {
          if (value == item[key]) {
            this.keyName = key
            return value == item[key]
          }
        }
      })
      return obj ? { keyName: this.keyName, obj } : null
    },
    updateValue(value) {
      if (Object.prototype.toString.call(this.value) === '[object Object]' && this.valueKey) {
        this.currentValue = value
      } else {
        this.currentValue = value[this.keyName]
      }
    },
    getSelectedIndex(key, columns) {
      if (!columns) return 0
      let index = null
      if (this.valueKey) {
        for (let i = 0; i < columns.length; i++) {
          if (key === columns[i][this.valueKey]) {
            index = i
            break
          }
        }
      } else {
        index = columns.findIndex(item => item === key)
      }
      return index
    },
    onConfirm(value, index) {
      this.showPicker = false
      this.updateValue(value)
      this.$emit('oninput', this.currentValue)
      this.$emit('confirm', this.currentValue, index)
    },
    onChange(picker, value, index) {
      this.updateValue(value)
      this.$emit('change', picker, this.currentValue, index)
    },
    onCancel(value, index) {
      this.showPicker = false
      this.$emit('cancel', this.currentValue, index)
    },
    onOpen() {
      if (this.value) {
        this.defaultIndex = this.getSelectedIndex(this.valueText, this.columns)
      }
    }
  }
}
</script>

<style scoped>

</style>
