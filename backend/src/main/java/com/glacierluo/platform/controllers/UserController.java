package com.glacierluo.platform.controllers;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.glacierluo.platform.classes.Json;
import com.glacierluo.platform.entity.User;
import com.glacierluo.platform.entity.UserRole;
import com.glacierluo.platform.repository.UserRepository;
import com.glacierluo.platform.repository.UserRoleRepository;
import com.glacierluo.platform.vo.ChangeUserPassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Api(tags = {"用户信息管理"})
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name="user",value = "用户详细实体user",required = true,dataType = "User")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Json register (@RequestBody User user){
        if(user.checkNull()){
            userRepository.save(user);
            return new Json("Register success!", 200);
        }else{
            return new Json("Data not complete!", 400);
        }
    }

    @PostMapping("/updateProfile")  //TODO:注册与更新信息接口的修改
    public Json updateProfile (@RequestBody User user){
        if(user.checkNull()){
            User updated = userRepository.findById(user.getId()).orElse(null);
            if(updated == null){
                return new Json("Unknow User!", 400);
            }else{
                updated.updateUser(user);
                userRepository.save(updated);
            }
//            userRepository.save(userRepository.findById(user.getId()).orElse(null).updateUser(user));
            return new Json("Update success!", 200);
        }else{
            return new Json("Data not complete", 400);
        }
    }

    //TODO:用户删除接口

    @GetMapping("/usersSheetExport")
    public void userSheetExport(HttpServletRequest request, HttpServletResponse response){
        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(user);
        }
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = null;
        fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .getBytes(), StandardCharsets.UTF_8);
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
        ExportParams params = new ExportParams("用户",
                new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                        .getBytes(), StandardCharsets.UTF_8));
        Workbook workbook = ExcelExportUtil.exportExcel(params, User.class, users);
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert out != null;
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/usersSheetImport")
    public Json userSheetExport(HttpServletRequest request) throws Exception {
        int len = request.getContentLength();
//        System.out.println(request.getContentType());
        ServletInputStream inputStream = null;
        inputStream = request.getInputStream();
        byte[] buffer = new byte[len];
        inputStream.read(buffer, 0, len);
        InputStream input = new BufferedInputStream(new ByteArrayInputStream(buffer));
//        InputStream input = new ByteArrayInputStream(buffer);
        ZipSecureFile.setMinInflateRatio(-1.0d);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<User> data = new ArrayList<>();
        data = ExcelImportUtil.importExcel(input, User.class, params);
//            data = ExcelImportUtil.importExcel(input, User.class, params);
        return new Json(data.toString(), 200);
    }

    @GetMapping("/userInfo/userID/{userID}")
    public Optional<User> userInfo(@PathVariable Long userID){
        return userRepository.findById(userID);
    }

    @GetMapping("/userInfo/userName/{userName}")
    public User userInfo(@PathVariable String userName){
        return userRepository.findByName(userName);
    }


    @GetMapping("/allUser")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }


    @ApiOperation(value = "修改密码",notes = "修改用户密码")
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String userChangePassword(@RequestBody ChangeUserPassword user) throws Exception{
        JSONObject result=new JSONObject();
        if (user.getNewPassword().equals(user.getOldPassword())){
            result.put("code","200");
            result.put("msg","两个密码不能相同");
            return  result.toJSONString();
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        UserRole userRole=userRoleRepository.findByuserId(user.getUserId());
        if(user.getUserId() == null){
            result.put("code","200");
            result.put("msg","用户名不存在");
            return  result.toJSONString();
        }
        if (!bCryptPasswordEncoder.matches(user.getOldPassword(),userRole.getPassword())){
            result.put("code","200");
            result.put("msg","密码错误");
            return  result.toJSONString();
        }
        userRole.setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
        userRoleRepository.save(userRole);
        userRole=userRoleRepository.findByuserId(user.getUserId());
        if (bCryptPasswordEncoder.matches(user.getNewPassword(),userRole.getPassword())){

            result.put("code","200");
            result.put("msg","密码修改成功");
        }else {
            result.put("code","200");
            result.put("msg","密码修改失败，请重试");
        }
        return  result.toJSONString();
    }




}
