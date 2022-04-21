package com.wang.graduationproject.Service;

import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;

public interface LoginService {

    LoginResultTo login(LoginTo loginTo);

    Boolean logout(long userId);

    void test();
}
