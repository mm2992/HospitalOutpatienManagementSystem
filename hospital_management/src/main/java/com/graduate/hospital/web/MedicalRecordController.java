package com.graduate.hospital.web;

import com.graduate.hospital.model.Doctor;
import com.graduate.hospital.model.MedicalRecord;
import com.graduate.hospital.model.Patient;
import com.graduate.hospital.service.MedicalRecordService;
import com.graduate.hospital.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/record")
@Slf4j
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;
    @PutMapping("operation")
    public boolean saveCondition(MedicalRecord medicalRecord, HttpSession session){
        Patient patient=(Patient) session.getAttribute("patient");
        Doctor doctor=(Doctor) session.getAttribute("user");
        log.info("当前的医生为：{}",doctor);
        medicalRecord.setDeptId(patient.getDeptId());
        medicalRecord.setDoctorName(doctor.getDoctorName());
        medicalRecord.setSeeTime(DateTimeUtil.getSysTime());
        log.info("当前患者的病例为：{}", medicalRecord);
        boolean res= medicalRecordService.saveRecord(medicalRecord);
        return res;
    }


}
