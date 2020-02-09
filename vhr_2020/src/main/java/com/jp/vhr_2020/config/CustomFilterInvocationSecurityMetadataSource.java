package com.jp.vhr_2020.config;

import com.jp.vhr_2020.model.Menu;
import com.jp.vhr_2020.model.Role;
import com.jp.vhr_2020.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @program: vhr_2020
 * @description: 根据用户传来的请求地址，分析出请求需要的角色
 * @author: CoderPengJiang
 * @create: 2020-01-31 00:00
 **/
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    //Collection为当前请求所需要的角色
    //第一步：根据用户请求的url分析出当前需要哪些角色
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
         String requestUrl=((FilterInvocation)object).getRequestUrl();
        List<Menu>  menus=menuService.getAllMenusWithRole();
        for (Menu menu:menus) {
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles=menu.getRoles();
                String[] str=new String[roles.size()];
                for (int i = 0; i <roles.size() ; i++) {
                    str[i]=roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
        //没匹配，统一登入访问
        //ROLE_LOGIN只是个标记，下一步再判断
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
