package com.jp.vhr_2020.controller.emp;
import java.io.File;
import	java.io.IOException;
import java.io.UnsupportedEncodingException;
import	java.util.List;

import com.jp.vhr_2020.model.*;
import com.jp.vhr_2020.service.*;
import com.jp.vhr_2020.utils.POIUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Native;
import java.util.List;

/**
 * @program: vhr_2020
 * @description:
 * @author: CoderPengJiang
 * @create: 2020-02-17 19:02
 **/
@RestController
@RequestMapping("employee/basic")
public class EmpBasicController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    NationService nationService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;
    @Autowired
    DepartmentService departmentService;
    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam
            (defaultValue = "10") Integer size, @RequestParam(value = "keyword",required = false) String keyword){
        return employeeService.getEmployeeByPage(page,size,keyword);
    }

    //添加员工资料
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if (employeeService.addEmp(employee)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败");
    }

    //加载民族
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }
    //加载政治面貌
    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }
    //加载职称
    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    //加载职位
    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    //查询最大的工号+1就是当前增加的工号
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
        RespBean respBean=RespBean.build().setStatus(200)
                .setObj(String.format("%08d",employeeService.maxWorkID()+1));
        return respBean;
    }

    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    //删除员工
    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id){
        if (employeeService.deleteEmpByEid(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if (employeeService.updateEmp(employee)==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    //导出文件
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() throws UnsupportedEncodingException {
        List<Employee> list=(List<Employee>)employeeService.getEmployeeByPage(null,null,null)
                .getData();
        return POIUtils.employee2Excel(list);
    }

//    //导入数据
//    @PostMapping("/import")
//    public RespBean importData(MultipartFile file) throws IOException {
//        List<Employee> list=POIUtils.excel2Employee(file.);
//        return RespBean.ok("上传成功");
//    }
}
