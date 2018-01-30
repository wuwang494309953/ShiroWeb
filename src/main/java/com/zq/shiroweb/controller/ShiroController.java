package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.entityEnum.ShiroEnum;
import com.zq.shiroweb.shiro.MyShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Archar on 2018/1/22.
 */
@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @Autowired
    private MyShiroService myShiroService;

    @RequestMapping("/login")
    public JsonData login(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        return JsonData.fail(ShiroEnum.UNLOGIN.getCode(), ShiroEnum.UNLOGIN.getMsg());
    }

    @RequestMapping("/unauth")
    public JsonData unauth(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
        return JsonData.fail(ShiroEnum.UNAUTH.getCode(), ShiroEnum.UNAUTH.getMsg());
    }

    @RequestMapping("rePermission")
    public JsonData rePermission() {
        myShiroService.updatePermission();
        return JsonData.success("缓存刷新成功");
    }
}
