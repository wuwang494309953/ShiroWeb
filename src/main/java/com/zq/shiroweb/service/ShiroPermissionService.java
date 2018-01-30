package com.zq.shiroweb.service;

import com.google.common.collect.Maps;
import com.zq.shiroweb.dao.SysAclDao;
import com.zq.shiroweb.entity.SysAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Archar on 2018/1/24.
 */
@Service
public class ShiroPermissionService {
    private static final String ROLES = "roles";

    private static final String PERMS = "perms";

    @Autowired
    private SysAclDao aclDao;

    @Transactional
    public Map<String, String> initFilterMap() {
        LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/shiro/unauth", "anon");

        List<SysAcl> aclList = aclDao.findAll(new Sort(Sort.Direction.ASC, "seq"));
        /*aclList.forEach(sysAcl -> {
            Set<SysRole> roleSet = sysAcl.getAclModule().getRoleSet();
            StringBuffer roleName = new StringBuffer();
            roleSet.forEach(sysRole -> roleName.append(sysRole.getName()).append(","));
            filterChainDefinitionMap.put(sysAcl.getUrl(), pvalue(ROLES, roleName.deleteCharAt(roleName.length()-1).toString()));
        });*/

        aclList.forEach(sysAcl -> filterChainDefinitionMap.put(sysAcl.getUrl(), pvalue(PERMS, sysAcl.getName())));

        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/**", "user");
        return filterChainDefinitionMap;
    }

    private String pvalue(String k, String v) {
        return new StringBuffer(k).append("[").append(v).append("]").toString();
    }
}
