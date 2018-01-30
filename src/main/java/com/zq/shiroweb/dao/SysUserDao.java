package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */
public interface SysUserDao extends JpaRepository<SysUser, Long>, QueryDslPredicateExecutor<SysUser> {

    SysUser findFirstByUsernameEquals(String username);

    SysUser findByIdEquals(Integer id);

    void deleteByIdEquals(Integer id);

    List<SysUser> findAllByStatusEquals(Integer status);

   /* @Query(value = "select NEW com.zq.shiroweb.dto.UserDto(u.username, u.mail) from SysUser as u")
    List<UserDto> findMyUsers();*/
}
