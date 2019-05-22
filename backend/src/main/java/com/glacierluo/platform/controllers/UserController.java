package com.glacierluo.platform.controllers;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.glacierluo.platform.classes.Json;
import com.glacierluo.platform.entity.User;
import com.glacierluo.platform.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

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
}
