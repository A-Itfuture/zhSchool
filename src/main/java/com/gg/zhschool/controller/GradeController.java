package com.gg.zhschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.zhschool.pojo.Grade;
import com.gg.zhschool.service.GradeService;
import com.gg.zhschool.util.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:10
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @ApiOperation("获取所有Grade信息")
    @GetMapping("/getGrades")
    public ResultBody getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return ResultBody.ok(grades);
    }

    @ApiOperation("删除一个或者多个grade信息")
    @DeleteMapping("/deleteGrade")
    public ResultBody deleteGradeById(
            @ApiParam("JSON的年级id集合,映射为后台List<Integer>") @RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return ResultBody.ok();
    }

    @ApiOperation("添加或者修改年级信息")
    @PostMapping("/saveOrUpdateGrade")
    public ResultBody saveOrUpdateGrade(@ApiParam("JSON的grade对象转换后台数据模型") @RequestBody Grade grade){
        //调用服务方法
        gradeService.saveOrUpdate(grade);
        return ResultBody.ok();
    }

    @ApiOperation("查询年级信息,分页带条件")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public ResultBody getGradeByOpr(
            @ApiParam("分页查询页码数") @PathVariable("pageNo") Integer pageNo,// 页码数
            @ApiParam("分页查询页大小") @PathVariable("pageSize") Integer pageSize,// 页大小
            @ApiParam("分页查询模糊匹配班级名") @RequestParam(value = "gradeName",defaultValue = "") String gradeName)// 模糊查询条件
    {
        //设置分页信息
        Page<Grade> page = new Page<>(pageNo, pageSize);
        // 调用服务层方法,传入分页信息,和查询的条件
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page,gradeName);

        //封装结果
        return ResultBody.ok(pageRs);

    }
}
