package com.example.demo.mapper;

import com.example.demo.entity.Love;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper
public interface LoveMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Love record);

    int insertSelective(Love record);

    Love selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Love record);

    int updateByPrimaryKey(Love record);


    Love selectByStatusId(Integer id);
    int updateByVersion(Love record);

}
