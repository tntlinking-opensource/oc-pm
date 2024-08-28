package com.geeke.dbtype.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 数据库类型管理Entity
 * @author
 * @version
 */

public class SysDbType extends DataEntity<SysDbType> {

    private static final long serialVersionUID = 1371119793305198596L;

    private String name; // 名称

    private String driver; // 驱动

    private String rule; // 连接地址规则

    // 构造方法
    public SysDbType() {
        super();
    }

    public SysDbType(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 128, message = "驱动长度必须介于 1 和 128 之间")
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Length(min = 1, max = 128, message = "连接地址规则长度必须介于 1 和 128 之间")
    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_db_type";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1371119793305198596";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
