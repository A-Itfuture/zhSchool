package com.gg.zhschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.zhschool.mapper.StudentMapper;
import com.gg.zhschool.pojo.LoginForm;
import com.gg.zhschool.pojo.Student;
import com.gg.zhschool.service.StudentService;
import com.gg.zhschool.util.MD5;
import org.springframework.stereotype.Service;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:05
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    /**
     * 学生登录
     *
     * @param loginForm
     * @return
     */
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    /**
     * 按条件查询学生信息【带分页】
     *
     * @param page
     * @param student
     * @return
     */
    @Override
    public IPage<Student> getStudentByOpr(Page<Student> pageParm, Student student) {
        QueryWrapper<Student> queryWrapper = null;
        if (student!=null){
            queryWrapper = new QueryWrapper<>();
            if (student.getClazzName() != null){
                queryWrapper.eq("clazz_name",student.getClazzName());
            }
            if (student.getName() != null) {
                queryWrapper.like("name", student.getName());
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }
        IPage<Student> iPage = baseMapper.selectPage(pageParm, queryWrapper);
        return iPage;
    }
}
