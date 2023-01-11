package com.gg.zhschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.zhschool.pojo.LoginForm;
import com.gg.zhschool.pojo.Teacher;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:06
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 登录
     * @param loginForm
     * @return
     */
    public Teacher login(LoginForm loginForm);

    /**
     * 获取教师信息，分页
     * @param pageParam
     * @param teacher
     * @return
     */
    IPage<Teacher> getTeachersByOpr(Page<Teacher> pageParam, Teacher teacher);
}
