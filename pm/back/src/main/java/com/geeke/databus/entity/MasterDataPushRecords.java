package com.geeke.databus.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.IdGen;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 主数据批次分发记录Entity
 *
 * @author
 */

public class MasterDataPushRecords extends DataEntity<MasterDataPushRecords> {

    private static final long serialVersionUID = 1442152230191628292L;

    private MasterDataBatchRecords masterDataBatchRecords; // 主数据批记录表id

    private String tenantId; // 租户id

    private MasterDataType masterDataType; // 主数据类型id

    private String dataKey; // 主数据key

    private Integer dataVersion; // 数据版本

    private Map<String, Object> targetApp;
    private String targetAppId;

    private String pushStatus; // 推送状态

    private String lastSendTime; // 最后发送时间

    private String pushFailInfo; // 推送失败信息

    private Integer pushFailSize; // 推送失败次数

    private List<MasterDataPushInterfaceRecords> masterDataPushInterfaceRecordsList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public MasterDataPushRecords() {
        super();
    }

    public MasterDataPushRecords(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "主数据批记录表id不能为空")
    public MasterDataBatchRecords getMasterDataBatchRecords() {
        return masterDataBatchRecords;
    }

    public void setMasterDataBatchRecords(MasterDataBatchRecords masterDataBatchRecords) {
        this.masterDataBatchRecords = masterDataBatchRecords;
    }

    @Length(min = 1, max = 21, message = "租户id长度必须介于 1 和 21 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @NotNull(message = "主数据类型id不能为空")
    public MasterDataType getMasterDataType() {
        return masterDataType;
    }

    public void setMasterDataType(MasterDataType masterDataType) {
        this.masterDataType = masterDataType;
    }

    @Length(min = 1, max = 64, message = "主数据key长度必须介于 1 和 64 之间")
    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    @NotNull(message = "接收应用不能为空")
    public Map<String, Object> getTargetApp() {
        return targetApp;
    }

    public void setTargetApp(Map<String, Object> targetApp) {
        this.targetApp = targetApp;
    }

    public String getTargetAppId() {
        return targetAppId;
    }

    public void setTargetAppId(String targetAppId) {
        this.targetAppId = targetAppId;
    }

    @Length(min = 0, max = 8, message = "推送状态长度必须介于 0 和 8 之间")
    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    @Length(min = 0, max = -1, message = "最后发送时间长度必须介于 0 和 -1 之间")
    public String getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(String lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    @Length(min = 0, max = 255, message = "推送失败信息长度必须介于 0 和 255 之间")
    public String getPushFailInfo() {
        return pushFailInfo;
    }

    public void setPushFailInfo(String pushFailInfo) {
        this.pushFailInfo = pushFailInfo;
    }

    public Integer getPushFailSize() {
        return pushFailSize;
    }

    public void setPushFailSize(Integer pushFailSize) {
        this.pushFailSize = pushFailSize;
    }

    public List<MasterDataPushInterfaceRecords> getMasterDataPushInterfaceRecordsList() {
        return masterDataPushInterfaceRecordsList;
    }

    public void setMasterDataPushInterfaceRecordsList(List<MasterDataPushInterfaceRecords> masterDataPushInterfaceRecordsList) {
        this.masterDataPushInterfaceRecordsList = masterDataPushInterfaceRecordsList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "master_data_push_records";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1442152230191628292";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }

    @Override
    public void preInsert() {
        if (StringUtils.isBlank(getId())) {
            setId(IdGen.uuid());
        }
        try {
            JSONObject userObj = SessionUtils.getUserJson();
            if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))) {
                this.updateBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
                this.createBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
            }else{
                this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
                this.createBy = "SYSTEM" + "(" + "SYSTEM" + ")";
            }
        } catch (Exception e) {
            this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
            this.createBy = "SYSTEM" + "(" + "SYSTEM" + ")";
        }
        this.updateDate = simpleDateFormat.format(new Date());
        this.createDate = this.updateDate;
    }

    @Override
    public void preUpdate() {
        try{
            JSONObject userObj = SessionUtils.getUserJson();
            if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))) {
                this.updateBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
            }else{
                this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
            }
        }catch (Exception e){
            this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
        }
        this.oldUpdateDate = this.updateDate;
        this.updateDate = simpleDateFormat.format(new Date());
    }
}
