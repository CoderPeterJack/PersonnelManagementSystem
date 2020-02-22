package com.jp.vhr_2020.service;
import	java.lang.Double;

import com.jp.vhr_2020.mapper.EmployeeMapper;
import com.jp.vhr_2020.model.Employee;
import com.jp.vhr_2020.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.DatabaseMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: vhr_2020
 * @description:
 * @author: CoderPengJiang
 * @create: 2020-02-18 10:49
 **/
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    //计算合同期限
    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
    DecimalFormat decimalFormat=new DecimalFormat("##.00");

    public Integer deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public RespPageBean getEmployeeByPage(Integer page, Integer size,String keyword) {
        if (page!=null&&size!=null){
            //重新赋值
            page=(page-1)*size;
        }
        List<Employee> data=employeeMapper.getEmployeeByPage(page,size,keyword);
        Long total=employeeMapper.getTotal(keyword);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee) {
        //计算合同期限
        Date beginContract=employee.getBeginContract();
        Date endContract=employee.getEndContract();
        double month=(Double.parseDouble(yearFormat.format(endContract))-
                Double.parseDouble(yearFormat.format(beginContract)))*12+
                (Double.parseDouble(monthFormat.format(endContract))-
                        Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
        return employeeMapper.insertSelective(employee);
    }

    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
}
