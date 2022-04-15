package com.wang.graduationproject.Service.Impl;

import com.wang.graduationproject.Dao.Token;
import com.wang.graduationproject.Dao.User;
import com.wang.graduationproject.Repository.TokenRepository;
import com.wang.graduationproject.Repository.UserRepository;
import com.wang.graduationproject.Service.LoginService;
import com.wang.graduationproject.Service.UserService;
import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;
import com.wang.graduationproject.To.UserTo;
import com.wang.graduationproject.Utils.JwtUtils;
import com.wang.graduationproject.Utils.LocalDateUtils;
import com.wang.graduationproject.Utils.SM2Utils;
import com.wang.graduationproject.Utils.SM4Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;


    @Override
    public void sign(UserTo userTo) {
        //测试用
        if(userTo.getPwd().equalsIgnoreCase("123456")){
            userTo.setPwd(SM2Utils.encrypt("123456"));
        }

        if(ObjectUtils.isEmpty(userRepository.findUserByUserNameAndIfDeletedFalse(userTo.getUserName()))!=true){
            throw new RuntimeException("该用户名已存在！");
        }
        String MD5Pwd=SM2Utils.decrypt(userTo.getPwd());//解密获得密码的32位小写MD5值
        String SM4Pwd= SM4Utils.SM4Encrypt(MD5Pwd);//将密码的MD5值用SM4加密
        User user=new User();
        BeanUtils.copyProperties(userTo,user);
        user.setPwd(SM4Pwd);
        user.setEnable(false);
        userRepository.saveAndFlush(user);
    }
}
