package com.zq.shiroweb.entityEnum;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum RoleTypeEnum {
    ADMIN("管理员"),NORMAL("普通");

    private String value;

    RoleTypeEnum(String value) {
        this.value = value;
    }
}
