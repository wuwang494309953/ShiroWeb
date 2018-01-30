package com.zq.shiroweb.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zq.shiroweb.dao.SysAclDao;
import com.zq.shiroweb.dao.SysDeptDao;
import com.zq.shiroweb.dao.SysUserDao;
import com.zq.shiroweb.dto.UserDto;
import com.zq.shiroweb.entity.QSysDept;
import com.zq.shiroweb.entity.QSysUser;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.exception.ParamException;
import com.zq.shiroweb.param.UserParam;
import com.zq.shiroweb.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Archar on 2018/1/2.
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysDeptDao sysDeptDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SysAclDao sysAclDao;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        System.out.println("开始实例化JPAQueryFactory");
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Transactional
    public void save(UserParam param) {
        BeanValidator.check(param);

        SysUser user = SysUser.builder()
                .username(param.getUsername())
                .telephone(param.getTelephone())
                .mail(param.getMail())
                .dept(sysDeptDao.findByIdEquals(param.getDeptId()))
                .status(param.getStatus())
                .remark(param.getRemark())
                .build();

        user.setOperator("system"); //TODO:
        user.setOperateIp("localhost"); //TODO:
        user.setOperateTime(new Date());

        sysUserDao.save(user);
    }

    @Transactional
    public void update(UserParam param) {
        BeanValidator.check(param);

        SysUser before = sysUserDao.findByIdEquals(param.getId());
        Preconditions.checkNotNull(before, "待更新用户不存在");

        try {
            PropertyUtils.copyProperties(before, param);
            before.setDept(sysDeptDao.findByIdEquals(param.getDeptId()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamException("更新用户属性失败");
        }

        before.setOperator("system"); //TODO:
        before.setOperateIp("localhost"); //TODO:
        before.setOperateTime(new Date());

       sysUserDao.save(before);
    }

    public Map<String, Object> findUserList(UserParam param, Integer pageIndex, Integer pageSize, String sortKey, String sortValue) {
        pageIndex = pageIndex == null ? 0 : pageIndex;
        pageSize = pageSize == null ? 10 : pageSize;
        QSysUser qSysUser = QSysUser.sysUser;
        List<Predicate> predicates = new LinkedList<>();
        if (StringUtils.isNotEmpty(param.getUsername())) {
            predicates.add(qSysUser.username.like("%" + param.getUsername() + "%"));
        }
        if (StringUtils.isNotEmpty(param.getTelephone())) {
            predicates.add(qSysUser.telephone.like("%" + param.getTelephone() + "%"));
        }
        if (StringUtils.isNotEmpty(param.getMail())) {
            predicates.add(qSysUser.mail.like("%" + param.getMail() + "%"));
        }

        Predicate[] dslPredicates = new Predicate[predicates.size()];
        QueryResults<UserDto> fetchResults = queryFactory
                .select(
                    Projections.bean(
                        UserDto.class,
                        qSysUser.id,
                        qSysUser.username,
                        qSysUser.telephone,
                        qSysUser.mail,
                        qSysUser.dept.id.as("deptId"),
                        qSysUser.dept.level.as("deptLevel"),
                        qSysUser.dept.name.as("deptName"),
                        qSysUser.status,
                        qSysUser.remark)
                )
                .where(predicates.toArray(dslPredicates))
                .offset(pageIndex * pageSize)
                .limit(pageSize)
                .orderBy(orderOf(qSysUser, sortKey, sortValue))
                .from(qSysUser)
                .fetchResults();
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", fetchResults.getTotal());
        result.put("users", fetchResults.getResults());
        return result;
    }

    public List<UserDto> findUsersWithRoleId() {
        List<UserDto> userDtos = UserDto.listOf(sysUserDao.findAllByStatusEquals(0));
        return userDtos;
    }

    public SysUser findUserWithName(String username) {
        SysUser user = sysUserDao.findFirstByUsernameEquals(username);
        user.setRoleSet(user.getRoleSet());
        user.getRoleSet().forEach(sysRole -> {
            sysRole.getAclModuleSet().size();
            sysRole.getUserSet().size();
            sysRole.setAclModuleSet(sysRole.getAclModuleSet());
            sysRole.setUserSet(sysRole.getUserSet());
            sysRole.getAclModuleSet().forEach(sysAclModule -> {
                sysAclModule.getAclSet().size();
                sysAclModule.setAclSet(sysAclModule.getAclSet());
            });
        });
        return user;
    }

    @Transactional
    public void delUserWithId(Integer userId) {
        sysUserDao.deleteByIdEquals(userId);
    }

    /**
     * 生成querydsl需要的排序条件
     * @param qSysUser
     * @param sortKey
     * @param sortValue
     * @return
     */
    @SuppressWarnings("unchecked")
    private OrderSpecifier orderOf(QSysUser qSysUser, String sortKey, String sortValue) {
        if (StringUtils.isBlank(sortValue) || StringUtils.isBlank(sortKey)) {
            return qSysUser.operateTime.desc();
        }
        Field[] fields = qSysUser.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (sortKey.equals(field.getName())) {
                try {
                    if ("ascending".equals(sortValue)) {
                        return new OrderSpecifier(Order.ASC, (Expression) field.get(qSysUser));
                    } else {
                        return new OrderSpecifier(Order.DESC, (Expression) field.get(qSysUser));
                    }
                } catch (Exception e) {
                    throw new ParamException("排序条件生成失败");
                }
            }
        }
        return qSysUser.operateTime.desc();
    }
}
