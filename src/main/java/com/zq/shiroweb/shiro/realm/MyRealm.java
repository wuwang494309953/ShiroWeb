package com.zq.shiroweb.shiro.realm;

import com.zq.shiroweb.entity.SysAcl;
import com.zq.shiroweb.entity.SysAclModule;
import com.zq.shiroweb.entity.SysRole;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.service.SysUserService;
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
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        Set<SysRole> roleSet = user.getRoleSet();
        if (CollectionUtils.isNotEmpty(roleSet)) {
            for (SysRole role : roleSet) {
                roleNameList.add(role.getName());
                Set<SysAclModule> permissionSet = role.getAclModuleSet();
                if (CollectionUtils.isNotEmpty(permissionSet)) {
                    for (SysAclModule sysAclModule : permissionSet) {
                        sysAclModule.getAclSet().forEach(sysAcl -> {
                            permissionList.add(sysAcl.getName());
                        });
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
        SysUser user = sysUserService.findUserWithName(token.getUsername());
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
