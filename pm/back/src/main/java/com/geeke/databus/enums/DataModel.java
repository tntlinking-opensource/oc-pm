package com.geeke.databus.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataModel {

    /**
     * 生产
     */
    produce("1"),
    /**
     * 消费
     */
    consume("0"),

    ;
    private String code;
}
