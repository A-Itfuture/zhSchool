package com.gg.zhschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.zhschool.mapper.ClazzMapper;
import com.gg.zhschool.pojo.Clazz;
import com.gg.zhschool.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:03
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    /**
     * 分页查询所有班级信息【带条件】
     *
     * @param clazzPage
     * @param clazz
     * @return
     */
    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzPage, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        if(clazz!=null){
            //年级名称
            String gradeName = clazz.getGradeName();
            if (!StringUtils.isEmpty(gradeName)){
                queryWrapper.eq("grade_name",gradeName);
            }

            //班级名称条件
            String clazzName = clazz.getName();
            if(!StringUtils.isEmpty(clazzName)){
                queryWrapper.like("name",clazzName);
            }

            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }
        Page<Clazz> page = baseMapper.selectPage(clazzPage, queryWrapper);
        return page;
    }

    /**
     * 获取所有clazz信息
     *
     * @return
     */
    @Override
    public List<Clazz> getClazzs() {
        return baseMapper.selectList(null);
    }
}
