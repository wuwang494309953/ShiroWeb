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
@Table(name = "permission")
public class PermissionEntity {

    @Id
    @GeneratedValue
    @Column(name = "pid", nullable = false, insertable = false)
    private Integer pid;

    @Column(name = "st_name",nullable = false)
    private String name;

    @Column(name = "st_url")
    private String url;
}
