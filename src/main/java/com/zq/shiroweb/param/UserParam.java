package com.zq.shiroweb.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/1/12.
 */
@Getter
@Setter
@ToString
public class UserParam {

    private Integer id;

    @NotBlank(message = "用户名不可以为空")
    private String username;

    @NotBlank(message = "电话号码不可以为空")
    private String telephone;

    private String mail;

    @NotNull(message = "用户部门不能为空")
    private Integer deptId;

    @NotNull(message = "用户状态不能为空")
    private Integer status;

    private String remark;
}
