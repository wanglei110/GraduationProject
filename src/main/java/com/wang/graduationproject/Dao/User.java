package com.wang.graduationproject.Dao;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="if_deleted")
    private Boolean ifDeleted;

    @Column(name="create_time")
    private LocalDateTime createTime=LocalDateTime.now();

    @Column(name="user_name")
    private String userName;

    @Column(name="pwd")
    private String pwd;

    @Column(name="role")
    private Integer role;

    @Column(name="name")
    private String name;

    @Column(name="academy")
    private String academy;

    @Column(name="enable")
    private Boolean enable=false;
}
