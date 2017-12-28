package com.zq.shiroweb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Archar on 2017/12/28.
 */
@Builder
@Getter
@Setter
@ToString
@Table(name = "user")
public class UserEntity {
    private Integer uid;

    private String username;

    private String password;

    private Set<RoleEntity> roles = new HashSet<>();
}
