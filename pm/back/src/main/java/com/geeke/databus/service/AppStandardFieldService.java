package com.geeke.databus.service;

import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.AppStandardFieldDao;
import com.geeke.databus.entity.AppStandardField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用主数据类型配置Service
 * @author
 * @version
 */

@Service("appStandardFieldService")
@Transactional(readOnly = true)
public class AppStandardFieldService extends CrudService<AppStandardFieldDao, AppStandardField> {
    public int deleteBymasterDataTypeIdAndappId(String MasterDataTypeId,String AppId){
        return dao.deleteBymasterDataTypeIdAndappId(MasterDataTypeId,AppId);
    }
}
