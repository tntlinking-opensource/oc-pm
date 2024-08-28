<template>
  <el-row class="dc-container" v-loading="loading">
    <el-form
      ref="editForm"
      :model="editFormData"
      label-width="100px"
      :disabled="action == 'view' || disabled"
      class="dc-el-form_ElEditForm"
    >
      <el-row>
        <div class="dc-divide">
          <div>基本信息</div>
        </div>
        <el-col :span="8">
          <el-form-item
            prop="projectName"
            label="项目名称"
            :rules="[{ required: true, message: '项目名称不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.projectName"
              :maxLength="50"
              placeholder="请输入项目名称"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="projectShortName"
            label="项目简称"
            :rules="[{ required: true, message: '项目简称不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.projectShortName"
              :maxLength="30"
              placeholder="请输入项目简称"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="projectCode"
            label="项目编号"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              disabled
              v-model="editFormData.projectCode"
              :maxLength="14"
              placeholder="项目编号"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="custom.id"
            label="客户名称"
            :rules="[{ required: true, message: '客户名称不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.custom"
              :style="{ width: '100%' }"
              placeholder="请选择客户"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in customOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="department.id"
            label="所属部门"
            :rules="[{ required: true, message: '所属部门不能为空', trigger: 'change' }]"
            class="dc-el-form-item_CascaderInput"
          >
            <el-cascader
              ref="cascader114"
              :options="departmentOptions"
              v-model="editFormData.department.id"
              :style="{ width: '100%' }"
              placeholder="请选择所属部门"
              :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
              clearable
              filterable
              separator="/"
              class="dc-el-cascader_CascaderInput"
            ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="projectType.id"
            label="项目类型"
            :rules="[{ required: true, message: '项目类型不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.projectType"
              :style="{ width: '100%' }"
              placeholder="请选择项目类型"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in projectTypeOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="projectManager.id"
            label="项目经理"
            :rules="[{ required: true, message: '项目经理不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.projectManager"
              :style="{ width: '100%' }"
              placeholder="请选择项目经理"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in projectManagerOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="planBeginDate"
            label="计划日期"
            :rules="[{ required: true, message: '计划日期不能为空', trigger: 'change' }]"
            class="dc-el-form-item_PickerTimeDateRange"
          >
            <el-date-picker
              @change="planDateChange"
              :value="[editFormData.planBeginDate, editFormData.planEndDate]"
              v-on:input="
                (val) => {
                  editFormData.planBeginDate = val ? val[0] : ''
                  editFormData.planEndDate = val ? val[1] : ''
                }
              "
              :style="{ width: '100%' }"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDateRange"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <div class="dc-divide">
          <div>启动信息</div>
        </div>
        <el-col :span="8">
          <el-form-item
            prop="applyBy"
            label="申请人"
            :rules="[{ required: true, message: '申请人不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              disabled
              v-model="editFormData.applyBy"
              :maxLength="64"
              placeholder="请输入申请人"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="applyDepartment.name"
            label="申请部门"
            :rules="[{ required: true, message: '申请部门不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.applyDepartment.name"
              disabled
              :maxLength="21"
              placeholder="请输入申请部门"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="applyDate"
            label="申请日期"
            :rules="[{ required: true, message: '申请日期不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.applyDate"
              disabled
              :maxLength="-1"
              placeholder="请输入申请日期"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="projectBusiOpp.id"
            label="立项商机"
            :rules="[{ required: true, message: '立项商机不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.projectBusiOpp"
              :style="{ width: '100%' }"
              placeholder="请选择立项商机"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in projectBusiOppOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="商机编号"
            :rules="[{ required: true, message: '商机编号不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.projectBusiOpp.value"
              :maxLength="21"
              placeholder="请输入商机编号"
              disabled
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <div class="dc-divide">
          <div>项目干系人</div>
        </div>
        <el-col :span="8">
          <el-form-item
            prop="projectCoreMember"
            label="项目核心成员"
            :rules="[{ required: true, message: '项目核心成员不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.projectCoreMember"
              :maxLength="10"
              placeholder="请输入项目核心成员"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            prop="marketManager"
            label="市场负责人"
            :rules="[{ required: true, message: '市场负责人不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.marketManager"
              :maxLength="10"
              placeholder="请输入市场负责人"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="customName" label="客户姓名" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.customName"
              :maxLength="10"
              placeholder="请输入客户姓名"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="customRole" label="客户角色" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.customRole"
              :maxLength="10"
              placeholder="请输入客户角色"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <div class="dc-divide">
          <div>项目人月投入计划</div>
        </div>
        <el-col :span="8">
          <el-form-item prop="projectBudget" label="项目总预算" class="dc-el-form-item_NumberInput">
            <number-input
              v-model="editFormData.projectBudget"
              :min="0"
              :max="999999999999.99"
              :allowNegative="false"
              :precision="2"
              placeholder="请输入项目总预算"
              clearable
              class="dc-number-input_NumberInput"
            ></number-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="projectBudgetPersonMonth" label="项目计划投入总人月" class="dc-el-form-item_NumberInput">
            <number-input
              v-model="editFormData.projectBudgetPersonMonth"
              :min="0"
              :max="999999999999"
              :allowNegative="false"
              :precision="0"
              placeholder="请输入项目计划投入总人月"
              clearable
              class="dc-number-input_NumberInput"
            ></number-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="planLaborCost" label="计划投入人力成本" class="dc-el-form-item_NumberInput">
            <number-input
              v-model="editFormData.planLaborCost"
              :min="0"
              :max="999999999999.99"
              :allowNegative="false"
              :precision="2"
              placeholder="请输入计划投入人力成本"
              clearable
              class="dc-number-input_NumberInput"
            ></number-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="单月计划人数" class="dc-el-form-item_NumberInput">
            <div v-for="(item, index) in editFormData.planPersonMonthlyArr" :key="index" style="display: inline-flex; flex-direction: column; width: 120px;">
              <span>{{ item.month }}</span>
              <number-input
                style="width: 100px"
                v-model="editFormData.planPersonMonthlyArr[index].value"
                :min="0"
                :max="999999999999.99"
                :allowNegative="false"
                :precision="0"
                placeholder="请输入"
                clearable
                class="dc-number-input_NumberInput"
              ></number-input>
            </div>
          </el-form-item>
        </el-col>
        <div class="dc-divide">
          <div>其他信息</div>
        </div>
        <el-col :span="24">
          <el-form-item prop="projectDesc" label="项目描述" class="dc-el-form-item_MutilpleInput">
            <el-input
              v-model="editFormData.projectDesc"
              type="textarea"
              placeholder="请输入项目描述"
              rows="2"
              :maxLength="300"
              class="dc-el-input_MutilpleInput"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-row>
</template>
<script>
import BizForm from '@/views/wf/common/bizForm'
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { listDictItemAll } from '@/api/sys/dictItem.js'
import { treeDepartment } from '@/api/org/department.js'
/** 根据用户界面配置import组件 结束 */
import {
  getProjectListById,
  createProjectList,
  reapplyProjectList,
  getProjectListByTaskId,
  agreeProjectList,
  forwardProjectList,
  commissionProjectList,
  proposeProjectList,
  backProjectList,
  rejectProjectList,
  terminateProjectList,
  reverseProjectList,
  listBackActivity,
  saveDraftProjectList
} from '@/api/projectManage/projectList'
import OperationIcon from '@/components/OperationIcon'
import {listUserAll} from "@/api/admin/user";
export default {
  extends: BizForm,
  name: 'projectList-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      dialogProps: {
        visible: false,
        title: ''
      },

      // 选项变量
      // 客户名称选项
      customOptions: [],
      // 所属部门选项
      departmentOptions: [],
      // 项目类型选项
      projectTypeOptions: [],
      // 项目经理选项
      projectManagerOptions: [],
      // 立项商机选项
      projectBusiOppOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      planMonths: []
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  computed: {},
  methods: {
    handlePlanPersonMonthly() {
      this.editFormData.planPersonMonthly = JSON.stringify(this.editFormData.planPersonMonthlyArr)
    },
    initPlanPersonMonthlyArr() {
      if (!this.editFormData.planPersonMonthly) {
        return
      }
      this.editFormData.planPersonMonthlyArr = JSON.parse(this.editFormData.planPersonMonthly)
    },
    /**
     * 获取两个日期之间的月份数据
     * @author Yogaa
     * @date 15:26 2023/12/10
     */
    getMonthsBetweenDates(startDate, endDate) {
      startDate = new Date(startDate)
      endDate = new Date(endDate)
      const months = [];
      let currentDate = startDate;
      while (currentDate <= endDate) {
        const year = currentDate.getFullYear().toString().padStart(4, '0');
        const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
        months.push(`${year}-${month}`);
        currentDate = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1);
      }
      return months;
    },
    /**
     * 计划日期改变事件
     * @author Yogaa
     * @date 15:26 2023/12/10
     */
    planDateChange(data) {
      let months = this.getMonthsBetweenDates(data[0], data[1])
      this.editFormData.planPersonMonthlyArr = months.map(month => {
        return {month, value: null}
      })
    },
    loadModelData(taskId) {
      this.setLoad()
      getProjectListByTaskId(taskId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.editFormData = {
              ...this.initEditData(),
              ...responseData.data
            }
            this.initPlanPersonMonthlyArr()
            this.$emit('after-load-data', this.editFormData) // 表单数据对象传给父节点
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 保存草稿，files附件列表, draftModel 草稿对象
    doSaveDraft(draftModel, files) {
      if (!draftModel) {
        // 不是从草稿打开创建，保存时更新原草稿
        draftModel = {
          procdefId: this.procDef.id,
          procdefName: this.procDef.name,
          name: this.procDef.name + '-' + this.editFormData.name
        }
      }
      this.setLoad()
      this.handlePlanPersonMonthly()
      let formData = this.createWfData(this.editFormData, files)
      formData.append('strDraft', JSON.stringify(draftModel))
      saveDraftProjectList(formData)
        .then((responseData) => {
          this.handleResponse(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 创建一个新流程 process流程 actNodes是提交的用户任务节点及其审批用户
    doCreate(files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.handlePlanPersonMonthly()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          let formData = this.createWfData(this.editFormData, files)
          createProjectList(this.procDef.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 重新发起申请 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doReApply(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.handlePlanPersonMonthly()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          reapplyProjectList(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 同意 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doAgree(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          agreeProjectList(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 转派任务
    doForward(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          forwardProjectList(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 委派任务
    doCommission(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          commissionProjectList(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 拟办（办理委派过来的任务）
    doPropose(comment, files) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          proposeProjectList(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 拒绝
    doReject(comment, files) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          rejectProjectList(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 退回 comment 意见, actNodes退回节点和用户信息
    doBack(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          backProjectList(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 终止流程 comment 意见, actNodes退回节点和用户信息
    doTerminate(comment, files) {
      this.setLoad()
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      terminateProjectList(this.task.id, formData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.$emit('save-finished')
          }
          this.showMessage(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 撤回任务
    doReverse(task) {
      this.setLoad()
      reverseProjectList(task.id)
        .then((responseData) => {
          this.handleResponse(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    doListActivity() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          listBackActivity(this.task.id)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },

    listCustomOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{'columnName':'dict_type_id', 'queryType': '=', 'value': '1831069644912500817'}])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937856')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.customOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listDepartmentOptions() {
      let search_List = {
        params: [{
          columnName: 'company_id',
          queryType: '=',
          value: currentUser.company.id === '0' ? '' : currentUser.company.id
        }]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 部门org_department
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937911')

      treeDepartment(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listProjectTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{'columnName':'dict_type_id', 'queryType': '=', 'value': '1831069644912500819'}])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937856')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.projectTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listProjectManagerOptions() {
      let search_List = {
        params: [{
          columnName: 'company_id',
          queryType: '=',
          value: currentUser.company.id === '0' ? '' : currentUser.company.id
        }]
      }
      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.projectManagerOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listProjectBusiOppOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [{'columnName':'dict_type_id', 'queryType': '=', 'value': '1831069644912500823'}])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1740772218121937856')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.projectBusiOppOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listCustomOptions()

      this.listDepartmentOptions()

      this.listProjectTypeOptions()

      this.listProjectManagerOptions()

      this.listProjectBusiOppOptions()
    },
    initEditData(This) {
      // 初始化申请时间
      const date = new Date()
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const applyDate = `${year}-${month}-${day}`
      // return {
      //   projectName: '测试项目1', // 项目名称
      //   projectShortName: '测试项目1', // 项目简称
      //   projectCode: 'AA202312080001', // 项目编号
      //   custom: {
      //     id: null,
      //     name: null,
      //     dictTypeId: null,
      //     value: null
      //   },
      //   // 所属部门
      //   department: {
      //     id: null,
      //     name: null,
      //     parentId: null,
      //     ids: null,
      //     code: null,
      //     companyId: null
      //   },
      //   projectType: {
      //     id: null,
      //     name: null,
      //     dictTypeId: null,
      //     value: null
      //   },
      //   projectManager: {
      //     id: null,
      //     name: null,
      //     dictTypeId: null,
      //     value: null
      //   },
      //   planBeginDate: '2023-12-01', // 计划日期
      //   planEndDate: '2023-12-08', // 计划日期
      //   applyBy: currentUser.name, // 申请人
      //   applyDepartment: {
      //     id: currentUser.department.id,
      //     name: currentUser.department.name,
      //     parentId: null,
      //     ids: null,
      //     code: null,
      //     companyId: null
      //   },
      //   // applyDepartmentName: currentUser.department.name,
      //   // applyDepartmentId: currentUser.department.id, // 申请部门
      //   applyDate: applyDate, // 申请日期
      //   projectBusiOpp: {
      //     id: null,
      //     name: null,
      //     dictTypeId: null,
      //     value: null
      //   },
      //   projectCoreMember: '测试核心成员', // 项目核心成员
      //   marketManager: '测试市场负责人', // 市场负责人
      //   customName: '测试客户姓名', // 客户姓名
      //   customRole: '测试客户角色', // 客户角色
      //   projectBudget: 888888, // 项目总预算
      //   projectBudgetPersonMonth: 88, // 项目计划投入总人月
      //   planLaborCost: 188888, // 计划投入人力成本
      //   projectDesc: '测试项目描述' // 项目描述
      // }
      return {
        projectName: '', // 项目名称
        projectShortName: '', // 项目简称
        projectCode: '', // 项目编号
        custom: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        // 所属部门
        department: {
          id: null,
          name: null,
          parentId: null,
          ids: null,
          code: null,
          companyId: null
        },
        projectType: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        projectManager: {
          id: null,
          name: null
        },
        planBeginDate: '', // 计划日期
        planEndDate: '', // 计划日期
        applyBy: currentUser.name, // 申请人
        applyDepartment: {
          id: currentUser.department.id,
          name: currentUser.department.name,
          parentId: null,
          ids: null,
          code: null,
          companyId: null
        },
        applyDepartmentName: currentUser.department.name,
        applyDepartmentId: currentUser.department.id, // 申请部门
        applyDate: applyDate, // 申请日期
        projectBusiOpp: {
          id: null,
          name: null,
          dictTypeId: null,
          value: null
        },
        projectCoreMember: '', // 项目核心成员
        marketManager: '', // 市场负责人
        customName: '', // 客户姓名
        customRole: '', // 客户角色
        projectBudget: null, // 项目总预算
        projectBudgetPersonMonth: null, // 项目计划投入总人月
        planLaborCost: null, // 计划投入人力成本
        planPersonMonthlyArr: [],
        planPersonMonthly: '',
        projectDesc: '' // 项目描述
      }
    }
  },
  mounted: function () {
    console.log(1)
    this.$nextTick(() => {
      this.dialogProps.visible = true
      this.initOptions()
      this.tabIndex = '1'
      if (this.wfAction == 'create') {
        this.$refs['editForm'].resetFields()
        // 从草稿创建
        if (this.bizData) {
          this.editFormData = {
            ...this.initEditData(),
            ...this.bizData
          }
          this.initPlanPersonMonthlyArr()
        }
      } else if (this.wfAction == 'approve') {
        // 重新申请
        let reApply =
          this.task.assignee == this.task.owner &&
          this.task.assignee == this.task.extInfo.applicantId &&
          this.task.taskDefinitionKey == this.task.extInfo.appActId
        if (!reApply) {
          this.action = 'view'
        }
        this.loadModelData(this.task.id)
      } else if (this.wfAction == 'viewBiz') {
        this.action = 'view'
        this.editFormData = {
          ...this.initEditData(),
          ...this.bizData
        }
        this.initPlanPersonMonthlyArr()
      } else if (this.wfAction == 'viewTask') {
        this.action = 'view'
        this.loadModelData(this.task.id)
      } else {
        console.err('=============== 业务审批单中，实现的：' + this.wfAction)
      }
    })
  }
}
</script>
<style lang="scss" scoped>
.dc-divide {
  width: 100%;
  background-color: #ecf5ff;
  padding: 5px 10px;
  margin: 10px 0;
  border-radius: 4px;
  div {
    border-left: 4px #409eff solid;
    padding-left: 6px;
  }
}
</style>
