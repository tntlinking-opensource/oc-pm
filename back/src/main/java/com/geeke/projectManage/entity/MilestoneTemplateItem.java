package com.geeke.projectManage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.projectManage.entity.MilestoneTemplate;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 里程碑模板详情Entity
 * @author
 * @version
 */

public class MilestoneTemplateItem extends DataEntity<MilestoneTemplateItem> {

    private static final long serialVersionUID = 1740772218121938502L;

    private MilestoneTemplate template; // 里程碑模板ID

    private String milestoneName; // 里程碑名称

    private Integer progressRate; // 进度占比

    private String milestoneDesc; // 描述

    private Integer sort; // 顺序

    // 构造方法
    public MilestoneTemplateItem() {
        super();
    }

    public MilestoneTemplateItem(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "里程碑模板ID不能为空")
    public MilestoneTemplate getTemplate() {
        return template;
    }

    public void setTemplate(MilestoneTemplate template) {
        this.template = template;
    }

    @Length(min = 1, max = 50, message = "里程碑名称长度必须介于 1 和 50 之间")
    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    @NotNull(message = "进度占比不能为空")
    public Integer getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(Integer progressRate) {
        this.progressRate = progressRate;
    }

    @Length(min = 0, max = 300, message = "描述长度必须介于 0 和 300 之间")
    public String getMilestoneDesc() {
        return milestoneDesc;
    }

    public void setMilestoneDesc(String milestoneDesc) {
        this.milestoneDesc = milestoneDesc;
    }

    @NotNull(message = "顺序不能为空")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "milestone_template_item";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938502";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "里程碑模板详情";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
