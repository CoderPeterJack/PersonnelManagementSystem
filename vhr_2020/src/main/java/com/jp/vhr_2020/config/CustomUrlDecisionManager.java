package com.jp.vhr_2020.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @program: vhr_2020
 * @description: 第二🙅‍：判断当前用户是否具备第一步返回出来的角色
 * @author: CoderPengJiang
 * @create: 2020-02-09 12:58
 **/
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    //Authentication authentication当前登录用户的信息，用户的角色保持在这
    //Object object 请求对象
    //Collection<ConfigAttribute> collection 为第一步方法的返回值，需要的角色保持在这
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //拿出需要的角色
        for (ConfigAttribute configAttribute:collection) {
            //需要的角色
            String needRole=configAttribute.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)){
                //如果当前用户是匿名用户的实列，就抛异常
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登入，请登录");
                }else{
                    return;
                }
            }
            //获取当前登入用户的角色
            Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
            //登入的角色是否包含需要的角色
            for (GrantedAuthority authority:authorities) {
                if (authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足,请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
