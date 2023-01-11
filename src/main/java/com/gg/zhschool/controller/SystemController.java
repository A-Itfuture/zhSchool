package com.gg.zhschool.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gg.zhschool.pojo.Admin;
import com.gg.zhschool.pojo.LoginForm;
import com.gg.zhschool.pojo.Student;
import com.gg.zhschool.pojo.Teacher;
import com.gg.zhschool.service.AdminService;
import com.gg.zhschool.service.StudentService;
import com.gg.zhschool.service.TeacherService;
import com.gg.zhschool.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 系统全局的方法
 *
 * @author： wxh
 * @version：v1.0
 * @date： 2022/09/24 17:18
 */
@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;


    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public ResultBody updatePwd(@ApiParam("token") @RequestHeader("token") String token,
                            @ApiParam("旧密码") @PathVariable("oldPwd") String oldPwd,
                            @ApiParam("新密码") @PathVariable("newPwd") String newPwd){
        boolean yOn = JwtHelper.isExpiration(token);
        if(yOn){
            //token过期
            return ResultBody.fail().message("token失效!");
        }
        //通过token获取当前登录的用户id
        Long userId = JwtHelper.getUserId(token);
        //通过token获取当前登录的用户类型
        Integer userType = JwtHelper.getUserType(token);

        // 将明文密码转换为暗文
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        if(userType == 1){
            QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Admin admin = adminService.getOne(queryWrapper);
            if (null!=admin) {
                admin.setPassword(newPwd);
                adminService.saveOrUpdate(admin);
            }else{
                return ResultBody.fail().message("原密码输入有误！");
            }
        }else if(userType == 2){
            QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Student student = studentService.getOne(queryWrapper);
            if (null!=student) {
                student.setPassword(newPwd);
                studentService.saveOrUpdate(student);
            }else{
                return ResultBody.fail().message("原密码输入有误！");
            }
        }
        else if(userType == 3){
            QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Teacher teacher = teacherService.getOne(queryWrapper);
            if (null!=teacher) {
                teacher.setPassword(newPwd);
                teacherService.saveOrUpdate(teacher);
            }else{
                return ResultBody.fail().message("原密码输入有误！");
            }
        }
        return ResultBody.ok();
    }


    @ApiOperation("头像上传统一入口")
    @PostMapping("/headerImgUpload")
    public  ResultBody headerImageUpload(@ApiParam("文件二进制数据") @RequestPart("multipartFile") MultipartFile multipartFile){
        //使用UUID随机生成文件名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //生成新的文件名字
        String fileName = uuid.concat(multipartFile.getOriginalFilename());
        //生成文件的保存路径(实际生产环境这里会使用真正的文件存储服务器)
        String portraitPath ="F:\\IdeaProjects\\project\\zhSchool\\src\\main\\resources\\public\\upload\\".concat(fileName);
        String portraitPathTmp ="F:\\IdeaProjects\\project\\zhSchool\\target\\classes\\public\\upload\\".concat(fileName);
        //保存文件
        try {
            //multipartFile.transferTo(new File(portraitPath));
            multipartFile.transferTo(new File(portraitPathTmp));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String headerImg = "upload/"+fileName;
        return ResultBody.ok(headerImg);
    }
    
    @ApiOperation("通过Token获取用户信息")
    @GetMapping("/getInfo")
    public ResultBody getUserInfoByToken(HttpServletRequest request,@RequestHeader("token") String token){
        // 获取用户中请求的token
        // 检查token 是否过期 20H
        boolean isEX = JwtHelper.isExpiration(token);
        if(isEX){
            return ResultBody.build(null, ResultCodeEnum.TOKEN_ERROR);
        }

        // 解析token,获取用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        // 准备一个Map集合用于存响应的数据
        HashMap<String, Object> map = new HashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getById(userId.intValue());
                map.put("user",admin);
                map.put("userType",1);
                break;
            case 2:
                Student student = studentService.getById(userId.intValue());
                map.put("user",student);
                map.put("userType",2);
                break;
            case 3:
                Teacher teacher = teacherService.getById(userId.intValue());
                map.put("user",teacher);
                map.put("userType",3);
                break;
            default:break;

        }
        return ResultBody.ok(map);
    }
    
    @ApiOperation("登录请求验证")
    @PostMapping("/login")
    public ResultBody login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        // 获取用户提交的验证码和session域中的验证码
        HttpSession session = request.getSession();
        String systemVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if ("".equals(systemVerifiCode)) {
            //session过期，验证码超时
            return ResultBody.fail().message("验证码失效,请刷新后重试");
        }
        if (!loginVerifiCode.equalsIgnoreCase(systemVerifiCode)) {
            //验证码有误
            return ResultBody.fail().message("验证码有误,请刷新后重新输入");
        }
        // 验证码使用完毕,移除当前请求域中的验证码
        session.removeAttribute("verifiCode");

        // 准备一个Map集合,用户存放响应的信息
        Map<String, Object> map = new HashMap<>();
        // 根据用户身份,验证登录的用户信息
        switch (loginForm.getUserType()) {
            // 管理员身份
            case 1:
                try {
                    // 调用服务层登录方法,根据用户提交的LoginInfo信息,查询对应的Admin对象,找不到返回Null
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        // 登录成功,将用户id和用户类型转换为token口令,作为信息响应给前端
                        //用户类型和用户id转换为密文，以token响应给客户端
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或者密码有误!");
                    }
                    return ResultBody.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return ResultBody.fail().message(e.getMessage());
                }
            // 学生身份
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (null !=student){
                        map.put("token",JwtHelper.createToken(student.getId().longValue(),2));
                    }else {
                        throw new RuntimeException("用户名或者密码有误!");
                    }
                    return ResultBody.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return ResultBody.fail().message(e.getMessage());
                }
            // 教师身份
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if(null != teacher){
                        map.put("token",JwtHelper.createToken(teacher.getId().longValue(),3));
                    }else {
                        throw new RuntimeException("用户名或者密码有误!");
                    }
                    return ResultBody.ok(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultBody.fail().message(e.getMessage());
                }
            default:
            // 查无此用户,响应失败
            return ResultBody.fail().message("查无此用户");
        }
    }

    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取验证码字符串
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());

        //将验证码放在当前请求域
        request.getSession().setAttribute("verifiCode", verifiCode);

        //将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
