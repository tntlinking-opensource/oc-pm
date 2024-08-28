package com.geeke.micro.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.micro.dao.MicroAppDao;
import com.geeke.micro.entity.MicroApp;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.IdGen;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 应用管理Service
 * @author
 * @version
 */

@Service("microAppService")
@Transactional(readOnly = true)
public class MicroAppService extends CrudService<MicroAppDao, MicroApp> {

    @Autowired
    private SysFileService sysFileService;

    @Transactional(readOnly = false)
    public MicroApp save(MicroApp microApp, MultipartFile[] iconIdUploads, String[] deleteIds) throws java.io.IOException {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // appName不能重复

        colMaps.clear();

        colMaps.put("app_key", "appKey");

        colMaps.put("del_flag", "delFlag");

        if (exists(dao, microApp, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "appName已被使用，请重新输入。"));
        }

        if (StringUtils.isEmpty(microApp.getIconId())) {
            microApp.setIconId(IdGen.uuid());
        }
        // 保存附件
        sysFileService.changeAndSaveSysFileList(iconIdUploads, microApp.getIconId());

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }

        MicroApp microAppTemp = super.save(microApp);

        return microAppTemp;
    }
}
