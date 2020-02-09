package com.jp.vhr_2020.controller;

import com.jp.vhr_2020.model.Menu;
import com.jp.vhr_2020.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: vhr_2020
 * @description:系统配置controller
 * @author: CoderPengJiang
 * @create: 2020-01-18 11:12
 **/
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
//    一定一定不要用前端传过来的用户id，因为前端的数据不可信，后端同样要校验
    @Autowired
    MenuService menuService;
    @GetMapping("/menu")
    public List<Menu> getMenusByHrId(){
        return menuService.getMenusByHrId();
    }
}
