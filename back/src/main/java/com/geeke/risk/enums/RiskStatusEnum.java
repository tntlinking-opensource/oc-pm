package com.geeke.risk.enums;

public enum RiskStatusEnum {
    SAVED("0", "已保存"),
    HAPPEND("1", "已发生"),
    SOLVED("2", "已解决")
    ;

    String value;
    String desc;

    public static boolean checkStatus(String status) {
        RiskStatusEnum[] values = RiskStatusEnum.values();
        for (RiskStatusEnum value : values) {
            if (value.getValue().equals(status)) {
                return true;
            }
        }
        return false;
    }

    RiskStatusEnum(String value, String desc) {
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
