package com.geeke.risk.service;

import cn.hutool.core.collection.CollectionUtil;
import com.geeke.admin.entity.Role;
import com.geeke.admin.entity.User;
import com.geeke.admin.entity.UserRole;
import com.geeke.admin.service.RoleService;
import com.geeke.admin.service.UserRoleService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.DataEntity;
import com.geeke.common.sequence.dao.SequenceDao;
import com.geeke.common.sequence.entity.Sequence;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.common.service.CrudService;
import com.geeke.projectManage.entity.MilestoneManage;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.projectManage.entity.ScheduleManage;
import com.geeke.projectManage.service.MilestoneManageService;
import com.geeke.projectManage.service.ProjectListService;
import com.geeke.projectManage.service.ScheduleManageService;
import com.geeke.risk.dao.RiskManageDao;
import com.geeke.risk.entity.RiskManage;
import com.geeke.risk.enums.RiskImpactEnum;
import com.geeke.risk.enums.RiskStatusEnum;
import com.geeke.risk.enums.RiskTypeEnum;
import com.geeke.sys.dao.CodeRuleDao;
import com.geeke.sys.dao.CompanyCodeRuleDao;
import com.geeke.sys.service.SysFileService;
import com.geeke.team.entity.Project;
import com.geeke.utils.FreeMarkers;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 风险管理Service
 * @author
 * @version
 */

@Service("riskManageService")
@Transactional(readOnly = true)
public class RiskManageService extends CrudService<RiskManageDao, RiskManage> {

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private SequenceDao sequenceDao;

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private ProjectListService projectListService;

    @Autowired
    private CompanyCodeRuleDao companyCodeRuleDao;

    @Autowired
    CodeRuleDao codeRuleDao;

    @Autowired
    private MilestoneManageService milestoneManageService;

    @Autowired
    private ScheduleManageService scheduleManageService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Page<RiskManage> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        // admin查看全部审批通过数据，否则展示当前登录人是项目经理的数据
        SearchParams searchParams = new SearchParams();
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(new Parameter("user_id", "=", SessionUtils.getUser().getId()));
        searchParams.setParams(parameterList);
        List<UserRole> userRoleList = userRoleService.listAll(searchParams.getParams(), null);
        List<Role> roles = userRoleList.stream().map(UserRole::getRole).filter(i->i.getId() != null).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(roles)) {
            boolean flag = false;
            for(Role entity : roles) {
                Role role = roleService.get(entity.getId());
                if("admin".equals(role.getCode())) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                parameters.add(new Parameter("project.project_manager", "=", SessionUtils.getUser().getId()));
            }
        }
        Page<RiskManage> requirementManagePage = super.listPage(parameters, offset, limit, orderby);
        List<RiskManage> pageRows = requirementManagePage.getRows();
        if (CollectionUtil.isNotEmpty(pageRows)){
            for (RiskManage pageRow : pageRows) {
                List<Parameter> params = Lists.newArrayList();
                params.add(new Parameter("object_id", "=", pageRow.getId()));
                pageRow.setAttachmentFile(sysFileService.listAll(params,null));
            }
        }
        return requirementManagePage;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public RiskManage save(RiskManage entity, String[] deleteIds, MultipartFile[] attachments) throws IOException {

        RiskManage riskManage = save(entity);

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }
        // 保存附件
        if (attachments!=null && attachments.length>0){
            sysFileService.changeAndSaveSysFileList(attachments,riskManage.getId());
        }
        return riskManage;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> getRiskCode(String projectId) {
        Map<String, Object> map = new HashMap<>();
        ProjectList projectList = projectListService.get(projectId);
        String riskCode = this.generate(SessionUtils.getUser().getCompanyId(), "risk_code", projectId);
        if(projectList != null) {
            map.put("projectCode", projectList.getProjectCode());
            riskCode = projectList.getProjectCode() + riskCode;
            map.put("riskCode", riskCode);
        }
        return map;
    }

    public String generate(String companyId, String ruleCode, String prefix) {
        String ruleDef = null;
        // 公司id不为空，使用获取公司的编号规则表达式
        if(org.apache.commons.lang3.StringUtils.isNotBlank(companyId)) {
            companyCodeRuleDao.getRuleDefByCode(companyId, ruleCode);
        }
        // 公司Id为空，或公司编号规则不存在，获取系统统一的规则表达式
        if(ruleDef == null) {
            ruleDef = codeRuleDao.getRuleDefByCode(ruleCode);
        }

        if(ruleDef == null) {
            return null;
        }
        return doGenerate(companyId, ruleCode, ruleDef, prefix);
    }

    private String doGenerate(String companyId, String ruleCode, String templateString, String prefix) {
        // 公司Id为空时，使用统一的序号（companyId为0）
        String theCompanyId = org.apache.commons.lang3.StringUtils.isBlank(companyId) ? "0" : companyId;
        Map<String, Object> model = Maps.newHashMap();
        model.put("biz", prefix);
        model.put("seq", 0);
        Sequence sequence = sequenceDao.getByPrefix(theCompanyId, ruleCode, prefix);
        if(sequence == null) {
            sequence = insertSeq(theCompanyId, ruleCode, prefix);
        }

        model.put("seq", sequence.getVal());
        String seqNo = FreeMarkers.renderString(templateString, model);

        sequenceDao.nextSeqById(sequence);

        return seqNo;
    }

    private synchronized Sequence insertSeq(String companyId, String ruleCode, String prefix) {
        Sequence sequence = sequenceDao.getByPrefix(companyId, ruleCode, prefix);
        if(sequence == null) {
            sequence = new Sequence();
            sequence.setCompanyId(companyId);
            sequence.setCode(ruleCode);
            sequence.setPrefix(prefix);
            sequence.preInsert();
            sequenceDao.insert(sequence);

            sequence = sequenceDao.getByPrefix(companyId, ruleCode, prefix);	// 增加数据库锁
        }
        return sequence;
    }

    public List<RiskManage> entityListToDtoList(List<RiskManage> riskManages) {
        ArrayList<RiskManage> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(riskManages)) {
            return result;
        }
        for(RiskManage manage : riskManages) {
            // 组装里程碑
            // 通过id查询里程碑名称
            if(StringUtils.isNotBlank(manage.getMilestoneId())) {
                MilestoneManage entity = milestoneManageService.get(manage.getMilestoneId());
                manage.setMilestoneName(entity.getMilestoneName());
            }

            // 组装日程
            // 通过id查询日程名称
            if(StringUtils.isNotBlank(manage.getScheduleId())) {
                ScheduleManage entity = scheduleManageService.get(manage.getScheduleId());
                manage.setScheduleName(entity.getScheduleName());
            }

            // 组装风险类型
            if(StringUtils.isNotBlank(manage.getRiskType())) {
                if(RiskTypeEnum.JS_RISK.getValue().equals(manage.getRiskType())){
                    manage.setRiskTypeName(RiskTypeEnum.JS_RISK.getDesc());
                } else if(RiskTypeEnum.SC_RISK.getValue().equals(manage.getRiskType())){
                    manage.setRiskTypeName(RiskTypeEnum.SC_RISK.getDesc());
                } else {
                    manage.setRiskTypeName(RiskTypeEnum.HJ_RISK.getDesc());
                }
            }

            // 组装风险概率
            if(StringUtils.isNotBlank(manage.getRiskImpact())) {
                if(RiskImpactEnum.HIGH.getValue().equals(manage.getRiskImpact())){
                    manage.setRiskImpactName(RiskImpactEnum.HIGH.getDesc());
                } else if(RiskImpactEnum.MIDDLE.getValue().equals(manage.getRiskImpact())){
                    manage.setRiskImpactName(RiskImpactEnum.MIDDLE.getDesc());
                } else {
                    manage.setRiskImpactName(RiskImpactEnum.LOW.getDesc());
                }
            }

            // 组装风险状态
            if(StringUtils.isNotBlank(manage.getRiskStatus())) {
                if(RiskStatusEnum.SAVED.getValue().equals(manage.getRiskStatus())){
                    manage.setRiskStatusName(RiskStatusEnum.SAVED.getDesc());
                } else if(RiskStatusEnum.HAPPEND.getValue().equals(manage.getRiskStatus())){
                    manage.setRiskStatusName(RiskStatusEnum.HAPPEND.getDesc());
                } else {
                    manage.setRiskStatusName(RiskStatusEnum.SOLVED.getDesc());
                }
            }
        }
        return riskManages;
    }
}
