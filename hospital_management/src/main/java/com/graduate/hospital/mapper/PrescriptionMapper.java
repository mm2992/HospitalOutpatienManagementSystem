package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Prescription;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface PrescriptionMapper {
    int deleteByPrimaryKey(Prescription key);

    int insert(Prescription record);

    int insertSelective(Prescription record);

    Prescription selectByPrimaryKey(Prescription key);

    int updateByPrimaryKeySelective(Prescription record);

    int updateByPrimaryKey(Prescription record);

    int getCountByPatientId(String patientId);

    List<Prescription> getPrescriptionList(Map<String, Object> map);

    List<Prescription> selectByPrimaryKeyPageList(Map map);

    double calculateBill(String[] drugId, String patientId);

    int updateStatus(String patientId, String[] drugId);

    List<Prescription> selectByIdAndStatus(String idCard);

    int updateOutStatus(String patientId, String drugId);

    int selectCountNotPayOrTake(String patientId);

    List<Prescription> getAllPrescription(String patientId);
}