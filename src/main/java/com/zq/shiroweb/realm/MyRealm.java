package com.zq.shiroweb.realm;

import com.zq.shiroweb.dao.SysUserDao;
import com.zq.shiroweb.entity.SysAcl;
import com.zq.shiroweb.entity.SysRole;
import com.zq.shiroweb.entity.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Archar on 2017/12/28.
 */
@Transactional
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        Set<SysRole> roleSet = user.getRoleSet();
        if (CollectionUtils.isNotEmpty(roleSet)) {
            for (SysRole role : roleSet) {
                roleNameList.add(role.getName());
                Set<SysAcl> permissionSet = role.getAclSet();
                if (CollectionUtils.isNotEmpty(permissionSet)) {
                    for (SysAcl permission : permissionSet) {
                        permissionList.add(permission.getName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SysUser user = sysUserDao.findByUsernameEquals(token.getUsername());
        return new SimpleAuthenticationInfo(user, token.getPassword(), getName());
    }
}
