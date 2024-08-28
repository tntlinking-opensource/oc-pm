package com.geeke.databus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 平台主数据Entity
 * @author
 * @version
 */

public class MasterDataType extends DataEntity<MasterDataType> {

    private static final long serialVersionUID = 1434540843767783426L;

    private String code; // 主数据code

    private String name; // 主数据名称

    private Integer isSystem; // 是否为系统主数据 0：不是 1：是

    private List<StandardField> standardFieldList=Lists.newArrayList(); // 子表列表

    // 构造方法
    public MasterDataType() {
        super();
    }

    public MasterDataType(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 16, message = "主数据code长度必须介于 1 和 16 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 1, max = 32, message = "主数据名称长度必须介于 1 和 32 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "是否为系统主数据 0：不是 1：是不能为空")
    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public List<StandardField> getStandardFieldList() {
        return standardFieldList;
    }

    public void setStandardFieldList(List<StandardField> standardFieldList) {
        this.standardFieldList = standardFieldList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "master_data_type";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1434540843767783426";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
