package com.zq.shiroweb.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * Created by Archar on 2018/1/22.
 */
public class RolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        Subject subject = this.getSubject(servletRequest, servletResponse);

        //配置的访问所需角色
        String[] rolesArray = (String[]) o;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        //需要修改的地方在这里，判断访问所需的角色是否包含当前用户的角色
        List<String> roles = CollectionUtils.asList(rolesArray);
        boolean[] bo = subject.hasRoles(roles);
        //如果有一个角色满足，则可以访问
        for(boolean b : bo){
            if(b == true){
                return true;
            }
        }
        return false;
    }
}
