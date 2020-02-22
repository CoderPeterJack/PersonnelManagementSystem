package com.jp.vhr_2020.service;

import com.jp.vhr_2020.mapper.PoliticsstatusMapper;
import com.jp.vhr_2020.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: vhr_2020
 * @description:
 * @author: CoderPengJiang
 * @create: 2020-02-19 15:30
 **/
@Service
public class PoliticsstatusService {
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
