package com.wang.graduationproject.To;

import lombok.Data;

@Data
public class QueryIdeoTo {
    private Integer page;
    private Integer rows;
    /**
     *知识点
     * */
    private String knowledgePoint;

    /**
     *内容
     * */
    private String content;

    private String userName;
    /**
     * 是否开启or查询
     */
    private Boolean Or;
}
