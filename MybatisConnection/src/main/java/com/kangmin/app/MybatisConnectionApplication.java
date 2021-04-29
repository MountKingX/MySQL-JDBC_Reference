package com.kangmin.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kangmin.app.mapper")
public class MybatisConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisConnectionApplication.class, args);
    }
}
