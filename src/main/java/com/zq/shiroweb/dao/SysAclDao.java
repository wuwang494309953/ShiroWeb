package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysAcl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
 * Created by Archar on 2018/1/21.
 */
public interface SysAclDao extends JpaRepository<SysAcl, Long>, JpaSpecificationExecutor<SysAcl> {

    SysAcl findByIdEquals(Integer aclId);

    void deleteByIdEquals(Integer aclId);

    List<SysAcl> findAllByAclModule_IdEquals(Integer aclModuleId);

}
