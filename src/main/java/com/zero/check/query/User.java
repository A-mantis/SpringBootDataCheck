package com.zero.check.query;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 16:03
 * @history: 1.2019/11/21 created by wei.wang
 */
@Component
@Data
public class User {
    @NotBlank(message = "用户工号不能为空")
    private String userId;

    @NotBlank(message = "用户名称不能为空")
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
