package com.zq.shiroweb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue
    @Column(name = "rid", nullable = false, insertable = false)
    private Integer rid;

    @Column(name = "st_rname", nullable = false)
    private String rname;

    @Transient
    private Set<PermissionEntity> permissions = new HashSet<>();

    @Transient
    private Set<UserEntity> users = new HashSet<>();
}
