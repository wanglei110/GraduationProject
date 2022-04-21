package com.wang.graduationproject.Controller;

import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.Service.LoginService;
import com.wang.graduationproject.Service.UserService;
import com.wang.graduationproject.To.*;
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
    public Boolean sign(@RequestBody UserTo userTo){
        return userService.sign(userTo);
    }

    @PassToken
    @PostMapping("edit_pwd")
    @ResponseBody
    public Boolean editPwd(@RequestBody EditPwdTo editPwdTo){
        return userService.editPwd(editPwdTo);
    }

    @PassToken
    @PostMapping("query_user")
    @ResponseBody
    public PaginationResult queryUser(@RequestBody QueryUserTo queryUserTo){
        return userService.queryUserList(queryUserTo);
    }

    @PassToken
    @GetMapping("get_public_key")
    @ResponseBody
    public String getPublicKey(){
        return SM2Utils.getPublicKey();
    }
}
