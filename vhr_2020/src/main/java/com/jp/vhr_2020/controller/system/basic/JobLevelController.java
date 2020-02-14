package com.jp.vhr_2020.controller.system.basic;

import com.jp.vhr_2020.model.JobLevel;
import com.jp.vhr_2020.model.RespBean;
import com.jp.vhr_2020.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @program: vhr_2020
 * @description: 职称管理接口
 * @author: CoderPengJiang
 * @create: 2020-02-11 12:11
 **/
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {
    @Autowired
    JobLevelService jobLevelService;
    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    //注解@RequestBody主要用来接收前端
    // 传递给后端的json字符串中的数据的(请求体中的数据的)；
    // GET方式无请求体，所以使用@RequestBody接收数据时，
    // 前端不能使用GET方式提交数据，而是用POST方式进行提交。
    public RespBean addJobLevel(@RequestBody  JobLevel jobLevel){
        if (jobLevelService.addJobLevel(jobLevel)==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @PutMapping("/")
    public RespBean updateJobLevelById(@RequestBody JobLevel jobLevel){
        if (jobLevelService.updateJobLevelById(jobLevel)==1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }


    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
        if (jobLevelService.deleteJobLevelById(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelsByIds(Integer[] ids){
        if (jobLevelService.deleteJobLevelsByIds(ids) == ids.length){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
