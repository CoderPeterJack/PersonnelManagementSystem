package com.jp.vhr_2020.controller.system.basic;

import com.jp.vhr_2020.model.Department;
import com.jp.vhr_2020.model.RespBean;
import com.jp.vhr_2020.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @program: vhr_2020
 * @description: 部门,由于前端要一次性的拿到全部的json，所有数据
 * department的查询采用递归查询
 * @author: CoderPengJiang
 * @create: 2020-02-13 17:55
 **/
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @PostMapping("/")
    public RespBean addDep(@RequestBody Department dep){
        departmentService.addDep(dep);
        if (dep.getResult()==1){
            return RespBean.ok("添加成功",dep);
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id){
        Department dep=new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if (dep.getResult()==-2){
            return RespBean.error("该部门下有子部门，删除失败");
        }else if(dep.getResult()==-1){
            return RespBean.error("该部门下有员工，删除失败");
        }else if(dep.getResult()==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

