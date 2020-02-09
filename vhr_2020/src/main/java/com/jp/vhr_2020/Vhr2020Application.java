package com.jp.vhr_2020;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan(basePackages = "com.jp.vhr_2020.mapper")
public class Vhr2020Application {

    public static void main(String[] args) {
        SpringApplication.run(Vhr2020Application.class, args);
    }

}
