package com.gg.zhschool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.zhschool.pojo.Admin;
import com.gg.zhschool.service.AdminService;
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
@Api(tags = "系统管理员控制器")
@RestController//不是Controller：@ResponseBody+@Controller，减少在每个方法上加@ResponseBody
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("删除Admin信息")
    @DeleteMapping("/deleteAdmin")
    public ResultBody deleteAdmin(@ApiParam("删除管理员的id集合")@RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return ResultBody.ok();
    }

    @ApiOperation("添加或修改Admin信息")
    @PostMapping("/saveOrUpdateAdmin")
    public ResultBody saveOrUpdateAdmin(@ApiParam("管理员的数据类型")@RequestBody Admin admin){
        if (null == admin.getId() || 0 == admin.getId()) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return ResultBody.ok();
    }

    @ApiOperation("分页获取所有Admin信息【带条件】")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public ResultBody getAllAdmin(@ApiParam("页数")@PathVariable Integer pageNo,
                                  @ApiParam("每页显示条数")@PathVariable Integer pageSize,
                                  @ApiParam("查询条件")String adminName){
        Page<Admin> pageParam = new Page<>(pageNo,pageSize);
        IPage<Admin> page = adminService.getAdmins(pageParam, adminName);
        return ResultBody.ok(page);
    }
}
