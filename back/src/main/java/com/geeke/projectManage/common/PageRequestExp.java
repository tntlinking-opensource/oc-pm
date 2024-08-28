package com.geeke.projectManage.common;

import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.BaseEntity;
import com.geeke.dc.utils.DcConfigure;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PageRequestExp<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int offset;

    private int limit;

    private String order;

    private List<Parameter> params;

    private T extra;

    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
    }

    public PageRequestExp(int offset, int limit, List<Parameter> parameters, String order, T extra) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }

        this.offset = offset;
        this.limit = limit;
        if (parameters == null) {
            this.params = Lists.newArrayList();
        } else {
            this.params = parameters;
        }

        this.order = order;
        this.extra = extra;
    }

    public PageRequestExp(List<Parameter> parameters, T extra) {
        this.params = Objects.isNull(parameters) ? Lists.newArrayList() : parameters;
        this.extra = extra;
    }


    public int getPage() {
        return this.offset;
    }

    public int getSize() {
        return this.limit;
    }

    public String getOrder() {
        return this.order;
    }

    public int getOffset() {
        return this.offset;
    }

    public List<Parameter> getParams() {
        return params;
    }

    public void setParams(List<Parameter> params) {
        this.params = params;
    }

    /**
     * 获取数据库名称
     */
    public String getDbName() {
        return DcConfigure.dbName;
    }


    /* 数据库存储状态 */
    public String getDEL_FLAG_NORMAL() {
        return BaseEntity.DEL_FLAG_NORMAL;
    }

    public String getDEL_FLAG_DELETE() {
        return BaseEntity.DEL_FLAG_DELETE;
    }

    public String getDEL_FLAG_AUDIT() {
        return BaseEntity.DEL_FLAG_AUDIT;
    }
}
