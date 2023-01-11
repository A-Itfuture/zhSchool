package com.gg.zhschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.zhschool.pojo.Clazz;
import com.gg.zhschool.pojo.Grade;
import com.gg.zhschool.service.ClazzService;
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
@Api("班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @ApiOperation("获取所有Clazz信息")
    @GetMapping("/getClazzs")
    public ResultBody getGrades(){
        List<Clazz> clazzs = clazzService.getClazzs();
        return ResultBody.ok(clazzs);
    }

    @ApiOperation("删除一个或者多个班级信息")
    @DeleteMapping("/deleteClazz")
    public ResultBody deleteClazzByIds(@ApiParam("多个班级id的JSON") @RequestBody List<Integer>ids){
        clazzService.removeByIds(ids);
        return  ResultBody.ok();
    }

    @ApiOperation("保存或者修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public ResultBody saveOrUpdateClazz(@ApiParam("JSON转换后端Clazz数据模型") @RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return ResultBody.ok();
    }

    @ApiOperation("查询班级信息,分页带条件")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public ResultBody getClazzsByOpr(
            @ApiParam("页码数")  @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小")  @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Clazz clazz
    ){
        //设置分页条件
        Page<Clazz> clazzPage = new Page<>(pageNo,pageSize);
        IPage<Clazz> iPage = clazzService.getClazzsByOpr(clazzPage,clazz);
        return ResultBody.ok(iPage);
    }
}
