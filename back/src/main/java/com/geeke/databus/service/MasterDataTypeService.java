package com.geeke.databus.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.MasterDataTypeDao;
import com.geeke.databus.dao.StandardFieldDao;
import com.geeke.databus.entity.MasterDataType;
import com.geeke.databus.entity.StandardField;
import com.geeke.micro.dao.MicroVersionDao;
import com.geeke.micro.entity.MicroVersion;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.Reflections;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 平台主数据Service
 * @author
 * @version
 */

@Service("masterDataTypeService")
@Transactional(readOnly = true)
public class MasterDataTypeService extends CrudService<MasterDataTypeDao, MasterDataType> {

    @Autowired
    private StandardFieldDao standardFieldDao;

    @Autowired
    private StandardFieldService standardFieldService;
    @Autowired
    private MicroVersionDao microVersionDao;

    @Override
    public MasterDataType get(String id) {
        MasterDataType masterDataType = super.get(id);

        List<Parameter> params = null;
        PageRequest pageRequest;

        /*获取子表列表   主标准字段表*/
        params = Lists.newArrayList();

        params.add(new Parameter("master_data_type_id", "=", masterDataType.getId()));
        pageRequest = new PageRequest(params);
        masterDataType.setStandardFieldList(standardFieldDao.listAll(pageRequest));

        return masterDataType;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public MasterDataType save(MasterDataType masterDataType) {
        MasterDataType masterDataTypeTemp = super.save(masterDataType);

        /* 保存子表数据     主标准字段表 */
        saveStandardFieldList(masterDataTypeTemp);

        return masterDataTypeTemp;
    }

    /**
     * 删除
     * @param masterDataType
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(MasterDataType masterDataType) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     主标准字段表 */
        params = Lists.newArrayList();

        params.add(new Parameter("master_data_type_id", "=", masterDataType.getId()));
        pageRequest = new PageRequest(params);
        masterDataType.setStandardFieldList(standardFieldDao.listAll(pageRequest));

        if (masterDataType.getStandardFieldList() != null && masterDataType.getStandardFieldList().size() > 0) {
            standardFieldService.bulkDelete(masterDataType.getStandardFieldList());
        }

        int rows = super.delete(masterDataType);
        return rows;
    }

    /**
     * 批量删除
     * @param masterDataTypeList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<MasterDataType> masterDataTypeList) {
        for (MasterDataType masterDataType : masterDataTypeList) {
            delete(masterDataType);
        }

        return masterDataTypeList.size();
    }
    public List<MicroVersion> listMicroVersionAll(List<Parameter> parameters, String orderby) {
        String id = SessionUtils.getUser().getId();
        String loginName = SessionUtils.getUser().getLoginName();
        if (!loginName.toLowerCase().equals("system")&&!loginName.toLowerCase().equals("super")){
            parameters.add(new Parameter("a.developer_id","=",id));
        }
        PageRequest pageRequest = new PageRequest(parameters, orderby);
        return microVersionDao.listAll(pageRequest);
    }
    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, MasterDataType entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (StandardField child : entity.getStandardFieldList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     主标准字段表 */
    private void saveStandardFieldList(MasterDataType masterDataType) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("master_data_type_id", "=", masterDataType.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<StandardField> list_StandardField = standardFieldDao.listAll(pageRequest);

        List<StandardField> deletes = Lists.newArrayList(); // 删除列表
        List<StandardField> inserts = Lists.newArrayList(); // 添加列表
        List<StandardField> updates = Lists.newArrayList(); // 更新列表
        for (StandardField standardFieldSaved : list_StandardField) {
            boolean found = false;
            for (StandardField standardField : masterDataType.getStandardFieldList()) {
                if (standardFieldSaved.getId().equals(standardField.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(standardFieldSaved);
            }
        }
        if (deletes.size() > 0) {
            standardFieldService.bulkDelete(deletes);
        }
        for (StandardField standardField : masterDataType.getStandardFieldList()) {
            if (StringUtils.isBlank(standardField.getId())) {
                standardField.setMasterDataTypeId(masterDataType.getId());

                inserts.add(standardField);
            } else {
                standardField.setMasterDataTypeId(masterDataType.getId());
                updates.add(standardField);
            }
        }
        if (updates.size() > 0) {
            standardFieldService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            standardFieldService.bulkInsert(inserts);
        }
    }
}
