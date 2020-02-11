package com.jp.vhr_2020.exception;

import com.jp.vhr_2020.model.RespBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @program: vhr_2020
 * @description: 全局异常统一处理
 * @author: CoderPengJiang
 * @create: 2020-02-10 18:31
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e){
        if (e instanceof MySQLIntegrityConstraintViolationException)
            return RespBean.error("该数据有关联数据，操作失败!");
        return RespBean.error("数据库异常，操作失败");
    }
}
