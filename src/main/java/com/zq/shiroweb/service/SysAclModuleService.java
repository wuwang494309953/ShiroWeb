package com.zq.shiroweb.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zq.shiroweb.dao.SysAclModuleDao;
import com.zq.shiroweb.dao.SysRoleAclDao;
import com.zq.shiroweb.entity.SysAclModule;
import com.zq.shiroweb.entity.SysRoleAcl;
import com.zq.shiroweb.param.AclModuleParam;
import com.zq.shiroweb.util.BeanValidator;
import com.zq.shiroweb.util.LevelUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Archar on 2018/1/21.
 */
@Service
public class SysAclModuleService {

    @Autowired
    private SysAclModuleDao aclModuleDao;

    @Autowired
    private SysRoleAclDao roleAclDao;

    @Transactional
    public void save(AclModuleParam param) {
        BeanValidator.check(param);

        SysAclModule aclModule = SysAclModule.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .status(param.getStatus())
                .remark(param.getRemark())
                .build();

        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator("system"); //TODO:
        aclModule.setOperateIp("localhost"); //TODO:
        aclModule.setOperateTime(new Date());

        aclModuleDao.save(aclModule);
    }

    @Transactional
    public void update(AclModuleParam param) {
        BeanValidator.check(param);

        SysAclModule before = aclModuleDao.findByIdEquals(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限不存在");

        SysAclModule after = SysAclModule.builder()
                .id(param.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .status(param.getStatus())
                .remark(param.getRemark())
                .build();

        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator("system"); //TODO:
        after.setOperateIp("localhost"); //TODO:
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    public Map<String, Object> findAclModuleIdsWithRoleId(Integer roleId) {

        List<Integer> aclIds = Lists.newLinkedList();
        List<SysRoleAcl> aclList = roleAclDao.findByRoleIdEquals(roleId);
        aclList.forEach(sysRoleAcl -> {
            aclIds.add(sysRoleAcl.getAclId());
        });
        Map<String, Object> result = Maps.newHashMap();
        result.put("roleId", roleId);
        result.put("aclIds", aclIds);
        return result;
    }

    private void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclModule> aclModuleList = aclModuleDao.findAllByLevelLike(before.getLevel() + "." + before.getId() + "%");
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                    aclModuleDao.save(aclModule);
                }
            }
        }
        aclModuleDao.save(after);
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = aclModuleDao.findByIdEquals(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }
}
