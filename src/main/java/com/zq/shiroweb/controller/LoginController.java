package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.param.LoginParam;
import com.zq.shiroweb.util.BeanValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Archar on 2018/1/1.
 */
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private SecurityManager securityManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonData login(LoginParam param) {
        BeanValidator.check(param);
        UsernamePasswordToken token = new UsernamePasswordToken(param.getUsername(), param.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SysUser user = (SysUser) subject.getPrincipal();

            subject.getSession().setAttribute("user", user);
            return JsonData.success("登录成功");
        } catch (AuthenticationException e) {
            return JsonData.fail("用户名或密码错误");
        }
    }

    @RequestMapping("/test")
    public JsonData test(HttpSession session) {
        String str = "这里是应用一";
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        Session mysession = subject.getSession();
        mysession.setAttribute("name", str);
        return JsonData.success(str);
    }
}
