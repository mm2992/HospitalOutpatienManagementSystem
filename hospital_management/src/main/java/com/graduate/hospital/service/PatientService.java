package com.graduate.hospital.service;

import com.graduate.hospital.model.Patient;
import com.graduate.hospital.vo.PatientVo;

import java.util.Map;

public interface PatientService {

    String insert(Patient patient);


    PatientVo getPatientInfo(String idCard);

    Patient getNoSeePatient(String deptId);

    int updateStatus(String patientId);

    String deletePatientById(String idCard);

    String logOutPatient(String idCard);

    boolean updateBalance(String idCard, Double newBalance);
}
