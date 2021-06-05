package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Manufacturer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManufacturerMapper {
    int deleteByPrimaryKey(String maneId);

    int insert(Manufacturer record);

    int insertSelective(Manufacturer record);

    Manufacturer selectByPrimaryKey(String maneId);

    int updateByPrimaryKeySelective(Manufacturer record);

    int updateByPrimaryKey(Manufacturer record);

    List<Manufacturer> getAllManufacturer();

    String getManeIdByName(String maneName);
}