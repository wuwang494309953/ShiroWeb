package com.zq.shiroweb.testController;

import com.zq.shiroweb.common.JsonData;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/29.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/testRole")
    public JsonData testRole() {
        return JsonData.success("请求成功啦");
    }
}
