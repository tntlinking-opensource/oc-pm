package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 主数据批次记录Entity
 * @author
 * @version
 */

public class MasterDataJson extends DataEntity<MasterDataJson> {

    private static final long serialVersionUID = 1440676737126588442L;

    private MasterDataBatchRecords masterDataBatchRecords; // 主数据批记录表id

    private String dataJson; // json数据
    private String analysis; // 校验  默认0 未校验    1：校验

    // 构造方法
    public MasterDataJson() {
        super();
    }

    public MasterDataJson(String id) {
        super(id);
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    // 生成get和set方法

    @NotNull(message = "主数据批记录表id不能为空")
    public MasterDataBatchRecords getMasterDataBatchRecords() {
        return masterDataBatchRecords;
    }

    public void setMasterDataBatchRecords(MasterDataBatchRecords masterDataBatchRecords) {
        this.masterDataBatchRecords = masterDataBatchRecords;
    }

    @Length(min = 0, max = -1, message = "json数据长度必须介于 0 和 -1 之间")
    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "master_data_json";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1440676737126588442";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }


    @Getter
    @AllArgsConstructor
    public enum Analysis {

        /**
         * 未解析
         */
        NOT_ANALYSIS("0"),

        /**
         * 已解析
         */
        ALREADY_ANALYSIS("1"),

        ;
        private String code;
    }
}
