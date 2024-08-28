package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 应用主数据类型配置Entity
 * @author
 * @version
 */

public class AppMasterDataType extends DataEntity<AppMasterDataType> {

    private static final long serialVersionUID = 1434746435262390343L;

    private MasterDataType masterDataType; // 主数据类型id

    private String appId; // 应用

    private String defaultWorkModel; // 默认工作模式  消费者:0  生产者：1

    private String produceWorkModel; // 是否支持生产者工作模式 支持：1 不支持：0

    private String consumerWorkModel; // 是否支持消费者模式 支持：1 不支持：0

    private String level = "0"; // level

    private String auditStatus = "0"; // 审核状态  审核中：0  已审核：1

    // 构造方法
    public AppMasterDataType() {
        super();
    }

    public AppMasterDataType(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "主数据类型id不能为空")
    public MasterDataType getMasterDataType() {
        return masterDataType;
    }

    public void setMasterDataType(MasterDataType masterDataType) {
        this.masterDataType = masterDataType;
    }

    @Length(min = 0, max = 21, message = "应用长度必须介于 0 和 21 之间")
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Length(min = 1, max = 8, message = "默认工作模式长度必须介于 1 和 8 之间")
    public String getDefaultWorkModel() {
        return defaultWorkModel;
    }

    public void setDefaultWorkModel(String defaultWorkModel) {
        this.defaultWorkModel = defaultWorkModel;
    }

    @Length(min = 0, max = 8, message = "是否支持生产者工作模式长度必须介于 0 和 8 之间")
    public String getProduceWorkModel() {
        return produceWorkModel;
    }

    public void setProduceWorkModel(String produceWorkModel) {
        this.produceWorkModel = produceWorkModel;
    }

    @Length(min = 0, max = 8, message = "是否支持消费者模式长度必须介于 0 和 8 之间")
    public String getConsumerWorkModel() {
        return consumerWorkModel;
    }

    public void setConsumerWorkModel(String consumerWorkModel) {
        this.consumerWorkModel = consumerWorkModel;
    }

    @Length(min = 0, max = 2, message = "level长度必须介于 0 和 2 之间")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Length(min = 0, max = 8, message = "审核状态长度必须介于 0 和 8 之间")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }


    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "app_master_data_type";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1434746435262390343";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
