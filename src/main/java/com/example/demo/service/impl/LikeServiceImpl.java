package com.example.demo.service.impl;

import com.example.demo.dto.LoveDto;
import com.example.demo.entity.Love;
import com.example.demo.mapper.LoveMapper;
import com.example.demo.model.RestResp;
import com.example.demo.redis.RedisCache;
import com.example.demo.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LoveMapper likeMapper;

    @Autowired
    private RedisCache redisCache;


    @Override
    public RestResp like(Integer uid, Integer statusId) {
        log.info("like  uid={},statusId={}",uid,statusId);
        //验证是否已经点赞
        if (!this.isLiked(uid, statusId)) {
            LoveDto dto = new LoveDto();
            dto.setUid(uid);
            dto.setStatusId(statusId);
            List<LoveDto> tepList = new ArrayList<>();
            tepList.add(dto);
            //此处将点赞信息发送到redis对列中，采用异步入库的方式，以减轻数据的压力
            redisCache.setCacheList("likeQueen", tepList);


            return  new RestResp("success","点赞成功",null);

        } else {
            //不能重复点赞
            return  new RestResp("success","不能重复点赞",null);
        }


    }

    @Override
    public boolean isLiked(Integer uid, Integer statusId) {
        log.info("isLiked  uid={},statusId={}",uid,statusId);
        //从redis获取bitSet信息
        String cacheObject = redisCache.getCacheObject(String.valueOf(statusId));
        if (cacheObject == null) {
            //redis中没有数据，从数据库中查询
            Love love = this.selectByStatusId(statusId);
            //数据库中也没有直接返回
            if(love!=null) {
                //更新redis缓存
                redisCache.setCacheObject(String.valueOf(statusId),love.getLikeUser());
                cacheObject=love.getLikeUser();
            }else {
                return  false;
            }


        }
        //String[] ===>long[]
        String[] split = cacheObject.split(",");
        long[] longs = new long[split.length];
        for (int i = 0; i < split.length; i++) {
            longs[i] = Long.valueOf(split[i]);
        }
        //还原bitSet
        BitSet set = BitSet.valueOf(longs);
        //验证是否已经点赞
        return set.get(uid);
    }

    @Override
    public Love selectByStatusId(Integer id) {
        return likeMapper.selectByStatusId(id);
    }

    @Override
    public int updateByVersion(Love record) {
        return likeMapper.updateByVersion(record);
    }

    @Override
    public int insertSelective(Love record) {
        return likeMapper.insertSelective(record);
    }
}
