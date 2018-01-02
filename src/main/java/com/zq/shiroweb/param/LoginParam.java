package com.zq.shiroweb.param;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2018/1/2.
 */
@Setter
@Getter
@ToString
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    public String username;

    @NotBlank(message = "密码不能为空")
    public String password;

}
