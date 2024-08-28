package com.geeke.projectManage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.DictItem;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 里程碑模板Entity
 * @author
 * @version
 */

public class MilestoneTemplate extends DataEntity<MilestoneTemplate> {

    private static final long serialVersionUID = 1740772218121938467L;

    private String templateName; // 模板名称

    private DictItem status; // 状态

    // 构造方法
    public MilestoneTemplate() {
        super();
    }

    public MilestoneTemplate(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 50, message = "模板名称长度必须介于 1 和 50 之间")
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @NotNull(message = "状态不能为空")
    public DictItem getStatus() {
        return status;
    }

    public void setStatus(DictItem status) {
        this.status = status;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "milestone_template";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938467";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "里程碑模板";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
