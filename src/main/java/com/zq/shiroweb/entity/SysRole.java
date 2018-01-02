package com.zq.shiroweb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Archar on 2018/1/1.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_role")
public class SysRole {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "remark", nullable = false)
    private String remark;

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "operate_time", nullable = false)
    private Date operateTime;

    @Column(name = "operate_ip", nullable = false)
    private String operateIp;

    @ManyToMany
    @JoinTable(name = "sys_role_acl",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "acl_id", referencedColumnName = "id")})
    private Set<SysAcl> aclSet;

    @ManyToMany
    @JoinTable(name = "sys_role_user",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<SysUser> userSet;
}

