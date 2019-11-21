package com.zero.check.query;

import lombok.Data;
import org.hibernate.validator.constraints.EAN;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 15:05
 * @history: 1.2019/11/21 created by wei.wang
 */
@Component
@Data
public class UserInfo {
    @NotBlank(message = "主键不能为空")
    @Pattern(regexp = "^[1-9]\\d*$",message = "主键范围不正确")
    private String id;

    @Valid
    @NotEmpty(message = "用户列表不能为空")
    private List<User> userList;

    @NotNull(message = "权限不能为空")
    @Min(value = 1, message = "权限范围为[1-99]")
    @Max(value = 99, message = "权限范围为[1-99]")
    private Long roleId;
}
