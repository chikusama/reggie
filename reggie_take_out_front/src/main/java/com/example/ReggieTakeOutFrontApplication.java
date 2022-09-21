package com.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//扫描servlet类
@ServletComponentScan
@MapperScan("com.example.mapper")
@EnableTransactionManagement
@Slf4j
//开启redis缓存
@EnableCaching
public class ReggieTakeOutFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieTakeOutFrontApplication.class, args);
        log.info("瑞吉前端项目已启动");
        log.info("<======>");
    }

}
