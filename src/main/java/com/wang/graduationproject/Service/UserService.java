package com.wang.graduationproject.Service;

import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.To.EditPwdTo;
import com.wang.graduationproject.To.QueryUserTo;
import com.wang.graduationproject.To.UserTo;

public interface UserService {

    boolean sign(UserTo userTo);

    boolean editPwd(EditPwdTo editPwdTo);

    boolean editUser(UserTo userTo);

    PaginationResult queryUserList(QueryUserTo queryUserTo);
}
