package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.param.DeptParam;
import com.zq.shiroweb.param.UserParam;
import com.zq.shiroweb.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Archar on 2018/1/11.
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    public JsonData saveUser(UserParam param) {
        if (param.getId() == null) {
            sysUserService.save(param);
        } else {
            sysUserService.update(param);
        }
        return JsonData.success("保存成功");
    }

    @RequestMapping("/userList.json")
    public JsonData userList(UserParam param, Integer pageIndex, Integer pageSize, String sortKey, String sortValue) {
        return JsonData.success(sysUserService.findUserList(param, pageIndex, pageSize, sortKey, sortValue));
    }

    @RequestMapping("/allUsers.json")
    public JsonData getAllUsers(Integer roleId) {
        return JsonData.success(sysUserService.findUsersWithRoleId());
    }

    @RequestMapping("/del.json")
    public JsonData delUser(Integer userId) {
        sysUserService.delUserWithId(userId);
        return JsonData.success("删除成功");
    }
}
