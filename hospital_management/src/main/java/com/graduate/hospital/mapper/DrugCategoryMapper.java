package com.graduate.hospital.mapper;

import com.graduate.hospital.model.DrugCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrugCategoryMapper {
    int deleteByPrimaryKey(String categoryId);

    int insert(DrugCategory record);

    int insertSelective(DrugCategory record);

    DrugCategory selectByPrimaryKey(String categoryId);

    int updateByPrimaryKeySelective(DrugCategory record);

    int updateByPrimaryKey(DrugCategory record);

    List<DrugCategory> getAllCategory();
}