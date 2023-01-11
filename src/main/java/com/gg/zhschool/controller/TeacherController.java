package com.gg.zhschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.zhschool.pojo.Teacher;
import com.gg.zhschool.service.TeacherService;
import com.gg.zhschool.util.MD5;
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
@Api(tags = "教师信息管理控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @ApiOperation("删除一个或者多个教师信息")
    @DeleteMapping("/deleteTeacher")
    public ResultBody deleteTeacher(@ApiParam("教师id集合")@RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return ResultBody.ok();
    }

    @ApiOperation("添加和修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public ResultBody saveOrUpdateTeacher(@ApiParam("教师封装类型") @RequestBody Teacher teacher){
        if (null == teacher.getId() || 0 == teacher.getId()) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return ResultBody.ok();
    }

    @ApiOperation("获取教师信息,分页带条件")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public ResultBody getTeachers(
            @ApiParam("页数")@PathVariable("pageNo") Integer pageNo,
            @ApiParam("每页条数")@PathVariable("pageSize") Integer pageSize,
            @ApiParam("教师查询条件")Teacher teacher
    ){
        Page<Teacher> pageParam = new Page<>(pageNo,pageSize);
        IPage<Teacher> page = teacherService.getTeachersByOpr(pageParam, teacher);
        return ResultBody.ok(page);
    }
}
