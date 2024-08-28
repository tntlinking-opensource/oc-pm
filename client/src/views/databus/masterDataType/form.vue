<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="70%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-tabs v-model="tabName" type="border-card" tab-position="top" class="dc-el-tabs_ElTabs">
          <el-tab-pane label="平台主数据" name="editForm" class="dc-el-tab-pane_ElTabPane">
            <el-form
              ref="editForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <el-form-item
                  prop="code"
                  label="主数据编码"
                  :rules="[{ required: true, message: '主数据编码不能为空', trigger: 'blur' }]"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.code"
                    :maxLength="16"
                    placeholder="请输入主数据编码"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  prop="name"
                  label="主数据名称"
                  :rules="[{ required: true, message: '主数据名称不能为空', trigger: 'blur' }]"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.name"
                    :maxLength="32"
                    placeholder="请输入主数据名称"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  prop="isSystem"
                  label="是否为系统主数据"
                  :rules="[{ required: true, message: '是否为系统主数据=', trigger: 'blur' }]"
                  class="dc-el-form-item_NumberInput"
                >
                  <el-select
                    v-model="editFormData.isSystem"
                    :min="0"
                    :max="9"
                    :precision="0"
                    placeholder="请输入是否为系统主数据"
                    clearable
                    class="dc-number-input_NumberInput"
                  >
                    <el-option
                    v-for="(item, index) in selectOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="remarks" label="备注信息" class="dc-el-form-item_MutilpleInput">
                  <el-input
                    v-model="editFormData.remarks"
                    type="textarea"
                    placeholder="请输入备注信息"
                    rows="2"
                    :maxLength="255"
                    class="dc-el-input_MutilpleInput"
                  ></el-input>
                </el-form-item>
              </el-row>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="主标准字段表" name="standardFieldTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddStandardFieldList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="standardFieldForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.standardFieldList"
                  ref="standardFieldTable"
                  :currentRow="table118CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="dataKey"
                    title="主键"
                    tree-node
                    resizable
                    min-width="100px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_NumberInput"
                  >
                    <template v-slot:header="{ column }">
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.dataKey'"
                        label-width="0px"
                        :rules="[{ required: true, message: '是否是主键', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <el-select
                          v-model="row.dataKey"
                          :min="0"
                          :max="9"
                          :precision="0"
                          placeholder="请选择是否是主键"
                          clearable
                          class="dc-number-input_NumberInput"
                        >
                           <el-option
                          v-for="(item, index) in selectOptions"
                          :key="index"
                          :label="item.label"
                          :value="item.value"
                           ></el-option>
                        </el-select>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.dataKey'"
                          label-width="0px"
                          :rules="[{ required: true, message: '是否是主键 ', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.dataKey==1
                              ? "是": "否"
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="filedCode"
                    title="字段编码"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.filedCode'"
                        label-width="0px"
                        :rules="[{ required: true, message: '字段编码不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.filedCode"
                          :maxLength="16"
                          placeholder="请输入字段编码"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.filedCode'"
                          label-width="0px"
                          :rules="[{ required: true, message: '字段编码不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.filedCode }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="filedName"
                    title="字段名称"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.filedName'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.filedName"
                          :maxLength="32"
                          placeholder="请输入字段名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.filedName'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.filedName }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="filedType"
                    title="字段类型"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.filedType'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-select
                          v-model="row.filedType"
                          :maxLength="16"
                          placeholder="请输入字段类型"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        >
                          <el-option
                          v-for="(item, index) in selectFieldOptions"
                          :key="index"
                          :label="item.label"
                          :value="item.value"
                           ></el-option>
                        </el-select>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.filedType'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.filedType== 0 ? "String" : row.filedType== 1 ? "BigInt" : "double"}}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="filedSize"
                    title="字段长度"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.filedSize'"
                        label-width="0px"
                        :rules="[{ required: true, message: '字段长度不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.filedSize"
                          :maxLength="4"
                          placeholder="请输入字段长度"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                          oninput="value=value.replace(/[^\d]/g,'')"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.filedSize'"
                          label-width="0px"
                          :rules="[{ required: true, message: '字段长度不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.filedSize }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="filedPrecision"
                    title="字段精度"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.filedPrecision'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.filedPrecision"
                          :maxLength="4"
                          placeholder="请输入字段精度"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                          oninput="value=value.replace(/[^\d]/g,'')"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.filedPrecision'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.filedPrecision }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="required"
                    title="是否必填"
                    tree-node
                    resizable
                    min-width="100px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_NumberInput"
                  >
                    <template v-slot:header="{ column }">
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.required'"
                        label-width="0px"
                        :rules="[{ required: true, message: '是否必填', trigger: 'blur' }]"
                        class="dc-el-form-item_NumberInput"
                      >
                        <el-select
                          v-model="row.required"
                          :min="0"
                          :max="9"
                          :precision="0"
                          placeholder="请输入是否必填"
                          clearable
                          class="dc-number-input_NumberInput"
                        >
                           <el-option
                          v-for="(item, index) in selectOptions"
                          :key="index"
                          :label="item.label"
                          :value="item.value"
                           ></el-option>
                        </el-select>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.required'"
                          label-width="0px"
                          :rules="[{ required: true, message: '是否必填 ', trigger: 'blur' }]"
                          class="dc-el-form-item_NumberInput"
                        >
                          {{
                            row.required==1
                              ? "是": "否"
                          }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="remarks"
                    title="备注信息"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_MutilpleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'standardFieldList.' + rowIndex + '.remarks'"
                        label-width="0px"
                        class="dc-el-form-item_MutilpleInput"
                      >
                        <el-input
                          v-model="row.remarks"
                          type="textarea"
                          placeholder="请输入备注信息"
                          rows="2"
                          :maxLength="255"
                          class="dc-el-input_MutilpleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'standardFieldList.' + rowIndex + '.remarks'"
                          label-width="0px"
                          class="dc-el-form-item_MutilpleInput"
                        >
                          {{ row.remarks }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    title="操作"
                    tree-node
                    resizable
                    width="50px"
                    min-width="50px"
                    fixed="right"
                    header-align="center"
                    v-if="action != 'view'"
                    class="dc-ux-table-column_ElTableOptColumn"
                  >
                    <template v-slot:header="scope">
                      <span>操作</span>
                    </template>
                    <template slot-scope="scope">
                      <OperationIcon
                        v-on:click="onDeleteChild(editFormData.standardFieldList, scope.rowIndex)"
                        type="danger"
                        content="删除"
                        placement="top"
                        icon-name="el-icon-delete"
                        class="dc-OperationIcon_IconButton"
                      ></OperationIcon>
                    </template>
                  </ux-table-column>
                </ux-grid>
              </el-row>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import { getMasterDataTypeById, saveMasterDataType } from '@/api/databus/masterDataType'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'masterDataType-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '平台主数据'
      },
      dialogTitle: '平台主数据',
      // 选项变量
      table118CurrentRow: {}, // 当前行
      tabName: 'editForm', // tab标签页
      // 窗口操作类型 view/edit/add
      action: '',
      selectOptions: [
        {
          label: '否',
          value: 0
        },
        {
          label: '是',
          value: 1
        }
      ],
      selectFieldOptions:[
         {
          label: 'String',
          value: "0"
        },
        {
          label: 'BigInt',
          value: "1"
        },
        {
          label: 'double',
          value: "2"
        }
        ]

    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    onSubmit() {
      let validFlag = true

      this.$refs['standardFieldForm'].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = 'standardFieldTable'
          validFlag = false
          return false
        }
      })

      this.$refs['editForm'].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = 'editForm'
          validFlag = false
          return false
        }
      })
      let standardFieldList = this.editFormData.standardFieldList;
      if(standardFieldList.length==0){
              this.showMessage({
              type: 'success',
              msg: "主数据标准字段没有配置"
            });
          validFlag = false

      }
      var i=0;
      for (let index = 0; index < standardFieldList.length; index++) {
        const element = standardFieldList[index];
        if(element.dataKey==1){
          i++;
        }
      }
      if(i!==1){
         this.showMessage({
              type: 'success',
              msg: "主数据标准字段主键不唯一"
            });
          validFlag = false

      }

      if (validFlag) {

        this.doSave()
      }
    },
    doSave() {
      this.setLoad()

      saveMasterDataType(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
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
    switchEdit() {
      this.action = 'edit'
      this.dialogProps.title = `修改${this.dialogTitle}`
      this.initOptions(this.editFormData)
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getMasterDataTypeById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
            resolve(form)
          })
          .catch((error) => {
            this.outputError(error)
          })
      })
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData(This) {
      return {
        code: '', // 主数据编码
        name: '', // 主数据名称
        isSystem: 0, // 是否为系统主数据 0：否 1：是
        remarks: '', // 备注信息
        standardFieldList: []
      }
    },

    onAddStandardFieldList() {
      this.editFormData.standardFieldList.push({
        filedCode: '', // 字段编码

        dataKey: 0,// 是否为主键

        filedName: '', // 字段名称

        filedType: "0", // 字段类型

        filedSize: '', // 字段长度

        filedPrecision: '', // 字段精度

        required: 1, // 是否必填 1必填  0非必填 默认必填

        remarks: '' // 备注信息
      })
    }
  },
  watch: {
    tabName(val, oldVal) {
      this.$nextTick(() => {
        if (this.$refs[val] && this.$refs[val].doLayout) {
          this.$refs[val].doLayout()
        }
      })
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.dialogProps.visible = true
      })
      this.$on('openCopyDialog', async function (id) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.tabName = 'editForm'
        this.editFormData.id = null //把id设置为空，添加一个新的

        for (var i = 0; i <= this.editFormData.standardFieldList.length - 1; i++) {
          this.editFormData.standardFieldList[i].id = null
        }

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
<style>
.my-input-sc {
  display: inline-block;
  height: 30px;
  line-height: 30px;
  -webkit-appearance: none;
  background-color: #ffffff;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  box-sizing: border-box;
  color: #606266;
  font-size: inherit;
  outline: none;
  padding: 0 15px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
}
</style>
