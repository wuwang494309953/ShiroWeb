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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "uid", nullable = false, insertable = false)
    private Integer uid;

    @Column(name = "st_username", nullable = false)
    private String username;

    @Column(name = "st_password", nullable = false)
    private String password;

    @Transient
    private Set<RoleEntity> roles = new HashSet<>();
}
