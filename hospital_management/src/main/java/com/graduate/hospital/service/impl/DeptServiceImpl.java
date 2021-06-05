package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.DepartmentMapper;
import com.graduate.hospital.model.Department;
import com.graduate.hospital.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public List getAllDept() {
        List<Department> departmentList=departmentMapper.getAllDept();
        return departmentList;
    }
}
