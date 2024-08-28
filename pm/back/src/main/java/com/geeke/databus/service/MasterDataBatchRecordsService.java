package com.geeke.databus.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.MasterDataBatchRecordsDao;
import com.geeke.databus.dao.MasterDataJsonDao;
import com.geeke.databus.dao.MasterDataRecordsDao;
import com.geeke.databus.entity.MasterDataBatchRecords;
import com.geeke.databus.entity.MasterDataJson;
import com.geeke.databus.entity.MasterDataRecords;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.Reflections;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 主数据批次记录Service
 * @author
 * @version
 */

@Service("masterDataBatchRecordsService")
@Transactional(readOnly = true)
public class MasterDataBatchRecordsService extends CrudService<MasterDataBatchRecordsDao, MasterDataBatchRecords> {

    @Autowired
    private MasterDataJsonDao masterDataJsonDao;

    @Autowired
    private MasterDataJsonService masterDataJsonService;

    @Autowired
    private MasterDataRecordsDao masterDataRecordsDao;

    @Autowired
    private MasterDataRecordsService masterDataRecordsService;

    @Override
    public MasterDataBatchRecords get(String id) {
        MasterDataBatchRecords masterDataBatchRecords = super.get(id);

        List<Parameter> params = null;
        PageRequest pageRequest;

        return masterDataBatchRecords;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public MasterDataBatchRecords save(MasterDataBatchRecords masterDataBatchRecords) {
        MasterDataBatchRecords masterDataBatchRecordsTemp = super.save(masterDataBatchRecords);

        /* 保存子表数据     数据json表 */
        saveMasterDataJsonList(masterDataBatchRecordsTemp);

        /* 保存子表数据     主数据记录 */
        saveMasterDataRecordsList(masterDataBatchRecordsTemp);

        return masterDataBatchRecordsTemp;
    }

    /**
     * 删除
     * @param masterDataBatchRecords
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(MasterDataBatchRecords masterDataBatchRecords) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     数据json表 */
        params = Lists.newArrayList();

        params.add(new Parameter("master_data_batch_records_id", "=", masterDataBatchRecords.getId()));
        pageRequest = new PageRequest(params);
        masterDataBatchRecords.setMasterDataJsonList(masterDataJsonDao.listAll(pageRequest));

        if (
            masterDataBatchRecords.getMasterDataJsonList() != null && masterDataBatchRecords.getMasterDataJsonList().size() > 0
        ) {
            masterDataJsonService.bulkDelete(masterDataBatchRecords.getMasterDataJsonList());
        }

        /* 处理子表     主数据记录 */
        params = Lists.newArrayList();

        params.add(new Parameter("master_data_batch_records_id", "=", masterDataBatchRecords.getId()));
        pageRequest = new PageRequest(params);
        masterDataBatchRecords.setMasterDataRecordsList(masterDataRecordsDao.listAll(pageRequest));

        if (
            masterDataBatchRecords.getMasterDataRecordsList() != null &&
            masterDataBatchRecords.getMasterDataRecordsList().size() > 0
        ) {
            masterDataRecordsService.bulkDelete(masterDataBatchRecords.getMasterDataRecordsList());
        }

        int rows = super.delete(masterDataBatchRecords);
        return rows;
    }

    /**
     * 批量删除
     * @param masterDataBatchRecordsList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<MasterDataBatchRecords> masterDataBatchRecordsList) {
        for (MasterDataBatchRecords masterDataBatchRecords : masterDataBatchRecordsList) {
            delete(masterDataBatchRecords);
        }

        return masterDataBatchRecordsList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, MasterDataBatchRecords entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (MasterDataJson child : entity.getMasterDataJsonList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (MasterDataRecords child : entity.getMasterDataRecordsList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     数据json表 */
    private void saveMasterDataJsonList(MasterDataBatchRecords masterDataBatchRecords) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("master_data_batch_records_id", "=", masterDataBatchRecords.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<MasterDataJson> list_MasterDataJson = masterDataJsonDao.listAll(pageRequest);

        List<MasterDataJson> deletes = Lists.newArrayList(); // 删除列表
        List<MasterDataJson> inserts = Lists.newArrayList(); // 添加列表
        List<MasterDataJson> updates = Lists.newArrayList(); // 更新列表
        for (MasterDataJson masterDataJsonSaved : list_MasterDataJson) {
            boolean found = false;
            for (MasterDataJson masterDataJson : masterDataBatchRecords.getMasterDataJsonList()) {
                if (masterDataJsonSaved.getId().equals(masterDataJson.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(masterDataJsonSaved);
            }
        }
        if (deletes.size() > 0) {
            masterDataJsonService.bulkDelete(deletes);
        }
        for (MasterDataJson masterDataJson : masterDataBatchRecords.getMasterDataJsonList()) {
            if (StringUtils.isBlank(masterDataJson.getId())) {
                masterDataJson.setMasterDataBatchRecords(masterDataBatchRecords);

                inserts.add(masterDataJson);
            } else {
                updates.add(masterDataJson);
            }
        }
        if (updates.size() > 0) {
            masterDataJsonService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            masterDataJsonService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     主数据记录 */
    private void saveMasterDataRecordsList(MasterDataBatchRecords masterDataBatchRecords) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("master_data_batch_records_id", "=", masterDataBatchRecords.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<MasterDataRecords> list_MasterDataRecords = masterDataRecordsDao.listAll(pageRequest);

        List<MasterDataRecords> deletes = Lists.newArrayList(); // 删除列表
        List<MasterDataRecords> inserts = Lists.newArrayList(); // 添加列表
        List<MasterDataRecords> updates = Lists.newArrayList(); // 更新列表
        for (MasterDataRecords masterDataRecordsSaved : list_MasterDataRecords) {
            boolean found = false;
            for (MasterDataRecords masterDataRecords : masterDataBatchRecords.getMasterDataRecordsList()) {
                if (masterDataRecordsSaved.getId().equals(masterDataRecords.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(masterDataRecordsSaved);
            }
        }
        if (deletes.size() > 0) {
            masterDataRecordsService.bulkDelete(deletes);
        }
        for (MasterDataRecords masterDataRecords : masterDataBatchRecords.getMasterDataRecordsList()) {
            if (StringUtils.isBlank(masterDataRecords.getId())) {
                masterDataRecords.setMasterDataBatchRecords(masterDataBatchRecords);

                inserts.add(masterDataRecords);
            } else {
                updates.add(masterDataRecords);
            }
        }
        if (updates.size() > 0) {
            masterDataRecordsService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            masterDataRecordsService.bulkInsert(inserts);
        }
    }
}
