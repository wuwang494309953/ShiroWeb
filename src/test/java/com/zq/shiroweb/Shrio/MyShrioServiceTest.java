package com.zq.shiroweb.Shrio;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zq.shiroweb.dao.SysAclDao;
import com.zq.shiroweb.entity.SysAcl;
import com.zq.shiroweb.entity.SysRole;
import com.zq.shiroweb.shiro.MyShiroService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Archar on 2018/1/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyShrioServiceTest {

    @Autowired
    private SysAclDao aclDao;

    @Autowired
    private MyShiroService myShiroService;

    @Test
    @Transactional
    public void test1() {
        LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        List<SysAcl> aclList = aclDao.findAll();
        aclList.forEach(sysAcl -> {
            Set<SysRole> roleSet = sysAcl.getAclModule().getRoleSet();
            StringBuffer roleName = new StringBuffer();
            roleSet.forEach(sysRole -> {
                roleName.append(sysRole.getName()).append(",");
            });
            System.out.println(roleSet.size());
            System.out.println(sysAcl.getUrl() + "---" + roleName.deleteCharAt(roleName.length()-1).toString());
        });
    }

}
