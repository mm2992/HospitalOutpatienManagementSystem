package com.graduate.hospital.service;

import com.graduate.hospital.model.Prescription;
import com.graduate.hospital.vo.PageListVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface PrescriptionService {
    boolean addPrescription(Prescription prescription);


    PageListVo<Prescription> getPrescription(Map<String, Object> map);

    boolean updatePrescription(Prescription prescription);

    boolean deletePrescription(Prescription prescription);

    PageListVo<Prescription> queryPrescriptionByPatientId(Map map);

    double calculateBill(String[] drugId, String patientId);

    String pay(String[] drugId, String patientId, double money);

    boolean updateOutStatus(String patientId, String drugId);

    boolean selectCountNotPayOrTake(String patientId);

    List<Prescription> getAllPrescription(String patientId);
}
