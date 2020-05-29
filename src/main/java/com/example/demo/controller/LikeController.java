package com.example.demo.controller;

import com.example.demo.entity.Love;
import com.example.demo.model.RestResp;
import com.example.demo.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Controller
@RequestMapping("/system")
@Slf4j
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private AtomicInteger i = new AtomicInteger(0);


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
        Love test = new Love();
        test.setStatusId(100);
        threadPoolTaskExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                System.out.println("我接受到了：" + test.getStatusId());
                return  0;

            }
        });

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


    /**
     * 查询赞接口
     *
     * @param uid      用户Id

     * @return
     */
    @RequestMapping("/getLiked")
    @ResponseBody
    public RestResp getLiked(@RequestParam("uid") Integer uid,HttpServletRequest httpServletRequest) throws InterruptedException {
        int andAdd = i.getAndAdd(1);
        log.info("{}  getLiked id ={}  uid ={}",Thread.currentThread().getId(),andAdd,uid);


        Love love = new Love();
        love.setId(andAdd);
        love.setStatusId(uid);
        love.setLikeUser("随便");

      //  Thread.currentThread().sleep(10);

         // this.wait(1000);

            return new RestResp("success", "查询成功", love);
       }


}
