package com.wang.graduationproject.Controller;

import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Service.LoginService;
import com.wang.graduationproject.Service.UserService;
import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;
import com.wang.graduationproject.To.UserTo;
import com.wang.graduationproject.Utils.SM2Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @PassToken
    @PostMapping("sign")
    @ResponseBody
    public void sign(@RequestBody UserTo userTo){
        userService.sign(userTo);
        return ;
    }

    @PassToken
    @GetMapping("get_public_key")
    @ResponseBody
    public String getPublicKey(){
        return SM2Utils.getPublicKey();
    }
}
