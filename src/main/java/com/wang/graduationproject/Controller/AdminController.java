package com.wang.graduationproject.Controller;

import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Service.AdminService;
import com.wang.graduationproject.To.UserTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Resource
    AdminService adminService;

    @PassToken
    @GetMapping("add_user")
    @ResponseBody
    public Boolean addUser(@RequestBody UserTo userTo){
        return adminService.addUser(userTo);
    }

    @PassToken
    @GetMapping("audit_sign")
    @ResponseBody
    public Boolean auditSign(@RequestBody UserTo userTo){

        return adminService.auditSign(userTo);
    }



}
