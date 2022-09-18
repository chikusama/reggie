package com.example.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

//获取全局异常
@RestControllerAdvice
public class GlobalException{

    //全局异常捕获,需要返回R对象
    @ExceptionHandler
    public  R<String> getException(SQLIntegrityConstraintViolationException exception){
        //获取异常的名称
        //该异常是Sql中unique键出现重复所导致的
        String message = exception.getMessage();
        //异常判断message中是否存在'duplicate entry' Duplicate entry
        if (message.contains("Duplicate entry")) {
            //如果存在按照" "进行切割
            String[] s = message.split(" ");
            //返回R对象
            return R.error(s[2]+"已存在");
        }
        return R.error("unknow error");
    }

    @ExceptionHandler
    public R<String> getCustomerException(CustomerException ex){
        return R.error(ex.getMessage());
    }

}
