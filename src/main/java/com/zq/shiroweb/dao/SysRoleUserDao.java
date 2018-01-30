package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysRoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Archar on 2018/1/20.
 */
public interface SysRoleUserDao extends JpaRepository<SysRoleUser, Long> {

    List<SysRoleUser> findByRoleIdEquals(Integer roleId);
}
