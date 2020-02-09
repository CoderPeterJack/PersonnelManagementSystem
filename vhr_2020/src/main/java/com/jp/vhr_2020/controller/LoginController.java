package com.jp.vhr_2020.controller;

import com.jp.vhr_2020.model.RespBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: vhr_2020
 * @description: 登录提示
 * @author: CoderPengJiang
 * @create: 2020-01-01 23:11
 **/
@RestController
public class LoginController {
    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录，请登录");
    }
}
