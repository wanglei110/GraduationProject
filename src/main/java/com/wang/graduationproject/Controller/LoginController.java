package com.wang.graduationproject.Controller;

import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Service.LoginService;
import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;
import com.wang.graduationproject.To.UserTo;
import com.wang.graduationproject.Utils.SM2Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("login")
public class LoginController {

    @Resource
    LoginService loginService;

    @PassToken
    @PostMapping("login")
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

    @PostMapping("logout")
    @ResponseBody
    public void logout(@RequestBody UserTo userTo){
        loginService.logout(userTo.getId());
    }

    @GetMapping("test")
    @ResponseBody
    public void test(){
        loginService.test();
    }
}
