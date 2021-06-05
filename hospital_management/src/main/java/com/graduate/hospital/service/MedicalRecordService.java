package com.graduate.hospital.service;

import com.graduate.hospital.model.MedicalRecord;

public interface MedicalRecordService {
    boolean saveRecord(MedicalRecord medicalRecord);


    MedicalRecord getRecord(String patientId);
}
