package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017/12/29.
 */
public interface UserDao extends JpaRepository<UserEntity, Long> {
    UserEntity findByUidEquals(String uid);
}
