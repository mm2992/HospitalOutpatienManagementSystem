package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Patient;
import com.graduate.hospital.vo.PatientVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface PatientMapper {
    int deleteByPrimaryKey(String idCard);

    int insert(Patient record);

    int insertSelective(Patient record);

    Patient selectByPrimaryKey(String idCard);

    int updateByPrimaryKeySelective(Patient record);

    int updateByPrimaryKey(Patient record);

//    根据科室id查挂号人数量
    int selectAllByDeptId(Integer deptId);

//    查询挂号信息
    PatientVo getPatientInfo(String idCard);

    Patient getNoSeePatient(String deptId);

    int updateStatus(String idCard);

    double getPatientBalance(String patientId);

    int updateBalance(String patientId, double newBalance);
}