package com.gg.zhschool.pojo;

import lombok.Data;

/**登录表单信息
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 16:48
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
