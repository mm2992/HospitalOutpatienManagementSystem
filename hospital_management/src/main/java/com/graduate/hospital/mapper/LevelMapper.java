package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Level;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LevelMapper {
    int deleteByPrimaryKey(String levelId);

    int insert(Level record);

    int insertSelective(Level record);

    Level selectByPrimaryKey(String levelId);

    int updateByPrimaryKeySelective(Level record);

    int updateByPrimaryKey(Level record);

    List<Level> getAllLevel();
}