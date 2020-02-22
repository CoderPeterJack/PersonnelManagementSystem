package com.jp.vhr_2020.service;

import com.jp.vhr_2020.mapper.NationMapper;
import com.jp.vhr_2020.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: vhr_2020
 * @description: 民族业务层
 * @author: CoderPengJiang
 * @create: 2020-02-19 15:20
 **/
@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
