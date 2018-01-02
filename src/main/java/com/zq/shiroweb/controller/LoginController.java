package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.param.LoginParam;
import com.zq.shiroweb.util.BeanValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Archar on 2018/1/1.
 */
@RestController
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonData login(LoginParam param, HttpSession session) {
        BeanValidator.check(param);
        UsernamePasswordToken token = new UsernamePasswordToken(param.getUsername(), param.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SysUser user = (SysUser) subject.getPrincipal();
            session.setAttribute("user", user);
            return JsonData.success("登录成功");
        } catch (Exception e) {
            return JsonData.fail("登录失败:" + e.getMessage());
        }
    }
}
