package com.jp.vhr_2020.utils;

import com.jp.vhr_2020.config.SecurityConfig;
import com.jp.vhr_2020.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @program: vhr_2020
 * @description: 获取当前用户对象的信息
 * @author: CoderPengJiang
 * @create: 2020-02-17 11:40
 **/
public class HrUtils {
    public static Hr getCurrentHr(){
        return (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
