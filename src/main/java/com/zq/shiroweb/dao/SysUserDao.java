package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2017/12/29.
 */
public interface SysUserDao extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {

    SysUser findByUsernameEquals(String username);

    SysUser findByIdEquals(Integer id);
}
