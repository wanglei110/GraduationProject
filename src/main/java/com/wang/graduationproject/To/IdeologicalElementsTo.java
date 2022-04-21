package com.wang.graduationproject.To;


import lombok.Data;
import java.time.LocalDateTime;

@Data

public class IdeologicalElementsTo {


    private Long id;
    private LocalDateTime createTime;

    /**
     *知识点
     * */
    private String knowledgePoint;

    /**
     *内容
     * */
    private String content;
    private Long userId;



}
