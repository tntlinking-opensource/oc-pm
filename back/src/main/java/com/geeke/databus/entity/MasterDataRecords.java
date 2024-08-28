package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 主数据批次记录Entity
 * @author
 * @version
 */

public class MasterDataRecords extends DataEntity<MasterDataRecords> {

    private static final long serialVersionUID = 1440676737126588443L;

    private MasterDataBatchRecords masterDataBatchRecords; // 主数据批记录表id

    private Integer orderNumber; // 序号

    private String tenantId; // 租户id

    private String masterDataTypeId; // 主数据类型id


    private String dataKey; // 主数据key

    private String dataVersion; // 数据版本

    // 构造方法
    public MasterDataRecords() {
        super();
    }

    public MasterDataRecords(String id) {
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

    @NotNull(message = "序号不能为空")
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Length(min = 1, max = 21, message = "租户id长度必须介于 1 和 21 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Length(min = 0, max = 21, message = "主数据类型id长度必须介于 0 和 21 之间")
    public String getMasterDataTypeId() {
        return masterDataTypeId;
    }

    public void setMasterDataTypeId(String masterDataTypeId) {
        this.masterDataTypeId = masterDataTypeId;
    }

    @Length(min = 1, max = 64, message = "主数据key长度必须介于 1 和 64 之间")
    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    @NotBlank(message = "数据版本不能为空")
    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "master_data_records";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1440676737126588443";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
