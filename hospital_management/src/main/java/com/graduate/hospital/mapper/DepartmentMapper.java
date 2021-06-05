package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DepartmentMapper {
    int deleteByPrimaryKey(String deptId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String deptId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDept();
}