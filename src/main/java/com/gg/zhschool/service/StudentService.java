package com.gg.zhschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.zhschool.pojo.LoginForm;
import com.gg.zhschool.pojo.Student;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:05
 */
public interface StudentService extends IService<Student> {
    /**
     * 登录
     * @param loginForm
     * @return
     */
    Student login(LoginForm loginForm);

    /**
     * 按条件查询学生信息【带分页】
     * @param page
     * @param student
     * @return
     */
    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
