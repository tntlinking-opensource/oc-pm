package com.geeke.databus.service;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.MasterDataPushInterfaceRecordsDao;
import com.geeke.databus.dao.MasterDataPushRecordsDao;
import com.geeke.databus.entity.MasterDataPushInterfaceRecords;
import com.geeke.databus.entity.MasterDataPushRecords;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.Reflections;
import com.geeke.utils.RemoteResultResolver;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 主数据批次分发记录Service
 * @author
 * @version
 */

@Service("masterDataPushRecordsService")
@Transactional(readOnly = true)
public class MasterDataPushRecordsService extends CrudService<MasterDataPushRecordsDao, MasterDataPushRecords> {

    @Autowired
    private MasterDataPushInterfaceRecordsDao masterDataPushInterfaceRecordsDao;

    @Autowired
    private MasterDataPushInterfaceRecordsService masterDataPushInterfaceRecordsService;

    @Autowired
    private DynamicRestTemplate restTemplate;

    @Override
    public Page<MasterDataPushRecords> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        Page<MasterDataPushRecords> result = super.listPage(parameters, offset, limit, orderby);

        //查看是否多次调用
        int flag = RemoteResultResolver.getRemoteFlag();
        //组装数据
        if (flag <= 1 && Objects.nonNull(result.getRows())) {
            //设置Header
            HttpHeaders headers = new HttpHeaders();
            headers.add(RemoteResultResolver.REMOTE_FLAG, String.valueOf(flag++));

            SearchParams microVersionParam = RemoteResultResolver.param(result.getRows(), "targetAppId", "id");
            JSONObject microVersionList = restTemplate.post(
                "cloud-geeke-devtool",
                "/micro/microVersion/listAll",
                JSONObject.class,
                microVersionParam,
                headers
            );
            Integer microVersionCode = Objects.requireNonNull(microVersionList.getInteger("code"));
            if (microVersionCode == 100) {
                List<Map<String, Object>> targetApp = microVersionList.getObject("data", ArrayList.class);
                RemoteResultResolver.resultResolver(result.getRows(), targetApp, "targetApp", "targetAppId");
            }
        }

        //查看是否多次调用
        int flag1 = RemoteResultResolver.getRemoteFlag();
        //组装数据
        if (flag1 <= 1 && Objects.nonNull(result.getRows())) {
            //设置Header
            HttpHeaders headers = new HttpHeaders();
            headers.add(RemoteResultResolver.REMOTE_FLAG, String.valueOf(flag1++));

            SearchParams microVersionParam = new SearchParams();
            JSONObject microVersionList = restTemplate.post(
                "cloud-geeke-organisation",
                "/ten/tenant/listAll",
                JSONObject.class,
                microVersionParam,
                headers
            );
            Integer microVersionCode = Objects.requireNonNull(microVersionList.getInteger("code"));
            if (microVersionCode == 100) {
                List<Map<String, Object>> targetApp = microVersionList.getObject("data", ArrayList.class);
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                for (Map<String, Object> hashMap : targetApp) {
                    objectObjectHashMap.put(hashMap.get("id"),hashMap.get("name"));
                }
                for (MasterDataPushRecords row : result.getRows()) {
                    String tenantId = row.getTenantId();
                    Object o = objectObjectHashMap.get(tenantId);
                    row.setTenantId(String.valueOf(o));
                }
            }
        }

        return result;
    }

    @Override
    public List<MasterDataPushRecords> listAll(List<Parameter> parameters, String orderby) {
        PageRequest pageRequest = new PageRequest(parameters, orderby);

        List<MasterDataPushRecords> result = dao.listAll(pageRequest);

        //查看是否多次调用
        int flag = RemoteResultResolver.getRemoteFlag();
        //组装数据
        if (flag <= 1 && Objects.nonNull(result)) {
            //设置Header
            HttpHeaders headers = new HttpHeaders();
            headers.add(RemoteResultResolver.REMOTE_FLAG, String.valueOf(flag++));

            SearchParams microVersionParam = RemoteResultResolver.param(result, "targetAppId", "id");
            JSONObject microVersionList = restTemplate.post(
                "cloud-geeke-devtool",
                "/micro/microVersion/listAll",
                JSONObject.class,
                microVersionParam,
                headers
            );
            Integer microVersionCode = Objects.requireNonNull(microVersionList.getInteger("code"));
            if (microVersionCode == 100) {
                List<Map<String, Object>> targetApp = microVersionList.getObject("data", ArrayList.class);
                RemoteResultResolver.resultResolver(result, targetApp, "targetApp", "targetAppId");
            }
        }

        return result;
    }

    @Override
    public MasterDataPushRecords get(String id) {
        MasterDataPushRecords masterDataPushRecords = super.get(id);

        Map<String, Object> targetAppMap = new HashMap<>();
        targetAppMap.put("id", masterDataPushRecords.getTargetAppId());
        masterDataPushRecords.setTargetApp(targetAppMap);

        List<Parameter> params = null;

        /*获取子表列表   推送记录*/
        params = Lists.newArrayList();

        params.add(new Parameter("master_data_push_records_id", "=", masterDataPushRecords.getId()));
        masterDataPushRecords.setMasterDataPushInterfaceRecordsList(masterDataPushInterfaceRecordsService.listAll(params, ""));

        return masterDataPushRecords;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public MasterDataPushRecords save(MasterDataPushRecords masterDataPushRecords) {
        if (StringUtils.isBlank(masterDataPushRecords.getId())){
            masterDataPushRecords.preInsert();
            dao.insert(masterDataPushRecords);
            this.saveAction(this.createAction(ActionConstants.ACTION_CREATED, masterDataPushRecords));
        }else{
            masterDataPushRecords.preUpdate();
            dao.update(masterDataPushRecords);
            this.saveAction(this.createAction(ActionConstants.ACTION_UPDATED, masterDataPushRecords));
        }

        /* 保存子表数据     推送记录 */
        saveMasterDataPushInterfaceRecordsList(masterDataPushRecords);

        return masterDataPushRecords;
    }

    /**
     * 删除
     * @param masterDataPushRecords
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(MasterDataPushRecords masterDataPushRecords) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     推送记录 */
        params = Lists.newArrayList();

        params.add(new Parameter("master_data_push_records_id", "=", masterDataPushRecords.getId()));
        pageRequest = new PageRequest(params);
        masterDataPushRecords.setMasterDataPushInterfaceRecordsList(masterDataPushInterfaceRecordsDao.listAll(pageRequest));

        if (
            masterDataPushRecords.getMasterDataPushInterfaceRecordsList() != null &&
            masterDataPushRecords.getMasterDataPushInterfaceRecordsList().size() > 0
        ) {
            masterDataPushInterfaceRecordsService.bulkDelete(masterDataPushRecords.getMasterDataPushInterfaceRecordsList());
        }

        int rows = super.delete(masterDataPushRecords);
        return rows;
    }

    /**
     * 批量删除
     * @param masterDataPushRecordsList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<MasterDataPushRecords> masterDataPushRecordsList) {
        for (MasterDataPushRecords masterDataPushRecords : masterDataPushRecordsList) {
            delete(masterDataPushRecords);
        }

        return masterDataPushRecordsList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, MasterDataPushRecords entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (MasterDataPushInterfaceRecords child : entity.getMasterDataPushInterfaceRecordsList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     推送记录 */
    private void saveMasterDataPushInterfaceRecordsList(MasterDataPushRecords masterDataPushRecords) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("master_data_push_records_id", "=", masterDataPushRecords.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<MasterDataPushInterfaceRecords> list_MasterDataPushInterfaceRecords = masterDataPushInterfaceRecordsDao.listAll(
            pageRequest
        );

        List<MasterDataPushInterfaceRecords> deletes = Lists.newArrayList(); // 删除列表
        List<MasterDataPushInterfaceRecords> inserts = Lists.newArrayList(); // 添加列表
        List<MasterDataPushInterfaceRecords> updates = Lists.newArrayList(); // 更新列表
        for (MasterDataPushInterfaceRecords masterDataPushInterfaceRecordsSaved : list_MasterDataPushInterfaceRecords) {
            boolean found = false;
            for (MasterDataPushInterfaceRecords masterDataPushInterfaceRecords : masterDataPushRecords.getMasterDataPushInterfaceRecordsList()) {
                if (masterDataPushInterfaceRecordsSaved.getId().equals(masterDataPushInterfaceRecords.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(masterDataPushInterfaceRecordsSaved);
            }
        }
        if (deletes.size() > 0) {
            masterDataPushInterfaceRecordsService.bulkDelete(deletes);
        }
        for (MasterDataPushInterfaceRecords masterDataPushInterfaceRecords : masterDataPushRecords.getMasterDataPushInterfaceRecordsList()) {
            if (StringUtils.isBlank(masterDataPushInterfaceRecords.getId())) {
                masterDataPushInterfaceRecords.setMasterDataPushRecords(masterDataPushRecords);

                inserts.add(masterDataPushInterfaceRecords);
            } else {
                updates.add(masterDataPushInterfaceRecords);
            }
        }
        if (updates.size() > 0) {
            masterDataPushInterfaceRecordsService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            masterDataPushInterfaceRecordsService.bulkInsert(inserts);
        }
    }
}
