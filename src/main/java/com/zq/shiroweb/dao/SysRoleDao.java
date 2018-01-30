package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Archar on 2018/1/18.
 */
public interface SysRoleDao extends JpaRepository<SysRole, Long> {
    SysRole findByIdEquals(Integer id);

    void deleteByIdEquals(Integer roleId);
}
