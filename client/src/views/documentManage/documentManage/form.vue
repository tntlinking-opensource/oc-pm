<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="60%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item prop="" label="项目名称" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.projectId"
                  :style="{ width: '100%' }"
                  placeholder="请选择"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                  v-on:change="upprojiectid"
                >
                  <el-option
                    v-for="(item, index) in selectOptions1"
                    :key="index"
                    :label="item.projectName"
                    :value="item.projectCode"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="projectId" label="项目编号" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.projectId"
                  :maxLength="-1"
                  placeholder=""
                  clearable
                  :disabled="true"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="documentName"
                label="文档名称"
                :rules="[{ required: true, message: '文档名称不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.documentName"
                  :maxLength="50"
                  placeholder="请输入文档名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="milestoneId" label="所属里程碑" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.milestoneId"
                  :style="{ width: '100%' }"
                  placeholder="请选择所属里程碑"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                  v-on:change="upmilestoneid"
                >
                  <el-option
                    v-for="(item, index) in milestoneIdOptions"
                    :key="index"
                    :label="item.milestoneName"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="scheduleId" label="所属日程" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.scheduleId"
                  :style="{ width: '100%' }"
                  placeholder="请选择所属日程"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in scheduleIdOptions"
                    :key="index"
                    :label="item.scheduleName"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="documentType" label="文档类型" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.documentType"
                  :style="{ width: '100%' }"
                  placeholder="请选择文档类型"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in documenttypeOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="demandId" label="关联需求" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.demandId"
                  :style="{ width: '100%' }"
                  placeholder="请选择关联需求"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in selectOptions2"
                    :key="index"
                    :label="item.requirementName"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="documentDescription" label="文档描述" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.documentDescription"
                  type="textarea"
                  placeholder="请输入文档描述"
                  rows="2"
                  :maxLength="50"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
                <upload-file
                  title="附件"
                  :action="action"
                  :aggiorna="aggiorna1"
                  :refresh="filerefresh"
                  :objectId="editFormData.annex"
                  v-model="editFormData.annexFile"
                  class="dc-upload-file_ElUploadFile"
                ></upload-file>
            </el-col>
          </el-row>
        </el-form>
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
import uploadFile from '@/views/components/uploadFile'
/** 根据用户界面配置import组件 结束 */
import { getDocumentManageById, saveDocumentManage } from '@/api/documentManage/documentManage'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import { getProjectListByIds} from '@/api/projectManage/projectList'
import { listProjectListAll} from '@/api/projectManage/projectList'
import { getBymilestone} from '@/api/projectManage/milestoneManage'
import { getrequireByrid} from '@/api/requirement/requirementManage'
import { getscheduleByid} from '@/api/projectManage/scheduleManage'
import {listDictItemAll} from '@/api/sys/dictItem.js'

export default {
  extends: BaseUI,
  name: 'documentManage-form',
  components: {
    /** 根据用户界面配置组件 开始 */
    uploadFile,
    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      filerefresh: new Date(),
      aggiorna1: new Date(),
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '文档管理'
      },
      dialogTitle: '文档管理',
      // 选项变量
      // 所属日程选项
      scheduleIdOptions: [],
      // 文档类型选项
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      // 项目名称s
      selectOptions1: [],
      selectOptions2:[],
      documenttypeOptions:[],
      milestoneIdOptions:[],
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
    listDocumentTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{
        'columnName': 'dict_type_id',
        'queryType': '=',
        'value': '10010101'
      }])


     /* // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, "1740772218121937856")*/


      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {

          this.documenttypeOptions = responseData.data

        } else {
          this.showMessage(responseData)
        }
      })
    },

    upmilestoneid(){
      let id = this.editFormData.milestoneId
      getscheduleByid(id).then((responseData) => {
        if (responseData.code == 100) {
          this.scheduleIdOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },


    upprojiectid(){
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])
      let id = this.editFormData.projectId

      getrequireByrid(id).then((responseData) => {
        if (responseData.code == 100) {
          this.selectOptions2 = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })

      getBymilestone(id).then((responseData1) => {
        if (responseData1.code == 100) {
          this.milestoneIdOptions = responseData1.data;
          console.log(this.milestoneIdOptions)
        } else {
          this.showMessage(responseData1)
        }
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
    doSave() {
      this.setLoad()
      let formData = this.createFormData(this.editFormData)
      saveDocumentManage(formData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.filerefresh=new Date()
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

    createFormData(editFormData) {
      let formData = new FormData()
      let deletes = []

      for (let idx in editFormData.annexFile.uploads) {
        formData.append('annexUploads', editFormData.annexFile.uploads[idx].raw)
      }
      deletes = deletes.concat(editFormData.annexFile.deletes)

      formData.append('entity', JSON.stringify(editFormData))
      formData.append('deleteIds', JSON.stringify(deletes))
      return formData
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getDocumentManageById(id)
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
    listSelectOptions1() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // getProjectListByIds(search_List).then((responseData) => {
      listProjectListAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.selectOptions1 = responseData.data;
          console.log(this.selectOptions1)
        } else {
          this.showMessage(responseData)
        }
      })
    },


    initOptions(This) {
      // 初始化自定义类型选择框选项变量
      this.listSelectOptions1()
      this.listDocumentTypeOptions()

      if(this.action === 'edit'){
        console.log('edit')
        this.upprojiectid();
        this.upmilestoneid();
      }

    },
    initEditData(This) {
      return {
        projectId: '', // 项目ID
        documentName: '', // 文档名称
        milestoneId: null, // 所属里程碑
        scheduleId: null, // 所属日程
        documentType: null, // 文档类型
        demandId: null, // 关联需求
        documentDescription: '', // 文档描述
        annex: null,
        annexFile: {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        }
      }
    }
  },
  watch: {},
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
        this.dialogProps.visible = true
        this.filerefresh = new Date()
      })
      this.$on('openAddDialog',  function () {
        this.aggiorna1=new Date()
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
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
        this.editFormData.id = null //把id设置为空，添加一个新的

        this.editFormData.annex = null

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
