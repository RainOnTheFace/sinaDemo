package com.example.demo.pullThread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.ApplicationContextProvider;
import com.example.demo.dto.LoveDto;
import com.example.demo.entity.Love;
import com.example.demo.redis.RedisCache;
import com.example.demo.service.LikeService;
import com.example.demo.utils.HttpClient;
import com.example.demo.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接受线程，模拟消息队列的接受端
 */
@Slf4j
public class SendHttpThread implements Runnable {

    private AtomicInteger j = new AtomicInteger(0);


    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SendHttpThread() {
        this.threadPoolTaskExecutor = ApplicationContextProvider.getBean(ThreadPoolTaskExecutor.class);
    }

    @Override
    public void run() {

        Long start =System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {

            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {

                        Map<String, Object> params = new HashMap<>();
                        int uid = j.getAndAdd(1);
                        params.put("uid", uid);

                        log.info("{} sent getLiked uid={}", Thread.currentThread().getId(), uid);
                        String returnMsg = HttpClientUtil.get("http://wqxin.com/system/getLiked?uid=" + uid);
                        JSONObject resp = JSONObject.parseObject(returnMsg);
                        JSONObject object = resp.getJSONObject("data");
                        log.info("{} received getLiked id={},uid={}，likeUser={}", Thread.currentThread().getId(), object.get("id"), object.get("statusId"), object.get("likeUser"));

                }
            });


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Long end =System.currentTimeMillis();
        log.info("累计耗时:{}",end-start);
    }

}
