package com.graduate.hospital.mapper;

import com.graduate.hospital.model.Doctor;
import com.graduate.hospital.vo.DoctorVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface DoctorMapper {
    int deleteByPrimaryKey(String doctorId);

    int insert(Doctor record);

    int insertSelective(Doctor record);

    Doctor selectByPrimaryKey(String doctorId);

    int updateByPrimaryKeySelective(Doctor record);

    int updateByPrimaryKey(Doctor record);

    Doctor queryDoctor(Doctor doctor);

    int getDoctorCount(Map<String, Object> map);

    List<DoctorVo> getDoctorVoList(Map<String, Object> map);

    int deleteByPrimaryKeys(String[] id);

    DoctorVo selectDoctor(String doctorId);

    int changePwd(String doctorId,  String newPwd);
}