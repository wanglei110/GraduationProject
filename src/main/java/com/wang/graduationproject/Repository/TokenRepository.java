package com.wang.graduationproject.Repository;

import com.wang.graduationproject.Dao.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findTokenByTokenAndExpireTimeAfter(String token, LocalDateTime localDateTime);

    void deleteAllByUserId(Long id);
}
