package com.jp.vhr_2020.model;

import java.util.List;

/**
 * @program: vhr_2020
 * @description: 分页类
 * @author: CoderPengJiang
 * @create: 2020-02-17 18:59
 **/
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
