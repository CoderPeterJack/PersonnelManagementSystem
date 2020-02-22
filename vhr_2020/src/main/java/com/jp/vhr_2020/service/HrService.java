package com.jp.vhr_2020.service;

import com.jp.vhr_2020.mapper.HrMapper;
import com.jp.vhr_2020.mapper.HrRoleMapper;
import com.jp.vhr_2020.model.Hr;
import com.jp.vhr_2020.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    @Autowired
    HrRoleMapper hrRoleMapper;
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

    public List<Hr> getAllHrs(String keywords) {
        //登录成功后信息保存在securityConfig中，
        //操作员管理时自己的信息不用展示，只展示其他hr的信息
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }

    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    //先删除在添加
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid,rids)==rids.length;
    }

    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }
}
