package com.zq.shiroweb.entityEnum;

import lombok.ToString;

/**
 * Created by Archar on 2018/1/15.
 */
@ToString
public enum  UserStatusEnum {
    ACTIVED("启用"),
    UNACTIVED("未激活"),
    DISABLED("禁用");

    private String value;

    UserStatusEnum(String value) {
        this.value = value;
    }
}

