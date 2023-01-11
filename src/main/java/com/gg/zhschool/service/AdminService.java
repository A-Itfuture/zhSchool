package com.gg.zhschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.zhschool.pojo.Admin;
import com.gg.zhschool.pojo.LoginForm;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 16:55
 */
public interface AdminService extends IService<Admin> {
    /**
     * 登录
     * @param loginForm
     * @return
     */
    Admin login(LoginForm loginForm);

    /**
     * 获取管理员信息，分页
     * @param pageParam
     * @param adminName
     * @return
     */
    IPage<Admin> getAdmins(Page<Admin> pageParam, String adminName);
}
