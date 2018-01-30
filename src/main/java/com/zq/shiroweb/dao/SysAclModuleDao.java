package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysAclModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Archar on 2018/1/21.
 */
public interface SysAclModuleDao extends JpaRepository<SysAclModule, Long> {

    SysAclModule findByIdEquals(Integer id);

    List<SysAclModule> findAllByLevelLike(String level);
}
