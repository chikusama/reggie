package com.example.common;

import org.springframework.context.annotation.Configuration;

public class CustomerException extends  RuntimeException{

    //自定义异常

    public CustomerException(String message) {
        super(message);
    }
}
