package com.gg.zhschool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.zhschool.pojo.Grade;

import java.util.List;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:04
 */
public interface GradeService extends IService<Grade> {
    /**
     * 年级分页查询
     * @param page
     * @param gradeName
     * @return
     */
    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);

    /**
     * 获取所有年级
     * @return
     */
    List<Grade> getGrades();

}
