package com.demo.miaoshademo.exception;

import com.demo.miaoshademo.common.Result;
import com.demo.miaoshademo.common.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 拦截异常
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        logger.error("exceptionHandler", e);
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return ResultGenerator.genFailResult(ex.getStatus().getMessage());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            /**
             * 打印堆栈信息
             */
            logger.error(String.format(msg, msg));
            return ResultGenerator.genFailResult(ex.getMessage());
        } else {
            return ResultGenerator.genFailResult("ERROR");
        }
    }
}
