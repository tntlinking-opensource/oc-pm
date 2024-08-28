package com.geeke.databus.service;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.BaseEntity;
import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.AppMasterDataTypeDao;
import com.geeke.databus.dao.AppStandardFieldDao;
import com.geeke.databus.entity.AppMasterDataType;
import com.geeke.databus.entity.AppStandardField;
import com.geeke.databus.entity.StandardField;
import com.geeke.databus.response.AppMasterDataTypeResponse;
import com.geeke.micro.service.MicroVersionService;
import com.geeke.sys.entity.Action;
import com.geeke.utils.SessionUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 应用主数据类型配置Service
 * @author
 * @version
 */

@Service("appMasterDataTypeService")
@Transactional(readOnly = true)
public class AppMasterDataTypeService extends CrudService<AppMasterDataTypeDao, AppMasterDataType> {

    @Autowired
    private AppStandardFieldDao appStandardFieldDao;

    @Autowired
    private AppStandardFieldService appStandardFieldService;

    @Autowired
    private MicroVersionService microVersionService;

    @Override
    public AppMasterDataType get(String id) {
        AppMasterDataType appMasterDataType = super.get(id);
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("app_id", "=", appMasterDataType.getAppId()));
        params.add(new Parameter("master_data_type_id", "=", appMasterDataType.getMasterDataType().getId()));
        List<AppStandardField> appStandardFields = appStandardFieldService.listAll(params, "");
        ArrayList<StandardField> objects = new ArrayList<>();
        for (AppStandardField appStandardField : appStandardFields) {
            StandardField standardField = new StandardField();
            standardField.setId(appStandardField.getStandardFieldId());
            objects.add(standardField);
        }
        appMasterDataType.getMasterDataType().setStandardFieldList(objects);
        return appMasterDataType;
    }
    public Page<AppMasterDataTypeResponse> listEnhancementPage(SearchParams searchParams) {
        List<Parameter> params1 = searchParams.getParams();
        String id = SessionUtils.getUser().getId();
        String loginName = SessionUtils.getUser().getLoginName();
        if (!loginName.toLowerCase().equals("system")&&!loginName.toLowerCase().equals("super")){
            params1.add(new Parameter("ma.developer_id","=",id));
        }
        Page<AppMasterDataType> result = listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        if (result.getTotal()==0){
            List<AppMasterDataTypeResponse>  arrayList = new ArrayList<>();
            return new Page<>(result.getTotal(), arrayList);
        }
        List<String> appVersions = result.getRows().stream()
            .map(AppMasterDataType::getAppId)
            .distinct()
            .collect(Collectors.toList());

        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("id", "in", appVersions));

        Map<String,String> appIdMap = microVersionService.listAll(params,"")
            .stream()
            .collect(Collectors.toMap(BaseEntity::getId,(x) -> x.getApp().getFullName()));

        List<AppMasterDataTypeResponse> responseList = result.getRows()
            .stream()
            .map(AppMasterDataTypeResponse::appMasterDataTypeConvert)
            .peek(response -> response.setAppName(appIdMap.get(response.getAppId())))
            .collect(Collectors.toList());

        return new Page<>(result.getTotal(), responseList);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public AppMasterDataType save(AppMasterDataType appMasterDataType) {
        List<StandardField> standardFieldList = appMasterDataType.getMasterDataType().getStandardFieldList();
        appStandardFieldService.deleteBymasterDataTypeIdAndappId(appMasterDataType.getMasterDataType().getId(),appMasterDataType.getAppId());
        for (StandardField standardField : standardFieldList) {
            AppStandardField appStandardField = new AppStandardField();
            appStandardField.setAppId(appMasterDataType.getAppId());
            appStandardField.setMasterDataTypeId(appMasterDataType.getMasterDataType().getId());
            appStandardField.setStandardFieldId(standardField.getId());
            appStandardFieldService.save(appStandardField);
        }

        return super.save(appMasterDataType);
    }

    /**
     * 删除
     * @param appMasterDataType
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(AppMasterDataType appMasterDataType) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     应用主数据字段表 */
        params = Lists.newArrayList();

        params.add(new Parameter("standard_field_id", "=", appMasterDataType.getId()));
        pageRequest = new PageRequest(params);
        appStandardFieldService.deleteBymasterDataTypeIdAndappId(appMasterDataType.getMasterDataType().getId(),appMasterDataType.getAppId());
        return super.delete(appMasterDataType);
    }

    /**
     * 批量删除
     * @param appMasterDataTypeList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<AppMasterDataType> appMasterDataTypeList) {
        for (AppMasterDataType appMasterDataType : appMasterDataTypeList) {
            delete(appMasterDataType);
        }

        return appMasterDataTypeList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, AppMasterDataType entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        return action;
    }

    /* 保存子表数据     应用主数据字段表 */
    private void saveAppStandardFieldList(AppMasterDataType appMasterDataType) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("standard_field_id", "=", appMasterDataType.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<AppStandardField> list_AppStandardField = appStandardFieldDao.listAll(pageRequest);

        List<AppStandardField> deletes = Lists.newArrayList(); // 删除列表
        List<AppStandardField> inserts = Lists.newArrayList(); // 添加列表
        List<AppStandardField> updates = Lists.newArrayList(); // 更新列表
        for (AppStandardField appStandardFieldSaved : list_AppStandardField) {
            boolean found = false;
            if (!found) {
                deletes.add(appStandardFieldSaved);
            }
        }
        if (deletes.size() > 0) {
            appStandardFieldService.bulkDelete(deletes);
        }
        if (updates.size() > 0) {
            appStandardFieldService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            appStandardFieldService.bulkInsert(inserts);
        }
    }
}
