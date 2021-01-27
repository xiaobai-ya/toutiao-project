package com.heima.common.exception;

/**
 * @Author Administrator
 * @create 2021/1/27 10:01
 */

import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获处理类
 */

@Log4j2
@ControllerAdvice  //对控制器做切面处理
public class ExceptionCatch {


    /**
     *
     * @param e 被捕获的当前程序异常
     * @return
     */
    @ResponseBody  //返回前端json数据
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionCatch(Exception e){

        /*记录日志*/
        e.printStackTrace();
        log.error("GlobalException:{}",e.getMessage());

        return ResponseResult.errorResult(500,"服务器繁忙,请稍后再试...");
    }

}
