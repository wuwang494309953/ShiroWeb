package com.zq.shiroweb.controller;

import com.zq.shiroweb.common.JsonData;
import com.zq.shiroweb.dto.DeptLevelDto;
import com.zq.shiroweb.param.DeptParam;
import com.zq.shiroweb.service.SysDeptService;
import com.zq.shiroweb.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Archar on 2018/1/5.
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/save.json")
    public JsonData saveDept(DeptParam param) {
        if (param.getId() == null) {
            sysDeptService.save(param);
        } else {
            sysDeptService.update(param);
        }
        return JsonData.success("保存成功");
    }

    @RequestMapping("/del.json")
    public JsonData delDept(Integer id) {
        sysDeptService.delDept(id);
        return JsonData.success("删除成功");
    }

    @RequestMapping("/tree.json")
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }
}
