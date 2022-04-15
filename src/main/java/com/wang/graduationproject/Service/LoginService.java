package com.wang.graduationproject.Service;

import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;

public interface LoginService {

    public LoginResultTo login(LoginTo loginTo);

    public void logout(long userId);

    public void test();
}
