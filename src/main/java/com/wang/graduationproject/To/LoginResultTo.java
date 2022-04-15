package com.wang.graduationproject.To;

import lombok.Data;

@Data
public class LoginResultTo {

    private Long id;
    private String userName;
    private String role;
    private String name;
    private String academy;
    private String token;
}
