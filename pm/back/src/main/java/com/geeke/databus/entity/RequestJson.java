package com.geeke.databus.entity;

import com.geeke.common.persistence.DataEntity;

public class RequestJson extends DataEntity<RequestJson> {
    private String code;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RequestJson() {
            super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
