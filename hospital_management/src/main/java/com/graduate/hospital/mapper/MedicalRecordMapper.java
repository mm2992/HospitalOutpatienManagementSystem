package com.graduate.hospital.mapper;

import com.graduate.hospital.model.MedicalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MedicalRecordMapper {
    int deleteByPrimaryKey(String patientId);

    int insert(MedicalRecord record);

    int insertSelective(MedicalRecord record);

    MedicalRecord selectByPrimaryKey(String patientId);

    int updateByPrimaryKeySelective(MedicalRecord record);

    int updateByPrimaryKey(MedicalRecord record);
}