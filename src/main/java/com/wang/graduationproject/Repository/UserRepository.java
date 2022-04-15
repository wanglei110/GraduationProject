package com.wang.graduationproject.Repository;

import com.wang.graduationproject.Dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserNameAndPwdAndIfDeletedFalse(String userName,String pwd);

    User findUserByUserNameAndIfDeletedFalse(String userName);
}
