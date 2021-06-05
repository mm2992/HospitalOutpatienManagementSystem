package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.DrugCategoryMapper;
import com.graduate.hospital.model.DrugCategory;
import com.graduate.hospital.service.DrugCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugCategoryServiceImpl implements DrugCategoryService {

    @Autowired
    DrugCategoryMapper drugCategoryMapper;
    @Override
    public List<DrugCategory> getAllCategory() {
        List<DrugCategory> drugCategoryList=drugCategoryMapper.getAllCategory();
        return drugCategoryList;
    }
}
