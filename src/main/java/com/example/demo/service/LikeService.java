package com.example.demo.service;


import com.example.demo.entity.Love;
import com.example.demo.model.RestResp;
import org.springframework.stereotype.Service;


@Service
public interface LikeService {

    public RestResp like(Integer uid, Integer statusId);

    public boolean isLiked(Integer uid, Integer statusId);


    public Love selectByStatusId(Integer id);

    public int updateByVersion(Love record);

   public   int insertSelective(Love record);


}
