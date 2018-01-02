package com.zq.shiroweb;

import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.util.RegExPatternMatcher;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2018/1/2.
 */
public class NormalTests {

    /*
    * url路径匹配测试
    * eg：/**匹配所有请求，/regex/* 匹配/regex开头的请求
    * */
    @Test
    public void matchUrl() {
        PatternMatcher matcher = new AntPathMatcher();
        boolean flag = matcher.matches("/regex", "/regex/aa/bb");
        Assert.assertTrue(flag);
    }
}
