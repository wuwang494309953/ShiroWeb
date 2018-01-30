package com.zq.shiroweb.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Archar on 2018/1/18.
 */
@Getter
@Setter
@ToString
public class RoleParam {

    private Integer id;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @NotNull(message = "角色状态不能为空")
    private Integer status;

    @Length(max = 150, message = "备注长度限制150字符")
    private String remark;
}
