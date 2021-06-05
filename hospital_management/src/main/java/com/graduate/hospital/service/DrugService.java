package com.graduate.hospital.service;

import com.graduate.hospital.model.Drug;
import com.graduate.hospital.vo.DrugVo;
import com.graduate.hospital.vo.PageListVo;

import java.util.Map;

public interface DrugService {
    String enterDrug(Drug drug);

//    分页查询
    PageListVo<DrugVo> getAllDrug(Map map);

    DrugVo getDrugById(String drugId);

    PageListVo<DrugVo> selectDrugByDate(Map<String, Object> map);

    PageListVo<DrugVo> selectDrugByStock(Map<String, Object> map);

    boolean updateStock(String drugId, Integer quantity);

    Drug getDrug(String drugId);
}
