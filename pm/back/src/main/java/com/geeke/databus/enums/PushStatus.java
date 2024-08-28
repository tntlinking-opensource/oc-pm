package com.geeke.databus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PushStatus {

    NOT_PUSH("未推送","0"),
    ALREADY_PUSH("已推送","1"),
    FAIL_PUSH("推送失败","2"),
    SUCCESS_PUSH("推送成功","3"),

    ;
    private String msg;
    private String code;
}
