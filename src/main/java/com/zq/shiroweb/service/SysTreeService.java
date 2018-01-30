package com.zq.shiroweb.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.zq.shiroweb.dao.SysAclModuleDao;
import com.zq.shiroweb.dao.SysDeptDao;
import com.zq.shiroweb.dto.AclModuleLevelDto;
import com.zq.shiroweb.dto.DeptLevelDto;
import com.zq.shiroweb.entity.SysAclModule;
import com.zq.shiroweb.entity.SysDept;
import com.zq.shiroweb.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 是的
 * Created by Archar on 2018/1/5.
 */
@Service
public class SysTreeService {

    @Autowired
    private SysDeptDao sysDeptDao;

    @Autowired
    private SysAclModuleDao sysAclModuleDao;

    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptDao.findAll();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtoList) {
        if (CollectionUtils.isEmpty(deptLevelDtoList)) {
            return  Lists.newArrayList();
        }
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelDtoList) {
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        rootList.sort(deptSeqComparator);
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        for (DeptLevelDto deptLevelDto : deptLevelList) {
            //遍历该层的元素
            //处理当前层级的数据
            String newLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(newLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                //排序
                tempDeptList.sort(deptSeqComparator);
                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入到下一层处理
                transformDeptTree(tempDeptList, newLevel, levelDeptMap);
            } else {
                deptLevelDto.setDeptList(null);
            }
        }
    }

    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleDao.findAll();
        List<AclModuleLevelDto> dtoList = Lists.newLinkedList();

        aclModuleList.forEach(sysAclModule -> {
            dtoList.add(AclModuleLevelDto.adapt(sysAclModule));
        });
        return aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> aclModuleLevelDtoList) {
        if (CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
            return Lists.newArrayList();
        }

        Multimap<String, AclModuleLevelDto> levelMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        for (AclModuleLevelDto dto : aclModuleLevelDtoList) {
            levelMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        rootList.sort(Comparator.comparingInt(SysAclModule::getSeq));
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelMap);
        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDto> levelList, String level, Multimap<String, AclModuleLevelDto> levelMap) {
        for (AclModuleLevelDto levelDto : levelList) {
            //遍历该层的元素
            //处理当前层级的数据
            String newLevel = LevelUtil.calculateLevel(level, levelDto.getId());
            //处理下一层
            List<AclModuleLevelDto> tempDeptList = (List<AclModuleLevelDto>) levelMap.get(newLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                //排序
                tempDeptList.sort(Comparator.comparingInt(SysAclModule::getSeq));
                //设置下一层
                levelDto.setAclModuleList(tempDeptList);
                //进入到下一层处理
                transformAclModuleTree(tempDeptList, newLevel, levelMap);
            } else {
                levelDto.setAclModuleList(null);
            }
        }
    }

    private Comparator<DeptLevelDto> deptSeqComparator = Comparator.comparingInt(SysDept::getSeq);
}
