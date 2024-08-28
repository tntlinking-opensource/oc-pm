package com.geeke.micro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.dbtype.entity.SysDbType;
import com.geeke.sys.entity.DictItem;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 应用版本管理Entity
 * @author
 * @version
 */

public class MicroVersion extends DataEntity<MicroVersion> {

    private static final long serialVersionUID = 1368089934395490323L;

    private MicroApp app; // 应用

    private DictItem type; // 版本类型

    private String url; // 应用入口

    private String isLocked = "0"; // 停用

    private String name; // 版本号

    private String mobilUrl; // 	 移动端入口

    private SysDbType dbType; // 数据库类型

    // 构造方法
    public MicroVersion() {
        super();
    }

    public MicroVersion(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "应用不能为空")
    public MicroApp getApp() {
        return app;
    }

    public void setApp(MicroApp app) {
        this.app = app;
    }

    @NotNull(message = "版本类型不能为空")
    public DictItem getType() {
        return type;
    }

    public void setType(DictItem type) {
        this.type = type;
    }

    @Length(min = 1, max = 1024, message = "应用入口长度必须介于 1 和 1024 之间")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 1, max = 1, message = "停用长度必须介于 1 和 1 之间")
    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    @Length(min = 1, max = 10, message = "版本号长度必须介于 1 和 10 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 1024, message = "	 移动端入口长度必须介于 1 和 1024 之间")
    public String getMobilUrl() {
        return mobilUrl;
    }

    public void setMobilUrl(String mobilUrl) {
        this.mobilUrl = mobilUrl;
    }

    @NotNull(message = "数据库类型不能为空")
    public SysDbType getDbType() {
        return dbType;
    }

    public void setDbType(SysDbType dbType) {
        this.dbType = dbType;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "micro_version";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1368089934395490323";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
