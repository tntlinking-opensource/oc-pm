package com.geeke.databus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldType {

    FIELD_STRING("string","0"),
    FIELD_LONG("long","1"),
    FIELD_DOUBLE("double","2");
    private String type;
    private String code;
}
