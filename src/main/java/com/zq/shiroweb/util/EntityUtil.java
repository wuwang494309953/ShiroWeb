package com.zq.shiroweb.util;

import com.google.common.collect.Lists;
import com.zq.shiroweb.dto.UserDto;
import com.zq.shiroweb.entity.SysUser;
import com.zq.shiroweb.exception.ParamException;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.List;

/**
 * Created by Archar on 2018/1/18.
 */
public class EntityUtil {

    public static <D, E> D of(E item, Class<D> clazz) {
        D dto;
        try {
            dto = clazz.newInstance();
            PropertyUtils.copyProperties(dto, item);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamException("SysUser转UserDto失败");
        }
        return dto;
    }

    public static <D, E> List<D> listOf(List<E> sysEntitys, Class<D> clazz) {
        List<D> result = Lists.newLinkedList();
        sysEntitys.forEach(item -> result.add(EntityUtil.of(item, clazz)));
        return result;
    }
}
