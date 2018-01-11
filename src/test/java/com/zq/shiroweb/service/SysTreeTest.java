package com.zq.shiroweb.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Archar on 2018/1/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysTreeTest {

    @Autowired
    private SysTreeService sysTreeService;

    @Test
    public void test() {
        List result = sysTreeService.deptTree();
        Assert.assertTrue("测试有数据", result.size() > 0);
    }
}
