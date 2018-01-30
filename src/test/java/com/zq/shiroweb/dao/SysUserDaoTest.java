package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserDaoTest {

    @Autowired
    private SysUserDao sysUserDao;

    @Test
    public void contextLoads() {
        List<SysUser> result = sysUserDao.findAll();
        System.out.println(result);
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    @Transactional
    public void test1() {
        SysUser user = sysUserDao.findFirstByUsernameEquals("Saber");
//        SysDept dept = user.getDept();
        Set roles = user.getRoleSet();
        System.out.println(roles);
    }
}
