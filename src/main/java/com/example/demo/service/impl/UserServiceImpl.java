package com.example.demo.service.impl;

import com.example.demo.dto.LoveDto;
import com.example.demo.entity.Love;
import com.example.demo.entity.UserDto;
import com.example.demo.mapper.LoveMapper;
import com.example.demo.model.RestResp;
import com.example.demo.redis.RedisCache;
import com.example.demo.service.LikeService;
import com.example.demo.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {



    /**
     * 使用Hystrix熔断器，当调用findUserById失败后，调用forbackFindUserById方法
     * @param
     * @return
     */
    @HystrixCommand(fallbackMethod = "forbackFindUserById")
    @Override
    public UserDto findUserById(Integer uid) {

        Random random= new Random();
       int i= random.nextInt(10);

       if(i/2==0){
           log.info("service findUserById={}" ,i);
           throw  new NullPointerException("dddd");
       }

       ArrayList a=new ArrayList();
        UserDto user = new UserDto();
        user.setName("jon");
        user.setAge(10);
        user.setSex(0);
        return user;
    }


    public UserDto forbackFindUserById(Integer uid){
        log.info("forbackFindUserById");
        UserDto user = new UserDto();
        user.setName("tom");
        user.setAge(20);
        user.setSex(1);
        return  user;
    }

}
