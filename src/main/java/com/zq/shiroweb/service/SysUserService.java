package com.zq.shiroweb.service;

import com.google.common.base.Preconditions;
import com.zq.shiroweb.dao.SysUserDao;
import com.zq.shiroweb.dto.UserDto;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.exception.ParamException;
import com.zq.shiroweb.param.UserParam;
import com.zq.shiroweb.util.BeanValidator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Archar on 2018/1/2.
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Transactional
    public void save(UserParam param) {
        BeanValidator.check(param);

        SysUser user = SysUser.builder()
                .username(param.getUsername())
                .telephone(param.getTelephone())
                .mail(param.getMail())
                .deptId(param.getDeptId())
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
            PropertyUtils.copyProperties(param, before);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamException("更新属性失败");
        }

        before.setOperator("system"); //TODO:
        before.setOperateIp("localhost"); //TODO:
        before.setOperateTime(new Date());

       sysUserDao.save(before);
    }

    public List<UserDto> findUserList(int pageIndex, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "createTime"));
        sysUserDao.findAll(new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new LinkedList<>();
//                if (StringUtils.isNotEmpty()) {
//                    predicates.add(criteriaBuilder.equal(root.get("businessId"), ));
//                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        }, pageRequest);

        return null;
    }
}
