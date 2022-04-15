package com.wang.graduationproject.Controller;

import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Service.LoginService;
import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;
import com.wang.graduationproject.Utils.SM2Utils;
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
    LoginService loginService;

    @PassToken
    @GetMapping("login")
    @ResponseBody
    public LoginResultTo login(@RequestBody LoginTo loginTo){
        return loginService.login(loginTo);
    }

    @PassToken
    @GetMapping("get_public_key")
    @ResponseBody
    public String getPublicKey(){
        return SM2Utils.getPublicKey();
    }
    ////
}
