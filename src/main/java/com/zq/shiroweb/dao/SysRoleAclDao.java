package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysRoleAcl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Archar on 2018/1/22.
 */
public interface SysRoleAclDao extends JpaRepository<SysRoleAcl, Long> {
    List<SysRoleAcl> findByRoleIdEquals(Integer roleId);
}
