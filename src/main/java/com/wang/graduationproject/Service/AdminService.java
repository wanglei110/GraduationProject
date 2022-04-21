package com.wang.graduationproject.Service;

import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;
import com.wang.graduationproject.To.UserTo;

public interface AdminService {

    boolean addUser(UserTo userTo);

    boolean auditSign(UserTo userTo);

}
