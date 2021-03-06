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
@Table(name = "sys_role_user")
public class SysRoleUser {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "operate_time", nullable = false)
    private Date operateTime;

    @Column(name = "operate_ip", nullable = false)
    private String operateIp;
}
