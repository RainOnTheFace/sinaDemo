# sinaDemo  
### 要求  
具体题目如下：
o    实现一个简版微博点赞服务，要求：

§  支持对某条微博点赞，每条微博只能被每人赞一次

§  支持查询某人是否赞过某条微博

§  每秒有1万次点赞请求，同时每秒有20万次查询请求

§  要求系统架构能够支持未来3年内的数据增量，给出当前集群部署规模及未来扩容方案

§  请以此为基础，给出设计文档及demo程序。

§  基础定义：

§  微博Id：Integer

§  用户Id：Integer

§  点赞接口：public void like(int uid, int statusId)

§  查询赞接口：public boolean isLiked(int uid, int statusId) 

### 介绍

#### 1.开发环境  
jdk 1.8  
mysql   
redis    

#### 2.建表语句  
```CREATE TABLE `love` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
     `status_id` bigint(20) unsigned NOT NULL COMMENT '微博ID',
     `like_user` text COMMENT '点赞用户',
     `version` int(10) DEFAULT NULL COMMENT '版本',
     PRIMARY KEY (`id`),
     UNIQUE KEY `status_id_key` (`status_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';``` 


### 软件架构
**过滤核心思想：布隆算法+BitMap**  
**点赞：异步沉淀**  
**查询过滤：先从redis缓存中查询，没有再从数据库中查询**  


#### 理想架构图  
![理想架构图](https://github.com/RainOnTheFace/sinaDemo/blob/master/picture/sa.jpg)
#### 查询，点赞流程  
![查询，点赞流程](https://github.com/RainOnTheFace/sinaDemo/blob/master/picture/sinaDemo1.jpg)
#### 异步入库流程  
![异步入库流程](https://github.com/RainOnTheFace/sinaDemo/blob/master/picture/sinaDemo2.jpg)


#### 使用说明

**1. 配置数据库和redis**  
**2. 启动程序**  
**3.访问一下url**  
http://localhost:8080/page1  



