package com.geeke.risk.enums;

public enum RiskTypeEnum {
    JS_RISK("1", "技术风险"),
    SC_RISK("2", "市场风险"),
    HJ_RISK("3", "环境风险")
    ;

    String value;
    String desc;

    public static boolean checkStatus(String status) {
        RiskTypeEnum[] values = RiskTypeEnum.values();
        for (RiskTypeEnum value : values) {
            if (value.getValue().equals(status)) {
                return true;
            }
        }
        return false;
    }

    RiskTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
