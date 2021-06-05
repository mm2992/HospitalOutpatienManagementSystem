package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Drug;
import com.graduate.hospital.vo.DrugVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface DrugMapper {
    int deleteByPrimaryKey(String drugId);

    int insert(Drug record);

    int insertSelective(Drug record);

    Drug selectByPrimaryKey(String drugId);

    int updateByPrimaryKeySelective(Drug record);

    int updateByPrimaryKey(Drug record);

    int getCountSelected(Drug drug);

    List<DrugVo> getDrugsSelected(Map map);

    DrugVo getOneDrugVo(String drugId);

    int getCountByDate(String date);

    List<DrugVo> selectByDate(Map<String, Object> map);

    int getCountByStock(Integer stock);

    List<DrugVo> selectByStock(Map<String, Object> map);

    int getStockById(String drugId);

    int updateStock(String drugId, int newStock);
}