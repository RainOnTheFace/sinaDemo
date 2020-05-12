# 要求
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

#### 介绍
1.开发环境 
jdk 1.8  
mysql 
redis   

#### 软件架构
需要redis 或者RabbtiMQ

``````


#### 使用说明

1.  http://localhost:8080/page1



