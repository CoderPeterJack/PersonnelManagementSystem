package com.jp.vhr_2020.converter;
import java.text.ParseException;
import	java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
//import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: vhr_2020
 * @description: 接收高级搜索前端传来的日期进行日期转换,将String转为Date
 * @author: CoderPengJiang
 * @create: 2020-02-28 09:06
 **/


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter implements Converter<String, Date> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String source) {
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//@Component
//public class DateConverter implements Converter<String,Date>{
//    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//    @Override
//    public Date convert(String s) {
//        try {
//            return simpleDateFormat.parse(s);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    @Override
//    public JavaType getInputType(TypeFactory typeFactory) {
//        return null;
//    }
//
//    @Override
//    public JavaType getOutputType(TypeFactory typeFactory) {
//        return null;
//    }
//}
