package com.geeke.documentManage.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.Page;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.documentManage.dao.DocumentManageDao;
import com.geeke.documentManage.entity.DocumentManage;
import com.geeke.projectManage.common.PageRequestExp;
import com.geeke.projectManage.common.SearchParamsExp;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.sys.service.SysFileService;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.IdGen;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文档管理Service
 * @author
 * @version
 */

@Service("documentManageService")
@Transactional(readOnly = true)
public class DocumentManageService extends CrudService<DocumentManageDao, DocumentManage> {

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private DocumentManageDao documentManageDao;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public DocumentManage save(DocumentManage documentManage, MultipartFile[] annexUploads, String[] deleteIds)
        throws java.io.IOException {
        if (StringUtils.isEmpty(documentManage.getAnnex())) {
            documentManage.setAnnex(IdGen.uuid());
        }
        // 保存附件
        sysFileService.changeAndSaveSysFileList(annexUploads, documentManage.getAnnex());
        /*if(annexUploads.length > 0){
            deleteIds = new String[1];
            deleteIds[0] = documentManage.getAnnexId();
        }*/

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }

        DocumentManage documentManageTemp = super.save(documentManage);

        return documentManageTemp;
    }

    public Page<DocumentManage> listPage(SearchParamsExp<String> sp) {
        JSONObject userInfo = SessionUtils.getUserJson();
        // TODO 除admin角色外，只能查看项目经理为自己的项目
        String userId = userInfo.getString("id");
        if(checkrolebyid(userId) == 0){
            sp.setExtra(userId);
        }
        PageRequestExp<String> pr = sp.transferPageRequest();
        return listPage(pr);
    }

    public List<DocumentManage> listAll(SearchParamsExp<String> sp) {
        JSONObject userInfo = SessionUtils.getUserJson();
        // TODO 除admin角色外，只能查看项目经理为自己的项目
        String userId = userInfo.getString("id");
        if(checkrolebyid(userId) == 0){
            sp.setExtra(userId);
        }
        PageRequestExp<String> pr = sp.transferPageRequest();
        return listAll(pr);
    }

    public int checkrolebyid(String userid){
        return documentManageDao.checkrolebyid(userid);
    }
}
