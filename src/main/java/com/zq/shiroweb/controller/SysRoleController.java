package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.param.RoleParam;
import com.zq.shiroweb.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Archar on 2018/1/18.
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @RequestMapping("/save.json")
    public JsonData saveRole(RoleParam param) {
        if (param.getId() == null) {
            roleService.save(param);
        } else {
            roleService.update(param);
        }
        return JsonData.success("保存成功");
    }

    @RequestMapping("/del.json")
    public JsonData delRole(Integer roleId) {
        roleService.delRoleWithId(roleId);
        return JsonData.success("获取数据成功");
    }

    @RequestMapping("/changeUsers.json")
    public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds[]", required = false) Integer[] userIds) {
        roleService.changeUsers(roleId, userIds);
        return JsonData.success("保存成功");
    }

    @RequestMapping("/changeAcls.json")
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds[]", required = false) Integer[] aclIds) {
        roleService.changeAcls(roleId, aclIds);
        return JsonData.success("保存成功");
    }

    @RequestMapping("/getUsersWithRoleId.json")
    public JsonData getUsersWithRoleId(Integer roleId) {
        return JsonData.success(roleService.findUserIdsWithRoleId(roleId));
    }

    @RequestMapping("/getRoles.json")
    public JsonData findRoles() {
        return JsonData.success(roleService.findRoles(),"获取数据成功");
    }
}
