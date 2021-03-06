package com.zq.shiroweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "sys_acl_moudule")
public class SysAclModule {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "operate_time", nullable = false)
    private Date operateTime;

    @Column(name = "operate_ip", nullable = false)
    private String operateIp;

    @OneToMany
    @JoinColumn(name = "acl_module_id")
    @JsonIgnore
    private Set<SysAcl> aclSet;

    @ManyToMany
    @JoinTable(name = "sys_role_acl",
            joinColumns = {@JoinColumn(name = "acl_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @JsonIgnore
    private Set<SysRole> roleSet;
}
