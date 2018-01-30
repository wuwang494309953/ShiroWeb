package com.zq.shiroweb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Archar on 2018/1/1.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_acl")
public class SysAcl {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    /*@Column(name = "acl_module_id", nullable = false)
    private Integer aclModuleId;*/

    @ManyToOne
    @JoinColumn(name = "acl_module_id", nullable = false)
    private SysAclModule aclModule;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Column(name = "remark")
    private String remark;

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "operate_time", nullable = false)
    private Date operateTime;

    @Column(name = "operate_ip", nullable = false)
    private String operateIp;
}
