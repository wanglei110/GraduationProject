package com.wang.graduationproject.To;

import lombok.Data;

@Data
public class QueryUserTo {
    private Integer page;
    private Integer rows;
    private Boolean enable;
    private String name;
    private String academy;
    /**
     * 是否开启or查询
     */
    private Boolean Or;
}
