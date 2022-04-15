package com.wang.graduationproject.Service.Impl;

import com.wang.graduationproject.Dao.Token;
import com.wang.graduationproject.Dao.User;
import com.wang.graduationproject.Repository.TokenRepository;
import com.wang.graduationproject.Repository.UserRepository;
import com.wang.graduationproject.Service.LoginService;
import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;
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
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TokenRepository tokenRepository;

    @Override
    public LoginResultTo login(LoginTo loginTo) {

        //测试用
        if(loginTo.getPwd().equalsIgnoreCase("123456")){
            loginTo.setPwd(SM2Utils.encrypt("123456"));
        }
        String MD5Pwd=SM2Utils.decrypt(loginTo.getPwd());//解密获得密码的32位小写MD5值
        String SM4Pwd= SM4Utils.SM4Encrypt(MD5Pwd);//将密码的MD5值用SM4加密
        User user=userRepository.findUserByUserNameAndPwdAndIfDeletedFalse(loginTo.getUserName(),SM4Pwd);
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户名或密码错误！");
        }
        LoginResultTo loginResultTo=new LoginResultTo();
        BeanUtils.copyProperties(user,loginResultTo);
        loginResultTo.setToken(createToken(loginResultTo.getId()));
        return loginResultTo;
    }

    @Override
    public void logout(long userId) {
        tokenRepository.deleteAllByUserId(userId);
    }

    @Override
    public void test() {
        return;
    }

    public String createToken(long id){
        Token token=new Token();
        token.setToken(JwtUtils.createToken(String.valueOf(id)));
        token.setCreateTime(LocalDateTime.now());
        token.setExpireTime(LocalDateUtils.plus(LocalDateTime.now(),30, ChronoUnit.MINUTES));
        token.setUserId(id);
        tokenRepository.saveAndFlush(token);
        return token.getToken();
    }

}
