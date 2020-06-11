package com.example.demo.service;


import com.example.demo.entity.Love;
import com.example.demo.entity.UserDto;
import com.example.demo.model.RestResp;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    public UserDto findUserById(Integer uid);



}
