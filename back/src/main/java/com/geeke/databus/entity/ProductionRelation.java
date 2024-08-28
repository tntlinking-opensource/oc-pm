package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 生产消费关系Entity
 * @author
 * @version
 */
public class ProductionRelation extends DataEntity<ProductionRelation> {

    private static final long serialVersionUID = 1436276714930348034L;

    public static final int consumer =1;

    public static final int producer =0;

    private String tenantId; // 租户id
    private String tenantName; // 租户id

    private String masterDataTypeId; // 主数据类型id
    private String masterDataTypeName; // 主数据类型id

    private String appId; // 应用

    private String dataModel; // 模式 定义枚举（0:消费  1:生产） 为消费时，租户消费对接数据 0

    // 构造方法
    public ProductionRelation() {
        super();
    }

    public ProductionRelation(String id) {
        super(id);
    }

    // 生成get和set方法

    public String getMasterDataTypeName() {
        return masterDataTypeName;
    }

    public void setMasterDataTypeName(String masterDataTypeName) {
        this.masterDataTypeName = masterDataTypeName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
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

    @Length(min = 1, max = 21, message = "应用长度必须介于 1 和 21 之间")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Length(min = 1, max = 16, message = "模式 定义枚举（消费  生产） 为消费时，租户消费对接数据长度必须介于 1 和 16 之间")
    public String getDataModel() {
        return dataModel;
    }

    public void setDataModel(String dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "production_relation";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1436276714930348034";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
