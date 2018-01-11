package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.param.LoginParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Archar on 2018/1/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SecurityManager securityManager;

    private MockMvc mvc;

    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
    }

    /*登录测试无法成功。https://www.cnblogs.com/ningheshutong/p/6478080.html。
    * 原因：托管spring容器时，shiro生成的sessioncontext不对。
    * */
    @Test
    public void Login() {
        try {
            mvc.perform(MockMvcRequestBuilders.post("/login")
                    .accept(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("username","admin")
                    .param("password", "123")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .session(session)
            )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Login1() {
        SecurityUtils.setSecurityManager(securityManager);
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SysUser user = (SysUser) subject.getPrincipal();
            session.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
