package com.glacierluo.platform.controllers;

import com.glacierluo.platform.classes.Json;
import com.glacierluo.platform.entity.User;
import com.glacierluo.platform.entity.UserRole;
import com.glacierluo.platform.repository.UserRepository;
import com.glacierluo.platform.repository.UserRoleRepository;
import com.glacierluo.platform.vo.ChangeUserPassword;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


//@RestController是@ResponseBody和@Controller的组合注解。
@RestController
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @GetMapping("/auth/login")
    public String loginPage(ModelMap model){
        model.addAttribute("loginProcessUrl","/auth/authorize");
        return "base-login";
    }



//    @GetMapping("/usersSheetExport")
//    public void userSheetExport(HttpServletRequest request, HttpServletResponse response){
//        ServletOutputStream out = null;
//        try {
//            out = response.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
//        String fileName = null;
//        fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
//                .getBytes(), StandardCharsets.UTF_8);
//        response.setContentType("multipart/form-data");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
//        Sheet sheet1 = new Sheet(1, 0, User.class);
//        sheet1.setSheetName("用户表");
//        List<List<String>> users = new ArrayList<>();
////        userRepository.findAll().forEach(users::add);
//        for (User user : userRepository.findAll()) {
////            List<String> temp = new ArrayList<>();
////            temp.add(all.next().toListString());
//            users.add(user.toListString());
//        }
//        writer.write0(users, sheet1);
//        writer.finish();
//        try {
//            if (out != null) {
//                out.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @PostMapping("/userSheetInport")
//    public @ResponseBody Json userSheetExport(HttpServletRequest request) throws IOException {
//        int len = request.getContentLength();
////        System.out.println(request.getContentType());
//        ServletInputStream inputStream = request.getInputStream();
//        byte[] buffer = new byte[len];
//        inputStream.read(buffer, 0, len);
//        InputStream input = new BufferedInputStream(new ByteArrayInputStream(buffer));
//        List<List> data = new ArrayList<>();
//        try{
//            ExcelReader excelReader = new ExcelReader(input, null,
//                    new AnalysisEventListener<List<String>>() {
//                        @Override
//                        public void invoke(List<String> object, AnalysisContext context) {
//                            System.out.println(
//                                    "当前sheet:" + context.getCurrentSheet().getSheetNo() + " 当前行：" + context.getCurrentRowNum()
//                                            + " data:" + object);
//                            data.add(object);
//                        }
//                        @Override
//                        public void doAfterAllAnalysed(AnalysisContext context) {
//                            data.remove(0);
//                        }
//                    });
//            excelReader.read();
//        } catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try {
//                inputStream.close();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//        return new Json(data.toString(), 200);
//    }


}
