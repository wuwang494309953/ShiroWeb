package com.zq.shiroweb.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zq.shiroweb.dao.SysRoleAclDao;
import com.zq.shiroweb.dao.SysRoleDao;
import com.zq.shiroweb.dao.SysRoleUserDao;
import com.zq.shiroweb.entity.SysAclModule;
import com.zq.shiroweb.entity.SysRole;
import com.zq.shiroweb.entity.SysRoleAcl;
import com.zq.shiroweb.entity.SysRoleUser;
import com.zq.shiroweb.entityEnum.RoleTypeEnum;
import com.zq.shiroweb.exception.ParamException;
import com.zq.shiroweb.param.RoleParam;
import com.zq.shiroweb.util.BeanValidator;
import com.zq.shiroweb.util.EntityUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Archar on 2018/1/18.
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao roleDao;

    @Autowired
    private SysRoleUserDao roleUserDao;

    @Autowired
    private SysRoleAclDao roleAclDao;

    @Transactional
    public void save(RoleParam param) {
        BeanValidator.check(param);
        SysRole role = SysRole.builder()
                .name(param.getName())
                .status(param.getStatus())
                .remark(param.getRemark())
                .type(param.getType())
                .build();
        role.setOperator("system"); //TODO:
        role.setOperateIp("localhost"); //TODO:
        role.setOperateTime(new Date());

        roleDao.save(role);
    }

    @Transactional
    public void update(RoleParam param) {
        BeanValidator.check(param);
        SysRole before = roleDao.findByIdEquals(param.getId());

        try {
            PropertyUtils.copyProperties(before, param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamException("更新角色属性失败");
        }
        before.setOperator("system"); //TODO:
        before.setOperateIp("localhost"); //TODO:
        before.setOperateTime(new Date());

        roleDao.save(before);
    }

    @Transactional
    public void delRoleWithId(Integer roleId) {
        roleDao.deleteByIdEquals(roleId);
    }

    @Transactional
    public void changeUsers(Integer roleId, Integer[] userIds) {
        List<SysRoleUser> beforeRoleUsers = roleUserDao.findByRoleIdEquals(roleId);

        Set<Integer> beforeUserIds = Sets.newHashSet();

        Set<Integer> after = Sets.newHashSet(userIds);
        beforeRoleUsers.forEach(sysRoleUser -> {
            //找出已经删除的删除掉
            if (!after.contains(sysRoleUser.getUserId())) {
                roleUserDao.delete(sysRoleUser);
            }
            beforeUserIds.add(sysRoleUser.getUserId());
        });

        //这样after剩下的数据都是新增的
        after.removeAll(beforeUserIds);
        after.forEach(userId -> {
            SysRoleUser roleUser = SysRoleUser.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .build();
            roleUser.setOperator("system"); //TODO:
            roleUser.setOperateIp("localhost"); //TODO:
            roleUser.setOperateTime(new Date());
            roleUserDao.save(roleUser);
        });
    }

    @Transactional
    public void changeAcls(Integer roleId, Integer[] aclIds) {
        List<SysRoleAcl> beforeRoleAcls = roleAclDao.findByRoleIdEquals(roleId);

        Set<Integer> beforeAclIds = Sets.newHashSet();

        Set<Integer> after = Sets.newHashSet(aclIds);
        beforeRoleAcls.forEach(sysRoleAcl -> {
            //找出已经删除的删除掉
            if (!after.contains(sysRoleAcl.getAclId())) {
                roleAclDao.delete(sysRoleAcl);
            }
            beforeAclIds.add(sysRoleAcl.getAclId());
        });

        //这样after剩下的数据都是新增的
        after.removeAll(beforeAclIds);
        after.forEach(aclId -> {
            SysRoleAcl roleAcl = SysRoleAcl.builder()
                    .roleId(roleId)
                    .aclId(aclId)
                    .build();
            roleAcl.setOperator("system"); //TODO:
            roleAcl.setOperateIp("localhost"); //TODO:
            roleAcl.setOperateTime(new Date());
            roleAclDao.save(roleAcl);
        });
    }

    public Map<String, Object> findUserIdsWithRoleId(Integer roleId) {
        Map<String, Object> result = Maps.newHashMap();

        List<Integer> userIds = Lists.newLinkedList();
        List<SysRoleUser> roleUsers = roleUserDao.findByRoleIdEquals(roleId);
        roleUsers.forEach(sysRoleUser -> userIds.add(sysRoleUser.getUserId()));

        result.put("roleId", roleId);
        result.put("userIds", userIds);
        return result;
    }

    public List<RoleParam> findRoles() {
        return EntityUtil.listOf(roleDao.findAll(), RoleParam.class);
    }
}
