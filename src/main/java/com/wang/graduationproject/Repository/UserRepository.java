package com.wang.graduationproject.Repository;

import com.wang.graduationproject.Dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findUserByUserNameAndPwdAndIfDeletedFalse(String userName,String pwd);

    User findUserByUserNameAndIfDeletedFalse(String userName);

    User findUserByIdAndIfDeletedFalse(Long id);
}
