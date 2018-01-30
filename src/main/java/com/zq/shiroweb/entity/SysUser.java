package com.zq.shiroweb.entity;

import com.google.common.collect.Sets;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "sys_user")
public class SysUser implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password")
    private String password;

    /*@Column(name = "dept_id", nullable = false)
    private Integer deptId;*/

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private SysDept dept;

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

    @ManyToMany
    @JoinTable(name = "sys_role_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<SysRole> roleSet = Sets.newHashSet();
}
