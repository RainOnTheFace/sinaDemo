package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 设置线程池
 * User: 王庆新
 * Date: 2020/05/20
 * Time: 11:31
 * Description:
 **/
@Configuration
@EnableAsync
public class SpringThreadPoolExecutorConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(300);
        //核心线程池数
        pool.setCorePoolSize(10);
        //最大线程
        pool.setMaxPoolSize(20);
        //队列容量
        pool.setQueueCapacity(1000);
        //队列满，线程被拒绝执行策略
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        pool.setThreadNamePrefix("sinaDemo-service-");
        return pool;
    }
}
