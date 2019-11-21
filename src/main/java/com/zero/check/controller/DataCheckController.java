package com.zero.check.controller;

import com.zero.check.query.User;
import com.zero.check.query.UserInfo;
import com.zero.check.utils.Response;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 14:57
 * @history: 1.2019/11/21 created by wei.wang
 */
@RestController
@RequestMapping(value = "/check")
public class DataCheckController {

    @PostMapping(value = "/userValidPost")
    public Response queryUserPost(@Valid @RequestBody UserInfo userInfo, BindingResult result) {
        return Response.ok().setData("Hello " + userInfo.getId());
    }

    @GetMapping(value = "/userValidGet")
    public Response queryUserGet(@Valid User user, BindingResult result) {
        return Response.ok().setData("Hello " + user.getUserName());
    }
}
