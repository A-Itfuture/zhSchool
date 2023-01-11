package com.gg.zhschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.zhschool.mapper.GradeMapper;
import com.gg.zhschool.pojo.Grade;
import com.gg.zhschool.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:04
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    /**
     * 年级分页查询
     *
     * @param pageParam
     * @param gradeName
     * @return
     */
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName) {
        // 设置查询条件
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        // 设置排序规则
        queryWrapper.orderByDesc("id");
        queryWrapper.orderByAsc("name");
        // 分页查询数据
        Page<Grade> gradePage = baseMapper.selectPage(pageParam, queryWrapper);

        return gradePage;
    }

    /**
     * 获取所有年级
     *
     * @return
     */
    @Override
    public List<Grade> getGrades() {
        List<Grade> grades = baseMapper.selectList(null);
        return grades;
    }
}
