package com.zq.shiroweb.testController;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/29.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test1")
    public String test() {
        SecurityUtils.getSubject();
        return "这是一个十分炫酷的测试";
    }
}
