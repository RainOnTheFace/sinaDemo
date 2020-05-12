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
@RequestMapping("/system")
public class LikeController {
    @Autowired
    private LikeService likeService;


    /**
     * 点赞
     *
     * @param uid      用户Id
     * @param statusId 微博Id
     * @return
     */
    @RequestMapping("/like")
    @ResponseBody
    public RestResp like(@RequestParam("uid") Integer uid, @RequestParam("statusId") Integer statusId, HttpServletRequest httpServletRequest) {


        return likeService.like(uid, statusId);
    }


    /**
     * 查询赞接口
     *
     * @param uid      用户Id
     * @param statusId 微博Id
     * @return
     */
    @RequestMapping("/isLiked")
    @ResponseBody
    public RestResp isLiked(@RequestParam("uid") Integer uid, @RequestParam("statusId") Integer statusId, HttpServletRequest httpServletRequest) {

        Boolean falg = likeService.isLiked(uid, statusId);
        return new RestResp("success", "查询成功", falg);

    }
}
