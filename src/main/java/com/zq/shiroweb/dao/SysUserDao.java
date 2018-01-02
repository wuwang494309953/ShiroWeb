package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/12/29.
 */
public interface SysUserDao extends JpaRepository<SysUser, Long> {

    SysUser findByUsernameEquals(String username);
}
