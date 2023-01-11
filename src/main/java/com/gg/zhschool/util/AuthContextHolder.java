package com.gg.zhschool.util;

import javax.servlet.http.HttpServletRequest;

/**解析request请求中的 token口令的工具
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 14:16
 */
public class AuthContextHolder {
    /**
     * 从请求头token获取userid
     * @param request
     * @return
     */
    public static Long getUserIdToken(HttpServletRequest request){
        //从请求头获取token
        String token = request.getHeader("token");
        //调用工具类
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }
    /**
     * 从请求头token获取userType
     * @param request
     * @return
     */
    public static Integer getUserNameToken(HttpServletRequest request){
        //从请求头获取token
        String token = request.getHeader("token");
        //调用工具类
        Integer userType = JwtHelper.getUserType(token);
        return userType;
    }


}
