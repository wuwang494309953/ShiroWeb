package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Archar on 2017/12/30.
 */
@RestController
@RequestMapping("/role")
public class BaseController {

    @RequestMapping("/hasRoles")
    public JsonData hasRoles() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("admin")) {
            return JsonData.success("拥有角色");
        } else {
            return JsonData.fail("没有权限");
        }
    }
}
