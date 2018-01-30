package com.zq.shiroweb.dto;

import com.google.common.collect.Lists;
import com.zq.shiroweb.entity.SysAcl;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.exception.ParamException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.List;

/**
 * Created by Archar on 2018/1/21.
 */
@Getter
@Setter
@ToString
public class AclDto {

    private Integer id;

    private String name;

    private Integer aclModuleId;

    private String aclModuleName;

    private String aclModuleLevel;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    public static AclDto of(SysAcl sysAcl) {
        AclDto dto = new AclDto();
        try {
            PropertyUtils.copyProperties(dto, sysAcl);
            dto.aclModuleId = sysAcl.getAclModule().getId();
            dto.aclModuleName = sysAcl.getAclModule().getName();
            dto.aclModuleLevel = sysAcl.getAclModule().getLevel();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamException("SysAcl转AclDto失败");
        }
        return dto;
    }

    public static List<AclDto> listOf(List<SysAcl> sysAcls) {
        List<AclDto> result = Lists.newLinkedList();
        sysAcls.forEach(sysAcl -> result.add(AclDto.of(sysAcl)));
        return result;
    }
}
