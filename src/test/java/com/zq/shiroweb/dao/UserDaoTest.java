package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysAcl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() {
        List<SysAcl> result = userDao.findAll();
        System.out.println(result);
        Assert.assertTrue(result.size() > 0);
    }
}
