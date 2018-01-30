package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.param.AclParam;
import com.zq.shiroweb.param.UserParam;
import com.zq.shiroweb.service.SysAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Archar on 2018/1/21.
 */
@RestController
@RequestMapping("/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclService aclService;

    @RequestMapping("/save.json")
    public JsonData save(AclParam param) {
        if (param.getId() == null) {
            aclService.save(param);
        } else {
            aclService.update(param);
        }
        return JsonData.success("保存成功");
    }

    @RequestMapping("/del.json")
    public JsonData del(Integer aclId) {
        aclService.delAcl(aclId);
        return JsonData.success("保存成功");
    }

    @RequestMapping("/aclList.json")
    public JsonData aclList(AclParam param, Integer pageIndex, Integer pageSize, String sortKey, String sortValue) {
        return JsonData.success(aclService.findAclList(param, pageIndex, pageSize, sortKey, sortValue));
    }

    @RequestMapping("/aclWithModule.json")
    public JsonData aclWithModule(Integer aclModuleId) {
        return JsonData.success(aclService.findAclWithModule(aclModuleId));
    }
}
