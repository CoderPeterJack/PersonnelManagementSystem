package com.jp.vhr_2020.service;

import com.jp.vhr_2020.mapper.HrMapper;
import com.jp.vhr_2020.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: vhr_2020
 * @description: hr业务层
 * @author: CoderPengJiang
 * @create: 2020-01-01 21:19
 **/
@Service
public class HrService implements UserDetailsService {
    @Autowired
    HrMapper hrMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr=hrMapper.loadUserByUsername(username);
        if (hr==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //给用户赋值,默认用户登入时时没有角色的
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }
}
