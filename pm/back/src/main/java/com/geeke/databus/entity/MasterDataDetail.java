package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 主数据解析数据Entity
 * @author
 * @version
 */

public class MasterDataDetail extends DataEntity<MasterDataDetail> {

    private static final long serialVersionUID = 1440756915576135687L;

    private String masterDataRecordsId; // 主数据记录表id

    private String tenantId; // 租户id

    private String masterDataTypeId; // 主数据类型id

    private String dataKey; // 主数据key

    private String dataVersion; // 数据版本

    private String standardFieldId; // standard_field表中id字段

    private String masterDataInfoFiled; // 主数据详情字段

    private String masterDataInfoValue; // 字段对应值

    // 构造方法
    public MasterDataDetail() {
        super();
    }

    public MasterDataDetail(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 21, message = "主数据记录表id长度必须介于 1 和 21 之间")
    public String getMasterDataRecordsId() {
        return masterDataRecordsId;
    }

    public void setMasterDataRecordsId(String masterDataRecordsId) {
        this.masterDataRecordsId = masterDataRecordsId;
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

    @NotNull(message = "数据版本不能为空")
    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    @Length(min = 1, max = 21, message = "standard_field表中id字段长度必须介于 1 和 21 之间")
    public String getStandardFieldId() {
        return standardFieldId;
    }

    public void setStandardFieldId(String standardFieldId) {
        this.standardFieldId = standardFieldId;
    }

    @Length(min = 1, max = 32, message = "主数据详情字段长度必须介于 1 和 32 之间")
    public String getMasterDataInfoFiled() {
        return masterDataInfoFiled;
    }

    public void setMasterDataInfoFiled(String masterDataInfoFiled) {
        this.masterDataInfoFiled = masterDataInfoFiled;
    }

    @Length(min = 0, max = 128, message = "字段对应值长度必须介于 0 和 128 之间")
    public String getMasterDataInfoValue() {
        return masterDataInfoValue;
    }

    public void setMasterDataInfoValue(String masterDataInfoValue) {
        this.masterDataInfoValue = masterDataInfoValue;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "master_data_detail";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1440756915576135687";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
