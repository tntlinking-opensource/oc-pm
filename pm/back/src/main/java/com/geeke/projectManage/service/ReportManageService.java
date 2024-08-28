package com.geeke.projectManage.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.projectManage.common.PageRequestExp;
import com.geeke.projectManage.dao.ReportManageDao;
import com.geeke.projectManage.entity.ReportManage;
import com.geeke.sys.entity.SysFile;
import com.geeke.sys.service.SysFileService;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.IdGen;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 周报管理Service
 * @author
 * @version
 */

@Service("reportManageService")
@Transactional(readOnly = true)
public class ReportManageService extends CrudService<ReportManageDao, ReportManage> {

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private ReportManageDao reportManageDao;

    @Autowired
    private ProjectListService projectListService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ReportManage save(ReportManage reportManage, MultipartFile[] annexUploads, String[] deleteIds)
        throws java.io.IOException {
        if (StringUtils.isEmpty(reportManage.getAnnex())) {
            reportManage.setAnnex(IdGen.uuid());
        }
        // 保存附件
        sysFileService.changeAndSaveSysFileList(annexUploads, reportManage.getAnnex());

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }
        JSONObject userObj = SessionUtils.getUserJson();
        Long userId = Long.valueOf(userObj.getString("id"));
        reportManage.setReportUser(userId);
        ReportManage reportManageTemp = super.save(reportManage);

        return reportManageTemp;
    }

    public List<ReportManage> getListByProjectIdAndDate(String projectId, String reportDate) {
        return reportManageDao.getListByProjectIdAndDate(projectId, reportDate);
    }

    public Page<ReportManage> list(PageRequestExp<JSONObject> pre) {
        JSONObject userInfo = SessionUtils.getUserJson();
        String userId = userInfo.getString("id");
        // 判断是否为admin角色
        boolean isAdmin = projectListService.checkIsAdmin(userId);
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("type", isAdmin ? "admin" : "");
        pre.setExtra(params);
        return listPage(pre);
    }

    public ReportManage getById(String id) {
        ReportManage entity = get(id);
        String annex = entity.getAnnex();
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("object_id", "=", annex));
        List<SysFile> files = sysFileService.listAll(params, null);
        entity.setAnnexFiles(files);
        return entity;
    }
}
