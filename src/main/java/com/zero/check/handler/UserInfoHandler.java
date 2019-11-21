package com.zero.check.handler;

import com.zero.check.exception.UserInfoException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 15:04
 * @history: 1.2019/11/21 created by wei.wang
 */
@RestControllerAdvice
public class UserInfoHandler {

    /**
     * 校验错误拦截处理
     *
     * @param e 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(UserInfoException.class)
    public Object handle(UserInfoException e) {
        return e.getR();
    }
}

