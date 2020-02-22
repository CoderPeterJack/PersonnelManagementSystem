package com.jp.vhr_2020.service;

import com.jp.vhr_2020.mapper.MenuMapper;
import com.jp.vhr_2020.mapper.MenuRoleMapper;
import com.jp.vhr_2020.model.Hr;
import com.jp.vhr_2020.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: vhr_2020
 * @description:
 * @author: CoderPengJiang
 * @create: 2020-01-18 11:22
 **/
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;
    public List<Menu> getMenusByHrId(){
        return menuMapper.getMenusByHrId(((Hr)SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal()).getId());
    }

    //获取角色的所有菜单
//    @Cacheable
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    //添加事务，删除和添加要一起
    //rid为需要删除权限的用户，mids为需要添加的权限
    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        //先删除选中二点rid
        menuRoleMapper.deleteByRid(rid);
        //再判断
        if (mids==null||mids.length==0){
            return true;
        }
        //再执行插入
        Integer result=menuRoleMapper.insertRecord(rid,mids);
        return result==mids.length;
    }
}
