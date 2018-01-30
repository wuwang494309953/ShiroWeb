package com.zq.shiroweb.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Archar on 2018/1/21.
 */
@Getter
@Setter
@ToString
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15, min = 2, message = "部门名称需要2-15个字符")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "权限模块展示顺序不可以为空")
    private Integer seq;

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer status;

    @Length(max = 150, message = "备注长度限制150字符")
    private String remark;
}
