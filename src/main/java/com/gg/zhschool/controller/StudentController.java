package com.gg.zhschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.zhschool.pojo.Student;
import com.gg.zhschool.service.StudentService;
import com.gg.zhschool.util.MD5;
import com.gg.zhschool.util.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:10
 */
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation("删除一个或者多个学生信息")
    @DeleteMapping("/delStudentById")
    public ResultBody delStudentById(
            @ApiParam("多个学生id的JSON") @RequestBody List<Integer> ids
    ){
        studentService.removeByIds(ids);
        return ResultBody.ok();
    }

    @ApiOperation("增加学生信息")
    @PostMapping("/addOrUpdateStudent")
    public ResultBody addOrUpdateStudent(@RequestBody Student student){
        //对学生的密码进行加密
        if (null == student.getId() || 0 == student.getId()) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        //保存学生信息进入数据库
        studentService.saveOrUpdate(student);
        return ResultBody.ok();
    }

    @ApiOperation("查询学生信息,分页带条件")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public ResultBody<IPage<Student>> getStudentsByOpr(
            @ApiParam("页码数") @PathVariable("pageNo")Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize")Integer pageSize,
            @ApiParam("查询条件转换后端数据模型") Student student
    ){
        // 准备分页信息封装的page对象
        Page<Student> page =new Page<>(pageNo,pageSize);
        // 获取分页的学生信息
        IPage<Student> iPage = studentService.getStudentByOpr(page, student);
        // 返回学生信息
        return ResultBody.ok(iPage);
    }
}
