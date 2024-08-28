package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 应用主数据类型配置Entity
 * @author
 * @version
 */

public class AppStandardField extends DataEntity<AppStandardField> {

    private static final long serialVersionUID = 1434746435262390344L;

    private String masterDataTypeId; // 主数据类型id

    private String appId; // 应用

    private String standardFieldId; // 主数据标准字段id

    // 构造方法
    public AppStandardField() {
        super();
    }

    public AppStandardField(String id) {
        super(id);
    }

    // 生成get和set方法

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

    @Length(min = 1, max = 21, message = "主数据标准字段id长度必须介于 1 和 21 之间")
    public String getStandardFieldId() {
        return standardFieldId;
    }

    public void setStandardFieldId(String standardFieldId) {
        this.standardFieldId = standardFieldId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "app_standard_field";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1434746435262390344";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
