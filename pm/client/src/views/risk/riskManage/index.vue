<template>
  <el-row class="dc-container" v-loading="loading">
    <edit-form ref="editForm" v-on:save-finished="getList" v-on:before-load="setLoad"
      v-on:after-load="resetLoad"></edit-form>
    <el-form ref="queryForm" :model="queryFormData" label-width="100px" class="dc-el-form_ElQueryForm">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="" label="风险编号：" class="dc-el-form-item_SingleInput">
            <el-input v-model="queryFormData.riskCode" placeholder="请输入" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="" label="项目名称：" class="dc-el-form-item_SingleInput">
            <el-select clearable v-model="queryFormData.projectId" style="width:100%">
              <el-option v-for="item in projectOptions" :key="item.key" :value="item.key"
                :label="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="" label="风险名称：" class="dc-el-form-item_SelectInput">
            <el-input v-model="queryFormData.riskName" placeholder="请输入" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6" class="dc-el-form-item_SelectInput">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="getList" style="margin-left:20px" icon="el-icon-search" class="dc-el-button_Button"
              type="primary">查询</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button icon="el-icon-refresh" class="dc-el-button_Button" @click="handleReset">重置</el-button>
          </el-tooltip>
        </el-col>
        <el-col :span="24"></el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow"
      style="padding-bottom: 10px;border-bottom: 1px solid #d1caca;">
      <el-tooltip manual="true" class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">新增</el-button>
      </el-tooltip>
    </el-row>
    <div v-for="item, index in tableData" :key="item.id" class="riskTable">
      <div class="tableIndex" :style="{ 'background': background() }"> {{ index < 9 ? '0' + (index + 1) : index + 1
          }}</div>
          <div class="tableForm">
            <el-row>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">风险名称：</span><span class="tableName">{{
    item['riskName']
  }}</span>
              </el-col>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">项目名称：</span><span class="tableName">{{
    item.project.projectName
  }}</span>
              </el-col>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">风险编号：</span><span class="tableName">{{
    item['riskCode']
  }}</span>
              </el-col>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">所属里程碑：</span><span class="tableName">{{
    item['milestoneName']
  }}</span>
              </el-col>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">所属日程：</span><span class="tableName">{{
    item['scheduleName']
  }}</span>
              </el-col>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">风险类型：</span><span class="tableName">{{
    item['riskTypeName']
  }}</span>
              </el-col>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">风险概率：</span><span class="tableName">{{
    item['riskImpactName']
  }}</span>
              </el-col>
              <el-col :span="8" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">风险状态：</span><span class="tableName">{{
    item['riskStatusName']
  }}</span>
              </el-col>
              <el-col :span="24" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">风险内容：</span><span class="tableName">{{
    item['riskText']
  }}</span>
              </el-col>
              <el-col :span="24" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">应对方案：</span><span class="tableName">{{
    item['riskOption']
  }}</span>
              </el-col>
              <el-col :span="24" class="tableCol">
                <span class="tableIcon"></span> <span class="tableCloumn">附件：</span>
                <span class="tableName">{{
    (item['attachmentFile'].map(item => item.name)).toString()
  }}</span>
                <span class="tableView" v-if="item['attachmentFile'].length > 0"
                  @click="viewLoad(item['attachmentFile'])">预览</span> <span class="tableView"
                  v-if="item['attachmentFile'].length > 0" @click="downLoad(item['attachmentFile'])">下载</span>
              </el-col>
            </el-row>
          </div>
          <div style="width:70px">
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                <span style="color:#018cb7">操作</span> <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="JSON.stringify(item) + 'editData'">编辑</el-dropdown-item>
                <el-dropdown-item :command="JSON.stringify(item) + 'deleteData'">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

      </div>
      <el-pagination :total="tableDataTotal" :page-size="search.limit" :current-page="tableDataPage"
        :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
        class="dc-el-pagination_ElPagination"></el-pagination>
  </el-row>
</template>
<script>
import { listriskManage, deleteRisk, listProjectList } from '@/api/risk/riskManage'
import { deleteRequirementManage } from '@/api/requirement/requirementManage'
/** 根据用户界面配置import组件 开始 */
import EditForm from './form'
/** 根据用户界面配置import组件 结束 */
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import { fileDownById } from '@/api/sys/sysFile'
import { getLocalToken } from '@/utils/auth'
let Base64 = require('js-base64').Base64
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    EditForm,
    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {},
      tableData: [],
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      /** 根据用户界面配置生成data数据 结束 */
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      projectOptions: []
    }
  },
  computed: {},
  methods: {
    handleReset() {
      this.queryFormData = {};
      this.getList();
    },
    background() {
      return `rgba(${Math.round(Math.random() * 255)},
        ${Math.round(Math.random() * 255)},
        ${Math.round(Math.random() * 255)})`
    },
    getProject() {
      let params = {
        params: [{ columnName: "project_status", queryType: "in", value: [2, 3, 4] }]
      }
      listProjectList(params).then(res => {
        if (res.code == '100') {
          this.projectOptions = res.data
        }
      })
    },
    downLoad(list) {
      list.forEach(item => {
        this.onUploadViewFile(item)
      })
    },
    onUploadViewFile(row) { // 下载
      fileDownById(row.id).then(responseData => {
        const content = responseData;
        this.download(content, row.name);
      }).catch(error => {
        this.outputError(error)
      });
    },
    //下载方法
    download(content, fileName) {
      const blob = new Blob([content]);
      const url = window.URL.createObjectURL(blob);
      let dom = document.createElement("a");
      dom.style.display = "none";
      dom.href = url;
      dom.setAttribute("download", fileName);
      document.body.appendChild(dom);
      dom.click();
    },
    viewLoad(list) {
      list.forEach(item => {
        this.onViewFileList(item)
      })
    },
    onViewFileList(row) { // 预览
      let suffix = row.name.substring(row.name.lastIndexOf("."))
      let fileId = row.id + "" + suffix // 拼接参数，文件ID + 后缀名
      let token = getLocalToken()

      // 文件流访问路径
      let url = process.env.FILE_PREVIEW_URL + `sys/sysSeting/getFile/` + fileId + `?token=` + token
      window.open(process.env.KK_FILE_URL + encodeURIComponent(Base64.encode(url)));
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.queryFormData.riskCode) {
        this.search.params.push({
          "columnName": "risk_code",
          "queryType": "like",
          "value": `%${this.queryFormData.riskCode}%`
        },)
      }
      if (this.queryFormData.projectId) {
        this.search.params.push({
          "columnName": "project_Id",
          "queryType": "=",
          value: this.queryFormData.projectId
        },)
      }
      if (this.queryFormData.riskName) {
        this.search.params.push({
          "columnName": "risk_name",
          "queryType": "like",
          "value": `%${this.queryFormData.riskName}%`
        },)
      }
      // 数据权限: 团队成员信息team_member
      listriskManage(this.search)
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
    onSearch() {
      this.search.offset = 0
      this.tableDataPage = 1
      this.getList()
    },
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
    async pageInit() {
      this.onSearch()
    },
    onCreate() {
      this.$refs.editForm.$emit('openAddDialog')
    },
    onEdit(scope) {
      this.$refs.editForm.$emit('openEditDialog', scope)
    },
    handleCommand(val) {
      if (val.indexOf('editData') > 0) {
        //此时为编辑
        this.editData(val)
      }
      if (val.indexOf('deleteData') > 0) {
        //此时为删除
        this.deleteData(val)
      }
    },
    deleteData(val) {
      this.$confirm('确认删除该信息？删除后将无法恢复，请谨慎删除！', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let arr = val.split('deleteData')
          let obj = JSON.parse(arr[0])
          deleteRisk(obj).then(res => {
            if (res.code == '100') {
              this.$message({
                type: 'success',
                message: '删除成功'
              })
              this.getList()
            }
          })
        }).catch(() => { })
    },
    editData(val) {
      let arr = val.split('editData')
      let obj = JSON.parse(arr[0])
      this.onEdit(obj)
    },
    onDelete(scope) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          deleteRequirementManage(scope.row)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.getList()
                this.showMessage({
                  type: 'success',
                  msg: '删除成功'
                })
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        })
        .catch(() => { })
    },
  },
  watch: {},
  mounted() {
    this.pageInit()
    this.getProject()
  }
}
</script>
<style lang="scss" scoped>
.riskTable {
  display: flex;
  margin: 10px 0;
  border-bottom: 1px solid #d1caca;
  justify-content: space-between;
  height: auto;
  min-height: 200px;

  .tableIndex {
    height: 50px;
    width: 50px;
    border-radius: 10px;
    text-align: center;
    line-height: 50px;
    margin-left: 10px;
    margin-right: 20px;
    // background: rgba(Math.round(Math.random() * 255),
    //     Math.round(Math.random() * 255),
    //     Math.round(Math.random() * 255),
    //     1);
  }

  .tableForm {
    width: 100%;

    .tableCol {
      margin-bottom: 10px;

      .tableIcon {
        width: 8px;
        height: 8px;
        background: #018cb7;
        display: inline-block;
        border-radius: 50%;
        position: relative;
        top: -2px;
      }

      .tableCloumn {
        font-size: 14px;
        color: #999999;
      }

      .tableName {
        font-size: 14px;
      }

      .tableView {
        font-size: 12px;
        margin-left: 10px;
        color: #018cb7;
        cursor: pointer;
      }

    }
  }
}
</style>