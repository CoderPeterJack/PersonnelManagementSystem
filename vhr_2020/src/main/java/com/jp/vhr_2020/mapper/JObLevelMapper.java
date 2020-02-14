package com.jp.vhr_2020.mapper;

import com.jp.vhr_2020.model.JobLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobLevel record);

    int insertSelective(JobLevel record);

    JobLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobLevel record);

    int updateByPrimaryKey(JobLevel record);

    List<JobLevel> getAllJobLevels();
//此时JobLevelMapper.xml中可以引用ids这个数据，同过#{}或者${}
    Integer deleteJobLevelsByIds(@Param("ids") Integer[] ids);
}