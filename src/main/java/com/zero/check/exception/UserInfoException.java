package com.zero.check.exception;

import com.zero.check.utils.Response;
import lombok.Data;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 14:58
 * @history: 1.2019/11/21 created by wei.wang
 */
@Data
public class UserInfoException extends RuntimeException {
    private Response r;

    public UserInfoException(Response r) {
        this.r = r;
    }
}
