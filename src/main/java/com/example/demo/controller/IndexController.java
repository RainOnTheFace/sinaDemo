package com.example.demo.controller;

import com.example.demo.model.RestResp;
import com.example.demo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class IndexController {



    @RequestMapping("/page1")
    public String toPage1(){
        return "demo1";
    }
    @RequestMapping("/page2")
    public String toPage2(){
        return "demo2";
    }



}
