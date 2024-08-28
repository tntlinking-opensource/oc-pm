package com.geeke.risk.enums;

public enum RiskImpactEnum {
    HIGH("1", "高"),
    MIDDLE("2", "中"),
    LOW("3", "低")
    ;

    String value;
    String desc;

    public static boolean checkStatus(String status) {
        RiskImpactEnum[] values = RiskImpactEnum.values();
        for (RiskImpactEnum value : values) {
            if (value.getValue().equals(status)) {
                return true;
            }
        }
        return false;
    }

    RiskImpactEnum(String value, String desc) {
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
