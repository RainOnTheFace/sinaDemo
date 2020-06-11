package com.example.demo.controller;

import com.example.demo.entity.Love;
import com.example.demo.entity.UserDto;
import com.example.demo.model.RestResp;
import com.example.demo.service.LikeService;
import com.example.demo.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;





    @RequestMapping("/findUserById")
    @ResponseBody
    public RestResp findUserById(@RequestParam("uid") Integer uid, HttpServletRequest httpServletRequest) {
         log.info("findUserById={}",uid);
        UserDto user =userService.findUserById(uid);
        return new RestResp("success", "查询成功", user);

    }

}
