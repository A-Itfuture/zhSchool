package com.gg.zhschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.zhschool.mapper.TeacherMapper;
import com.gg.zhschool.pojo.LoginForm;
import com.gg.zhschool.pojo.Teacher;
import com.gg.zhschool.service.TeacherService;
import com.gg.zhschool.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:07
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    /**
     * 教师登录
     *
     * @param loginForm
     * @return
     */
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    /**
     * 获取教师信息，分页
     *
     * @param pageParam
     * @param teacher
     * @return
     */
    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> pageParam, Teacher teacher) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(teacher != null){
            //班级名称条件
            String clazzName = teacher.getClazzName();
            if (!StringUtils.isEmpty(clazzName)) {
                queryWrapper.eq("clazz_name",clazzName);
            }
            //教师名称条件
            String teacherName = teacher.getName();
            if(!StringUtils.isEmpty(teacherName)){
                queryWrapper.like("name",teacherName);
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }

        IPage<Teacher> page = baseMapper.selectPage(pageParam, queryWrapper);

        return page;
    }
}
