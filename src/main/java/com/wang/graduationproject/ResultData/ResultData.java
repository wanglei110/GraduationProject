package com.wang.graduationproject.ResultData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wang.graduationproject.Enum.ReturnCode;
import lombok.Data;
import static com.wang.graduationproject.Utils.LocalDateUtils.getLocalDateTimeStr;

//https://juejin.cn/post/7026759863506042917#heading-3
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResultData<T> {
    /** 结果状态 ,具体状态码参见ResultData.java*/
    private int status;
    private String message;
    private T data;
//    private long timestamp ;
//
//    public ResultData (){
//        this.timestamp = System.currentTimeMillis();
//    }

    private String time;
    ResultData (){
        this.time = getLocalDateTimeStr();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }


    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

}
