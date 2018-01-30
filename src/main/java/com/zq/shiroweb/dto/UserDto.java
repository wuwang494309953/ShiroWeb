package com.zq.shiroweb.dto;

import com.google.common.collect.Lists;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.exception.ParamException;
import lombok.*;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;

    private String username;

    private String telephone;

    private String mail;

    private Integer deptId;

    private String deptLevel;

    private String deptName;

    private Integer status;

    private String remark;

    public static UserDto of(SysUser sysUser) {
        UserDto dto = new UserDto();
        try {
            PropertyUtils.copyProperties(dto, sysUser);
            dto.deptId = sysUser.getDept().getId();
            dto.deptLevel = sysUser.getDept().getLevel();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamException("SysUser转UserDto失败");
        }
        return dto;
    }

    public static List<UserDto> listOf(List<SysUser> sysUsers) {
        List<UserDto> result = Lists.newLinkedList();
        sysUsers.forEach(sysUser -> result.add(UserDto.of(sysUser)));
        return result;
    }

}
