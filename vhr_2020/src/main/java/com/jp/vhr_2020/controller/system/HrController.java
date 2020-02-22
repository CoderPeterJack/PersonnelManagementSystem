package com.jp.vhr_2020.controller.system;

import com.jp.vhr_2020.model.Hr;
import com.jp.vhr_2020.model.RespBean;
import com.jp.vhr_2020.model.Role;
import com.jp.vhr_2020.service.HrService;
import com.jp.vhr_2020.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: vhr_2020
 * @description: 操作员管理
 * @author: CoderPengJiang
 * @create: 2020-02-17 11:29
 **/
@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    HrService hrService;
    @Autowired
    RoleService roleService;

    @GetMapping("/")
//    keywords为搜索关键字
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
    }

    //修改用户的角色状态时创建的
    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
        if (hrService.updateHr(hr)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败");
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    //修改用户的角色
    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid,Integer[] rids){
        if (hrService.updateHrRole(hrid,rids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id){
        if (hrService.deleteHrById(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
