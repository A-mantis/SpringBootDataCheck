package com.zero.check.controller;

import com.alibaba.fastjson.JSON;
import com.zero.check.query.User;
import com.zero.check.query.UserInfo;
import com.zero.check.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 14:57
 * @history: 1.2019/11/21 created by wei.wang
 */
@Slf4j
@RestController
@RequestMapping(value = "/check")
public class DataCheckController {

    @PostMapping(value = "/userValidPost")
    public Response userValidPost(@Valid @RequestBody UserInfo userInfo, BindingResult bindingResult) {
        try {
            //log.info("Response : {}", JSON.toJSONString(userInfo));
            //获取返回数据
            String result = "Hello " + userInfo.toString();
            //记录数据库日志
            this.insertLog();
            return Response.ok().setData(result);
        } catch (Exception ex) {
            //log.info("Error : {}", ex.getMessage());
            //记录数据库日志
            this.insertLog();
            return Response.error(ex.getMessage());
        }

    }

    private void insertLog() {
    }


    @GetMapping(value = "/userValidGet")
    public Response userValidGet(@Valid User user, BindingResult result) {
        return Response.ok().setData("Hello " + user.getUserName());
    }

    @PostMapping(value = "/testAspectUserValidPost")
    public Response testAspectQueryUserPost(@RequestBody UserInfo userInfo) {
        return Response.ok().setData("Hello " + userInfo.getId());
    }
}
