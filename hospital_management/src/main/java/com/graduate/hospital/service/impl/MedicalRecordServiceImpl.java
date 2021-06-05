package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.MedicalRecordMapper;
import com.graduate.hospital.mapper.PatientMapper;
import com.graduate.hospital.model.MedicalRecord;
import com.graduate.hospital.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    @Autowired
    MedicalRecordMapper medicalRecordMapper;
    @Autowired
    PatientMapper patientMapper;
    @Override
    @Transactional
    public boolean saveRecord(MedicalRecord medicalRecord) {
        int res= medicalRecordMapper.insert(medicalRecord);
        if (res==1){
            /*int num=patientMapper.updateStatus(medicalRecord.getPatientId());
            if (num==1){
                return true;
            }*/
            return true;
        }
        return false;
    }

    /**
     * 获取该患者的病情描述
     * @param patientId
     * @return
     */
    @Override
    public MedicalRecord getRecord(String patientId) {
        MedicalRecord medicalRecord = medicalRecordMapper.selectByPrimaryKey(patientId);

        return medicalRecord;
    }
}
