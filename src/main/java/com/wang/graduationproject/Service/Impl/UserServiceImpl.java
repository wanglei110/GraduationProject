package com.wang.graduationproject.Service.Impl;

import com.wang.graduationproject.Dao.Token;
import com.wang.graduationproject.Dao.User;
import com.wang.graduationproject.Enum.RoleEnum;
import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.Repository.TokenRepository;
import com.wang.graduationproject.Repository.UserRepository;
import com.wang.graduationproject.Service.LoginService;
import com.wang.graduationproject.Service.UserService;
import com.wang.graduationproject.To.*;
import com.wang.graduationproject.Utils.JwtUtils;
import com.wang.graduationproject.Utils.LocalDateUtils;
import com.wang.graduationproject.Utils.SM2Utils;
import com.wang.graduationproject.Utils.SM4Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanglei
 * */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TokenRepository tokenRepository;

    /**
     * 注册账号
     * */
    @Override
    public boolean sign(UserTo userTo) {
        //测试用
        if(userTo.getPwd().equalsIgnoreCase("123456")){
            userTo.setPwd(SM2Utils.encrypt("123456"));
        }

        if(ObjectUtils.isEmpty(userRepository.findUserByUserNameAndIfDeletedFalse(userTo.getUserName()))!=true){
            throw new RuntimeException("该用户名已存在！");
        }
        String SM4Pwd= processPwd(userTo.getPwd());
        User user=new User();
        BeanUtils.copyProperties(userTo,user);
        user.setPwd(SM4Pwd);
        user.setEnable(false);
        userRepository.saveAndFlush(user);
        return true;
    }

    /**
     * 修改密码
     * */
    @Override
    public boolean editPwd(EditPwdTo editPwdTo) {
        if(editPwdTo.getOldPwd().equals(editPwdTo.getOldPwd())){
            throw new RuntimeException("旧密码不能与新密码相同！");
        }
        User user=userRepository.findUserByIdAndIfDeletedFalse(editPwdTo.getId());
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("该用户不存在!");
        }
        String processPwd=processPwd(editPwdTo.getOldPwd());
        if(!processPwd.equals(user.getPwd())){
            throw new RuntimeException("原密码错误!");
        }
        user.setPwd(processPwd(editPwdTo.getNewPwd()));
        userRepository.saveAndFlush(user);
        tokenRepository.deleteAllByUserId(user.getId());
        return true;
    }

    @Override
    public boolean editUser(UserTo userTo) {
        User user=userRepository.findUserByIdAndIfDeletedFalse(userTo.getId());
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("该用户不存在!");
        }
        BeanUtils.copyProperties(userTo,user);
        userRepository.saveAndFlush(user);
        return true;
    }

    /**
     * 分页查询用户信息
     * */
    @Override
    public PaginationResult queryUserList(QueryUserTo queryUserTo) {
        Page<User> queryResult=userRepository.findAll(
                (Specification<User>)(root, query, CriteriaBuilder) ->{
                    List<Predicate> predicateAnd=new ArrayList<>();

                    predicateAnd.add(CriteriaBuilder.equal(root.get("ifDeleted"),false));
                    Predicate[] arrayAnd=new Predicate[predicateAnd.size()];
                    Predicate preAnd=CriteriaBuilder.and(predicateAnd.toArray(arrayAnd));
                    if(queryUserTo.getOr()==true){
                        List<Predicate> predicatesOr = new ArrayList<>();
                        if(queryUserTo.getName()!=null){
                            predicatesOr.add(CriteriaBuilder.like(root.get("name"),"%"+queryUserTo.getName()+"%"));
                        }
                        if(queryUserTo.getAcademy()!=null){
                            predicatesOr.add(CriteriaBuilder.like(root.get("academy"),"%"+queryUserTo.getName()+"%"));
                        }
                        if(queryUserTo.getEnable()!=null){
                            predicatesOr.add(CriteriaBuilder.equal(root.get("enable"),queryUserTo.getEnable()));
                        }
                        Predicate[] arrayOr=new Predicate[predicatesOr.size()];
                        Predicate preOr=CriteriaBuilder.or(predicatesOr.toArray(arrayOr));
                        return query.where(preAnd,preOr).getRestriction();
                    }else{
                        return query.where(preAnd).getRestriction();
                    }
                },
                PageRequest.of((queryUserTo.getPage()-1), queryUserTo.getRows(), Sort.Direction.DESC,"id")
        );

        PaginationResult result=new PaginationResult();
        result.setTotal(queryResult.getTotalElements());
        result.setRows(queryResult.getContent().stream().map(this::getUserTo).collect(Collectors.toList()));
        return result;
    }

    /**
     * 将User转成UserTO
     * */
    @Override
    public UserTo getUserTo(User user){
        UserTo userTo=new UserTo();
        userTo.setUserName(user.getUserName());
        userTo.setAcademy(user.getAcademy());
        userTo.setId(user.getId());
        userTo.setName(user.getName());
        //改
        userTo.setRole(RoleEnum.match(user.getRole()).getRoleName());
        return userTo;
    }

    /**
     * 将前端传来的加密过的密码的MD5值解密再用SM4进行加密
     * @param enMD5Pwd 前端传来的加密过的密码的MD5值
     * @return SM4加密后的密码
     * */
    public String processPwd(String enMD5Pwd){
        //解密获得密码的32位小写MD5值
        String MD5Pwd=SM2Utils.decrypt(enMD5Pwd);
        //将密码的MD5值用SM4加密
        return SM4Utils.SM4Encrypt(MD5Pwd);
    }

    public static void main(String[] args) {
        System.out.println(RoleEnum.match(1).getRoleName());
        System.out.println(RoleEnum.catchMessage("教学秘书").getRole());

    }
}
