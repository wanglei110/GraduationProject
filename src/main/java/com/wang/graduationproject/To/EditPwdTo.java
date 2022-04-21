package com.wang.graduationproject.To;

import lombok.Data;

@Data
public class EditPwdTo {

    private Long id;
    private String userName;
    private String oldPwd;
    private String newPwd;

}
