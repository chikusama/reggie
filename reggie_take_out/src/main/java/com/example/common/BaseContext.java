package com.example.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 提供ThreadLocal方法,来获取当前用户的id
 */
public class BaseContext {

    private static final ThreadLocal<Object> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void setThreadLocal(Object id){
        THREAD_LOCAL.set(id);
    }
    public static Object getThreadLocal(){
        return THREAD_LOCAL.get();
    }

}
