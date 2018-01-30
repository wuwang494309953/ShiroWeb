package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysRole;
import com.zq.shiroweb.param.RoleParam;
import com.zq.shiroweb.util.EntityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Archar on 2018/1/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleDaoTest {

    @Autowired
    private SysRoleDao roleDao;

    @Test
    public void test() {
        List<SysRole> roles = roleDao.findAll();
        List<RoleParam> list = EntityUtil.listOf(roleDao.findAll(), RoleParam.class);
        System.out.println(list.size());
    }
}
