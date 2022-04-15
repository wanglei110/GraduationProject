package com.wang.graduationproject.Service.Impl;

import com.wang.graduationproject.Dao.Token;
import com.wang.graduationproject.Dao.User;
import com.wang.graduationproject.Repository.TokenRepository;
import com.wang.graduationproject.Repository.UserRepository;
import com.wang.graduationproject.Service.AdminService;
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
public class AdminServiceImpl implements AdminService {

    @Resource
    private UserRepository userRepository;



}
