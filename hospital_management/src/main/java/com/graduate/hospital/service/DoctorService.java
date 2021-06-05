package com.graduate.hospital.service;

import com.graduate.hospital.model.Doctor;
import com.graduate.hospital.vo.DoctorVo;
import com.graduate.hospital.vo.PageListVo;

import java.util.Map;

public interface DoctorService {
    Doctor queryDoctor(Doctor doctor);

    String addDoctor(Doctor doctor);

    PageListVo<DoctorVo> pageList(Map<String, Object> map);

    boolean deleteDoctor(String[] id);

    DoctorVo searchDoctor(String doctorId);

    boolean updateDoctor(Doctor doctor,String xzDoctorId);

    DoctorVo getDoctorVoById(String doctorId);

    boolean changePwd(String doctorId, String newPwd);
}
