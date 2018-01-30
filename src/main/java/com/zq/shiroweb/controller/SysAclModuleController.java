package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.param.AclModuleParam;
import com.zq.shiroweb.service.SysAclModuleService;
import com.zq.shiroweb.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Archar on 2018/1/21.
 */
@RestController
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService aclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/save.json")
    public JsonData saveAclModule(AclModuleParam param) {
        if (param.getId() == null) {
            aclModuleService.save(param);
        } else {
            aclModuleService.update(param);
        }

        return JsonData.success("保存成功");
    }

    @RequestMapping("/getAclModulesWithRoleId.json")
    public JsonData getAclModulesWithRoleId(Integer roleId) {
        return JsonData.success(aclModuleService.findAclModuleIdsWithRoleId(roleId));
    }

    @RequestMapping("/tree.json")
    public JsonData aclModuleTree() {
        return JsonData.success(sysTreeService.aclModuleTree());
    }
}
