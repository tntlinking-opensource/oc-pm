package com.geeke.micro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 应用管理Entity
 * @author
 * @version
 */

public class MicroApp extends DataEntity<MicroApp> {

    private static final long serialVersionUID = 1368089934395490306L;

    private User developer; // 开发者

    private String name; // 应用名

    private String fullName; // 应用全称

    private String isLocked = "0"; // 禁用

    private String iconId; // 应用图标

    private String appKey; // AppKey

    // 构造方法
    public MicroApp() {
        super();
    }

    public MicroApp(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "开发者不能为空")
    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    @Length(min = 1, max = 128, message = "应用名长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 256, message = "应用全称长度必须介于 1 和 256 之间")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Length(min = 1, max = 1, message = "禁用长度必须介于 1 和 1 之间")
    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    @NotNull(message = "应用图标不能为空")
    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    @Length(min = 1, max = 40, message = "AppKey长度必须介于 1 和 40 之间")
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "micro_app";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1368089934395490306";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
