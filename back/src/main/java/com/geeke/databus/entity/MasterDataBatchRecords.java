package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 主数据批次记录Entity
 * @author
 * @version
 */

public class MasterDataBatchRecords extends DataEntity<MasterDataBatchRecords> {

    private static final long serialVersionUID = 1440676737126588419L;

    private String dataBatch; // 主数据批记录

    private String tenantId; // 租户id

    private String masterDataTypeId; // 主数据类型id

    private String sourceAppId; // 应用来源

    private String receiveDate; // 接收时间

    private String targetAppId; // 需要发送到应的应用

    private String pushStatus; // 推送状态

    private String lastSendTime; // 最后发送时间

    private List<MasterDataJson> masterDataJsonList = Lists.newArrayList(); // 子表列表

    private List<MasterDataRecords> masterDataRecordsList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public MasterDataBatchRecords() {
        super();
    }

    public MasterDataBatchRecords(String id) {
        super(id);
    }

    // 生成get和set方法

    public String getDataBatch() {
        return dataBatch;
    }

    public void setDataBatch(String dataBatch) {
        this.dataBatch = dataBatch;
    }

    @Length(min = 1, max = 21, message = "租户id长度必须介于 1 和 21 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Length(min = 1, max = 21, message = "主数据类型id长度必须介于 1 和 21 之间")
    public String getMasterDataTypeId() {
        return masterDataTypeId;
    }

    public void setMasterDataTypeId(String masterDataTypeId) {
        this.masterDataTypeId = masterDataTypeId;
    }

    @Length(min = 0, max = 21, message = "应用来源长度必须介于 0 和 21 之间")
    public String getSourceAppId() {
        return sourceAppId;
    }

    public void setSourceAppId(String sourceAppId) {
        this.sourceAppId = sourceAppId;
    }

    @Length(min = 0, max = -1, message = "接收时间长度必须介于 0 和 -1 之间")
    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Length(min = 0, max = 21, message = "需要发送到应的应用长度必须介于 0 和 21 之间")
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

    public List<MasterDataJson> getMasterDataJsonList() {
        return masterDataJsonList;
    }

    public void setMasterDataJsonList(List<MasterDataJson> masterDataJsonList) {
        this.masterDataJsonList = masterDataJsonList;
    }

    public List<MasterDataRecords> getMasterDataRecordsList() {
        return masterDataRecordsList;
    }

    public void setMasterDataRecordsList(List<MasterDataRecords> masterDataRecordsList) {
        this.masterDataRecordsList = masterDataRecordsList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "master_data_batch_records";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1440676737126588419";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
