package com.zq.shiroweb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * Created by Archar on 2017/12/28.
 */
@Builder
@Getter
@Setter
@ToString
@Table(name = "permission")
public class PermissionEntity {
    private Integer pid;
    private String name;
    private String url;
}
