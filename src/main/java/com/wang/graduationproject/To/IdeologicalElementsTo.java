package com.wang.graduationproject.To;


import lombok.Data;


@Data

public class IdeologicalElementsTo {


    private Long id;
    private String createTime;

    /**
     *知识点
     * */
    private String knowledgePoint;

    /**
     *内容
     * */
    private String content;

    private UserTo userTo;



}
