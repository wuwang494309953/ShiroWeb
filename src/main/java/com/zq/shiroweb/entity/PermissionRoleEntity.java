package com.zq.shiroweb.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Archar on 2017/12/28.
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission_role")
public class PermissionRoleEntity {

    @Id
    @GeneratedValue
    @Column(name = "rid", nullable = false, insertable = false)
    private Integer rid;

    @Column(name = "pid", nullable = false)
    private Integer pid;

}
