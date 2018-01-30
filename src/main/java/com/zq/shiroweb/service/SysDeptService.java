package com.zq.shiroweb.service;

import com.google.common.base.Preconditions;
import com.zq.shiroweb.dao.SysDeptDao;
import com.zq.shiroweb.entity.SysDept;
import com.zq.shiroweb.exception.ParamException;
import com.zq.shiroweb.param.DeptParam;
import com.zq.shiroweb.util.BeanValidator;
import com.zq.shiroweb.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Archar on 2018/1/5.
 */
@Service
public class SysDeptService {

    @Autowired
    private SysDeptDao sysDeptDao;

    @Transactional
    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept dept = SysDept.builder().name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();

        dept.setLevel(LevelUtil.calculateLevel(
                getLevel(param.getParentId()),
                param.getParentId()
                )
        );

        dept.setOperator("system"); //TODO:
        dept.setOperateIp("localhost"); //TODO:
        dept.setOperateTime(new Date());

        sysDeptDao.save(dept);
    }

    @Transactional
    public void delDept(Integer id) {
        SysDept dept = sysDeptDao.findByIdEquals(id);
        Preconditions.checkNotNull(dept, "待删除的部门不存在，无法删除");
        if (sysDeptDao.countByParentIdEquals(dept.getId()) > 0) {
            throw new ParamException("当前部门下面有子部门，无法删除");
        }
        sysDeptDao.deleteSysDeptByIdEquals(id);
    }

    @Transactional
    public void update(DeptParam param) {
        BeanValidator.check(param);
        SysDept before = sysDeptDao.findByIdEquals(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        SysDept after = SysDept.builder()
                .id(param.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        after.setLevel(LevelUtil.calculateLevel(
                getLevel(param.getParentId()),
                param.getParentId()
                )
        );

        after.setOperator("system"); //TODO:
        after.setOperateIp("localhost"); //TODO:
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    @Transactional
    private void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptDao.findAllByLevelLike(before.getLevel() + "." + before.getId() + "%");
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                    sysDeptDao.save(dept);
                }
            }
        }
        sysDeptDao.save(after);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptDao.countByNameEqualsAndAndIdEqualsAndParentIdEquals(deptName, deptId, parentId) > 0;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptDao.findByIdEquals(deptId);
        if (dept == null) {
            return null;
        } else {
            return dept.getLevel();
        }
    }
}
