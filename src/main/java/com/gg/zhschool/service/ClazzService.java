package com.gg.zhschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.zhschool.pojo.Clazz;

import java.util.List;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:02
 */
public interface ClazzService extends IService<Clazz> {
    /**
     * 分页查询所有班级信息【带条件】
     * @param clazzPage
     * @param clazz
     * @return
     */
    IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzPage, Clazz clazz);

    /**
     * 获取所有clazz信息
     * @return
     */
    List<Clazz> getClazzs();
}
