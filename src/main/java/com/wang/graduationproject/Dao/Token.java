package com.wang.graduationproject.Dao;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="create_time")
    private LocalDateTime createTime;

    @Column(name="expire_time")
    private LocalDateTime expireTime;

    @Column(name="token")
    private String token;

    @Column(name="user_id")
    private Long userId;


}
