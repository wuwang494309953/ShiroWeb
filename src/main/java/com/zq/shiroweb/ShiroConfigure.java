package com.zq.shiroweb;

import com.google.common.collect.Maps;
import com.zq.shiroweb.dao.ShiroRedisDao;
import com.zq.shiroweb.service.ShiroPermissionService;
import com.zq.shiroweb.shiro.MyShiroService;
import com.zq.shiroweb.shiro.RolesAuthorizationFilter;
import com.zq.shiroweb.shiro.realm.MyRealm;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/29.
 */
@Configuration
public class ShiroConfigure {

    @Autowired
    private ShiroPermissionService shiroPermissionService;

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager
            , RolesAuthorizationFilter rolesAuthorizationFilter) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        bean.setSecurityManager(manager);
        bean.setLoginUrl("/shiro/login");
//        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/shiro/unauth");

//        Map<String, String> filterChainDefinitionMap = myShiroService.initFilterMap();

        /*filterChainDefinitionMap.put("/test/testRole", "roles[adminm]");
        filterChainDefinitionMap.put("/admin","roles[admin,么么]");
        filterChainDefinitionMap.put("/edit", "perms[edit]");*/

        bean.setFilterChainDefinitionMap(shiroPermissionService.initFilterMap());
        /*设置自定义的过滤器*/
        Map<String, Filter> filterMap = Maps.newLinkedHashMap();
        filterMap.put("roles", rolesAuthorizationFilter);
        bean.setFilters(filterMap);
        return bean;
    }

    @Bean
    public RolesAuthorizationFilter rolesAuthorizationFilter() {
        return new RolesAuthorizationFilter();
    }

    @Bean
    public ShiroRedisDao shiroRedisDao() {
        return new ShiroRedisDao();
    }

    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(ShiroRedisDao shiroRedisDao) {
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionDAO(shiroRedisDao);
        return manager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm, DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setSessionManager(sessionManager);
        manager.setCacheManager(new MemoryConstrainedCacheManager());
        manager.setRealm(myRealm);
//        SecurityUtils.setSecurityManager(manager);
        return manager;
    }

    @Bean("myRealm")
    public MyRealm myRealm(@Qualifier("credentialMatcher") CredentialMatcher matcher) {
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

/*    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setExposeProxy(true);
        return creator;
    }*/
}
