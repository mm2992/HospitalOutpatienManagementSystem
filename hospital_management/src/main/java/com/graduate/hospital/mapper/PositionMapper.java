package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionMapper {
    int insert(Position record);

    int insertSelective(Position record);

     List<Position> selectAllPosition();
}