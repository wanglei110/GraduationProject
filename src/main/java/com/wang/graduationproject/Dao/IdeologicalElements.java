package com.wang.graduationproject.Dao;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="ideological_elements")
public class IdeologicalElements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="if_deleted")
    private Boolean ifDeleted;

    @Column(name="create_time")
    private LocalDateTime createTime;

    /**
     *知识点
     * */
    @Column(name="knowledge_point")
    private String knowledgePoint;

    /**
     *内容
     * */
    @Column(name="content")
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



}
