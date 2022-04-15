package com.wang.graduationproject.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Dao.Token;
import com.wang.graduationproject.Repository.TokenRepository;
import com.wang.graduationproject.Utils.JwtUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;


import org.apache.commons.lang3.StringUtils;

//https://www.jianshu.com/p/e88d3f8151db
//https://segmentfault.com/a/1190000037597927

@Component
public class TokenInterceptor implements HandlerInterceptor {
//    @Autowired
//    UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        String token = request.getHeader("token");// 从 http 请求头中取出 token
        if (StringUtils.isBlank(token)) {
            // 2.从headers中获取
            token = request.getHeader("token");
        }
        if (StringUtils.isBlank(token)) {
            // 3.从请求参数获取
            token = request.getParameter("token");
        }
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        if(JwtUtils.verifyToken(token)==true){
            Token userToken=tokenRepository.findTokenByTokenAndExpireTimeAfter(token,LocalDateTime.now());
            if(!ObjectUtils.isEmpty(userToken)){
                return true;
            }
        }

//        if (StringUtils.isBlank(token)) {
            //输出响应流
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "403");
        jsonObject.put("message", "没有访问权限");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getOutputStream().write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        return false;
//        }
//        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
