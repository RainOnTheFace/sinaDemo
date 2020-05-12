package com.example.demo.pullThread;

import com.example.demo.config.ApplicationContextProvider;
import com.example.demo.dto.LoveDto;
import com.example.demo.entity.Love;
import com.example.demo.redis.RedisCache;
import com.example.demo.service.LikeService;
import lombok.extern.slf4j.Slf4j;

import java.util.BitSet;

@Slf4j
public class InsertThread implements Runnable {

    private RedisCache redisCache;


    private LikeService likeService;

    public InsertThread() {
        this.redisCache = ApplicationContextProvider.getBean(RedisCache.class);
        this.likeService = ApplicationContextProvider.getBean(LikeService.class);
    }

    @Override
    public void run() {

        while (true) {
            try {
              Object likeQueen = redisCache.popCacheList("likeQueen");

                if (likeQueen != null) {


                    LoveDto dto = (LoveDto) likeQueen;
                    log.info("队列接受端 接收到: {}",dto);
                    //重新更新标志
                    Boolean reUpdate = true;
                    while (reUpdate) {
                        Love like = likeService.selectByStatusId(dto.getStatusId());
                        if (like == null) {
                            //微博被第一次点赞
                           reUpdate= this.insertLike(dto);

                        } else {
                            reUpdate = this.updateLike(dto, like);
                        }
                    }

                } else {
                    //如果对列中没有数据，则等待1s
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新数据库中点赞记录
     */
    private Boolean updateLike(LoveDto dto, Love like) {

        //LikeUser ===>long[]
        String[] split = like.getLikeUser().split(",");
        long[] temp = new long[split.length];
        for (int i = 0; i < split.length; i++) {
            temp[i] = Long.valueOf(split[i]);
        }
        //还原bitSet
        BitSet set = BitSet.valueOf(temp);
        set.set(dto.getUid());
        //bitSet转换成String
        long[] longs = set.toLongArray();
        StringBuilder sb = new StringBuilder();
        for (long l : longs) {
            sb.append(l).append(",");
        }
        String setStr=sb.substring(0, sb.length() - 1);
        like.setLikeUser(setStr);
        int i = likeService.updateByVersion(like);

        if (i == 1) {
            //更新成功，不需要重新更新
            //刷新redis缓存
            redisCache.setCacheObject(String.valueOf(dto.getStatusId()),setStr);
            return false;
        } else {
            //更新失败,需要重新更新
            return true;
        }
    }

    /**
     * 创建数据库中点赞记录
     */
    private Boolean insertLike(LoveDto dto) {
        Love like = new Love();

        //还原bitSet
        BitSet set = new BitSet();
        set.set(dto.getUid());
        //bitSet转换成String
        long[] longs = set.toLongArray();
        StringBuilder sb = new StringBuilder();
        for (long l : longs) {
            sb.append(l).append(",");
        }
        //设置参数
        String setStr=sb.substring(0, sb.length() - 1);
        like.setLikeUser(setStr);
        like.setStatusId(dto.getStatusId());
        like.setVersion(0);
        try {
            int i = likeService.insertSelective(like);

            if (i == 1) {
                //更新成功，不需要重新更新
                //刷新redis缓存
                redisCache.setCacheObject(String.valueOf(dto.getStatusId()),setStr);
                return false;
            } else {
                //更新失败,需要重新更新
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }


    }
}
