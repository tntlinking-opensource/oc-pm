package com.geeke.projectManage.common;

import com.geeke.common.controller.SearchParams;

public class SearchParamsExp<T> extends SearchParams {

    /**
     * 分页查询额外参数
     */
    private T extra;

    /**
     * 将查询条件转换成分页请求类
     * @author Yogaa
     * @date 16:45 2023/12/27
     */
    public PageRequestExp<T> transferPageRequest() {
        return new PageRequestExp<>(this.getOffset(), this.getLimit(), this.getParams(), this.getOrder(), this.getExtra());
    }

    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
    }
}
