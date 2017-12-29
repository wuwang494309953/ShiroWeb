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
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @GeneratedValue
    @Column(name = "uid", nullable = false, insertable = false)
    private Integer uid;

    @Column(name = "rid", nullable = false)
    private Integer rid;
}
