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
      <ux-grid
        column-key
        ref="table"
        :data="tableData"
        border
        v-on:sort-change="onSortChange"
        v-on:header-dragend="onWidthChange($refs.table)"
        :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
        class="dc-ux-grid_QueryTable"
      >
        <ux-table-column
          :field="tableColumns['129'].field"
          :title="tableColumns['129'].title"
          :width="tableColumns['129'].width"
          :visible="tableColumns['129'].visible"
          :params="{ sortId: '129', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['130'].field"
          :title="tableColumns['130'].title"
          :width="tableColumns['130'].width"
          :visible="tableColumns['130'].visible"
          :params="{ sortId: '130', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['131'].field"
          :title="tableColumns['131'].title"
          :width="tableColumns['131'].width"
          :visible="tableColumns['131'].visible"
          :params="{ sortId: '131', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['132'].field"
          :title="tableColumns['132'].title"
          :width="tableColumns['132'].width"
          :visible="tableColumns['132'].visible"
          :params="{ sortId: '132', summary: false }"
          tree-node
          resizable
          min-width="90px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['133'].field"
          :title="tableColumns['133'].title"
          :width="tableColumns['133'].width"
          :visible="tableColumns['133'].visible"
          :params="{ sortId: '133', summary: false }"
          tree-node
          resizable
          min-width="80px"
          align="right"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['134'].field"
          :title="tableColumns['134'].title"
          :width="tableColumns['134'].width"
          :visible="tableColumns['134'].visible"
          :params="{ sortId: '134', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        >
        </ux-table-column>
        <ux-table-column
          :field="tableColumns['135'].field"
          :title="tableColumns['135'].title"
          :width="tableColumns['135'].width"
          :visible="tableColumns['135'].visible"
          :params="{ sortId: '135', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['136'].field"
          :title="tableColumns['136'].title"
          :width="tableColumns['136'].width"
          :visible="tableColumns['136'].visible"
          :params="{ sortId: '136', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="center"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        >
          <template slot-scope="{ row, rowIndex }">
            <div
              v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case '0':
                    return '未推送'
                  case '1':
                    return '已推送'
                    case '2':
                    return '推送失败'
                     case '3':
                    return '推送成功'
                  default:
                    return '状态未知'
                }
              })(row.pushStatus, row, rowIndex)
            "
            ></div>
          </template>
        </ux-table-column>
        <ux-table-column
          :field="tableColumns['137'].field"
          :title="tableColumns['137'].title"
          :width="tableColumns['137'].width"
          :visible="tableColumns['137'].visible"
          :params="{ sortId: '137', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['138'].field"
          :title="tableColumns['138'].title"
          :width="tableColumns['138'].width"
          :visible="tableColumns['138'].visible"
          :params="{ sortId: '138', summary: false }"
          tree-node
          resizable
          min-width="80px"
          align="right"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['139'].field"
          :title="tableColumns['139'].title"
          :width="tableColumns['139'].width"
          :visible="tableColumns['139'].visible"
          :params="{ sortId: '139', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['140'].field"
          :title="tableColumns['140'].title"
          :width="tableColumns['140'].width"
          :visible="tableColumns['140'].visible"
          :params="{ sortId: '140', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['141'].field"
          :title="tableColumns['141'].title"
          :width="tableColumns['141'].width"
          :visible="tableColumns['141'].visible"
          :params="{ sortId: '141', summary: false }"
          tree-node
          resizable
          min-width="160px"
          align="right"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['142'].field"
          :title="tableColumns['142'].title"
          :width="tableColumns['142'].width"
          :visible="tableColumns['142'].visible"
          :params="{ sortId: '142', summary: false }"
          tree-node
          resizable
          min-width="180px"
          align="left"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
        <ux-table-column
          :field="tableColumns['143'].field"
          :title="tableColumns['143'].title"
          :width="tableColumns['143'].width"
          :visible="tableColumns['143'].visible"
          :params="{ sortId: '143', summary: false }"
          tree-node
          resizable
          min-width="160px"
          align="right"
          header-align="center"
          sortable
          show-overflow
          class="dc-ux-table-column_ElTableColumn"
        ></ux-table-column>
      </ux-grid>
      <el-pagination
        :total="tableDataTotal"
        :page-size="search.limit"
        background
        :current-page="tableDataPage"
        :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onSizeChange"
        v-on:current-change="onCurrentChange"
        class="dc-el-pagination_ElPagination"
      ></el-pagination>
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
import DcMain from '@/views/components/dcMain'
/** 根据用户界面配置import组件 结束 */
import { listMasterDataPushRecordsPage, deleteMasterDataPushRecords } from '@/api/databus/masterDataPushRecords'
/** 根据用户界面配置import组件 结束 */
import { getMasterDataBatchRecordsById, saveMasterDataBatchRecords } from '@/api/databus/masterDataBatchRecords'
import { listMasterDataPushInterfaceRecordsPage } from '@/api/databus/masterDataPushInterfaceRecords'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import PushIndex from "./pushform.vue";
export default {
  extends: BaseUI,
  name: 'masterDataBatchRecords-form',
  components: {
    PushIndex,
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
        title: '接口推送记录'
      },
      dialogTitle: '接口推送记录',
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      tableData: [],
      // 查询表格列头
      tableColumns: {
        129: {
          title: '主数据批记录表id',
          field: 'masterDataBatchRecordsId',
          visible: false,
          order: 0
        },
        130: {
          title: '租户名称',
          field: 'tenantId',
          visible: false,
          order: 1
        },
        131: {
          title: '主数据编码',
          field: 'masterDataType.code',
          visible: false,
          order: 2
        },
        132: {
          title: '主数据',
          field: 'masterDataType.name',
          visible: false,
          order: 3
        },
        133: {
          title: '数据版本',
          field: 'masterDataBatchRecords.masterDataRecordsList.dataVersion',
          visible: false,
          order: 4
        },
        134: {
          title: '接收应用',
          field: 'targetAppId',
          visible: false,
          order: 5
        },
        135: {
          title: '最后发送时间',
          field: 'pushDate',
          visible: true,
          order: 6
        },
        136: {
          title: '推送状态',
          field: 'pushStatus',
          visible: true,
          order: 7
        },
        137: {
          title: '推送失败信息',
          field: 'pushData',
          visible: true,
          order: 8
        },
        138: {
          title: '推送失败次数',
          field: 'pushFailSize',
          visible: false,
          order: 9
        },
        139: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 10
        },
        140: {
          title: '创建者',
          field: 'createBy',
          visible: false,
          order: 11
        },
        141: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 12
        },
        142: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 13
        },
        143: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 14
        },
        114: {
          title: '操作',
          width: '140px',
          visible: true,
          order: 15
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      /** 根据用户界面配置生成data数据 结束 */
      version: 2,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1564075923546759170',
      schemeId: '1564075923546759168'
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
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
      }
      // 数据权限: 查看master_data_push_records
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listMasterDataPushInterfaceRecordsPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total
            this.tableData = responseData.data.rows ? responseData.data.rows : []
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    onView() {
      let checkBoxData = this.$refs.table.getCheckboxRecords();
      if (checkBoxData.length!=1){
        this.showMessage({
          type: 'warning',
          msg: '请选中一行数据查看'
        })
        return;
      }
      // console.log(this.$refs.table.getRadioReserveRecord())
      this.$refs.PushInterfaceIndex.$emit('openViewDialog', checkBoxData[0].id)
    },
    doSave() {
      this.setLoad()

      saveMasterDataBatchRecords(this.editFormData)
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
        getMasterDataBatchRecordsById(id)
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
    // onDialogOpen() {
    //   this.$nextTick(() => {
    //     this.search.params.push({'columnName':'master_data_batch_records_id', 'queryType': '=', 'value':id});
    //     this.getList()
    //     // this.$refs['editForm'].clearValidate()
    //   })
    // },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    initEditData(This) {
      return {
        dataBatch: null, // 主数据批记录
        tenantId: '', // 租户id
        masterDataTypeId: '', // 主数据类型id
        sourceAppId: '', // 应用来源
        receiveDate: '', // 接收时间
        targetAppId: '', // 需要发送到应的应用
        pushStatus: '', // 推送状态
        lastSendTime: '', // 最后发送时间
        remarks: '' // 备注信息
      }
    },

  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.search.params.push({'columnName':'master_data_push_records_id', 'queryType': '=', 'value':id});
        this.getList()
        this.dialogProps.visible = true
        // this.pageInit()
        // this.columnDrop(this.$refs.table)
      })
    })
  }
}
</script>
