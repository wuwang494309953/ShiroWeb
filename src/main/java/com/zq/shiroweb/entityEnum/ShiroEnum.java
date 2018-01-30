package com.zq.shiroweb.entityEnum;

import lombok.Getter;

/**
 * Created by Archar on 2018/1/22.
 */
@Getter
public enum ShiroEnum {
    UNLOGIN("E001", "用户未登陆"),
    UNAUTH("E002", "用户没有权限");

    private String code;

    private String msg;

    ShiroEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
