package com.wang.graduationproject.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.graduationproject.ResultData.ResultData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice     //拦截Controller方法的返回值, 统一处理返回值/响应体, 一般用于统一返回格式、加解密、签名等等
public class RestResponseAdvice implements ResponseBodyAdvice<Object> {
      @Autowired
      private ObjectMapper objectMapper;
      /**
      * 是否支持advice功能
      * true支持, false不支持
       */
      @Override
      public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
          return true;
   }

    /**
     * 处理返回的结果数据
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                    Class<? extends HttpMessageConverter<?>> aClass,
                                    ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
    if(o instanceof String){
        return objectMapper.writeValueAsString(ResultData.success(o));
    }
    if (o instanceof ResultData) {     // 对于已经是ResultData类型的结果不再封装, 直接返回
        return o;
    }

    return ResultData.success(o);
   }
}


