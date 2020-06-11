package com.example.demo;

import com.example.demo.pullThread.ReceiveThread;
import com.example.demo.pullThread.SendHttpThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
@EnableCircuitBreaker //启动Hystrix

public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

		//模拟redis消息队列接受端
		// new Thread(new ReceiveThread()).start();
		new Thread(new SendHttpThread()).start();
	}

}
