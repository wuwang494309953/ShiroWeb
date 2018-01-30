package com.zq.shiroweb.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zq.shiroweb.dao.SysAclDao;
import com.zq.shiroweb.dao.SysAclModuleDao;
import com.zq.shiroweb.dto.AclDto;
import com.zq.shiroweb.entity.SysAcl;
import com.zq.shiroweb.entity.SysAclModule;
import com.zq.shiroweb.exception.ParamException;
import com.zq.shiroweb.param.AclParam;
import com.zq.shiroweb.util.BeanValidator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.HTMLDocument;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Archar on 2018/1/21.
 */
@Service
public class SysAclService {

    @Autowired
    private SysAclDao aclDao;

    @Autowired
    private SysAclModuleDao aclModuleDao;

    @Transactional
    public void save(AclParam param) {
        BeanValidator.check(param);

        SysAcl sysAcl = SysAcl.builder()
                .name(param.getName())
//                .aclModuleId(param.getAclModuleId())
                .aclModule(aclModuleDao.findByIdEquals(param.getAclModuleId()))
                .url(param.getUrl())
                .type(param.getType())
                .status(param.getStatus())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();

        sysAcl.setCode(generateCode());
        sysAcl.setOperator("system"); //TODO:
        sysAcl.setOperateIp("localhost"); //TODO:
        sysAcl.setOperateTime(new Date());

        aclDao.save(sysAcl);
    }

    @Transactional
    public void update(AclParam param) {
        BeanValidator.check(param);

        SysAcl before = aclDao.findByIdEquals(param.getId());
        Preconditions.checkNotNull(before, "待更新权限不存在");

        try {
            PropertyUtils.copyProperties(before, param);
            before.setAclModule(aclModuleDao.findByIdEquals(param.getAclModuleId()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamException("更新权限属性失败");
        }
        before.setOperator("system"); //TODO:
        before.setOperateIp("localhost"); //TODO:
        before.setOperateTime(new Date());

        aclDao.save(before);
    }

    @Transactional
    public void delAcl(Integer aclId) {
        aclDao.deleteByIdEquals(aclId);
    }

    public Map<String, Object> findAclList(AclParam param, Integer pageIndex, Integer pageSize, String sortKey, String sortValue) {
        pageIndex = pageIndex == null ? 0 : pageIndex;
        pageSize = pageSize == null ? 10 : pageSize;
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize, sortWithStr(sortKey, sortValue));
        Specification<SysAcl> specification = new Specification<SysAcl>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page aclPage = aclDao.findAll(specification, pageRequest);

        Map<String, Object> result = Maps.newHashMap();
        result.put("total", aclPage.getTotalElements());
        result.put("acls", AclDto.listOf(aclPage.getContent()));
        return result;
    }

    private Sort sortWithStr(String sortKey, String sortValue) {
        if (StringUtils.isBlank(sortValue) || StringUtils.isBlank(sortKey)) {
            return new Sort(Sort.Direction.DESC, "operateTime");
        }
        if ("ascending".equals(sortValue)) {
            return new Sort(Sort.Direction.ASC, sortKey);
        }
        return new Sort(Sort.Direction.DESC, sortKey);
    }

    public List<AclDto> findAclWithModule(Integer aclModuleId) {
        return AclDto.listOf(aclDao.findAllByAclModule_IdEquals(aclModuleId));
    }

    private String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }
}
