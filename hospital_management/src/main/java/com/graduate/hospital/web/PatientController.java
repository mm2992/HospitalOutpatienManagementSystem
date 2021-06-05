package com.graduate.hospital.web;

import com.graduate.hospital.model.Doctor;
import com.graduate.hospital.model.MedicalRecord;
import com.graduate.hospital.model.Patient;
import com.graduate.hospital.model.Prescription;
import com.graduate.hospital.service.MedicalRecordService;
import com.graduate.hospital.service.PatientService;
import com.graduate.hospital.service.PrescriptionService;
import com.graduate.hospital.util.DateTimeUtil;
import com.graduate.hospital.vo.PatientVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    PrescriptionService prescriptionService;
    @Autowired
    MedicalRecordService medicalRecordService;

    @RequestMapping("getPatient")
    public Patient getPatient(HttpSession session){
        Doctor doctor=(Doctor) session.getAttribute("user");
        String deptId=doctor.getDeptId();
        Patient patient=patientService.getNoSeePatient(deptId);
        if (patient==null){
            patient=new Patient();
        }
        session.setAttribute("patient",patient);
        return patient;
    }

    /**
     * 获取下一位患者
     * @return
     */
    @RequestMapping("getNextPatient")
    public Patient getNextPatient(HttpSession session){
        Patient newPatient=new Patient();
        Patient patient=(Patient) session.getAttribute("patient");
        String patientId=patient.getIdCard();
        //先更改当前患者的就诊状态为已就诊
        int res=patientService.updateStatus(patientId);
        if (res==1){
            //查找下一位纠正患者
            newPatient=getPatient(session);
        }
       if (newPatient.getIdCard()==null){
           Patient patient1= new Patient();
           patient1.setIdCard("0000000000");
           return patient1;
       }
        return newPatient;
    }

//查询患者信息
    @RequestMapping("getPatientById")
    public PatientVo getPatientById(String idCard){
        PatientVo patientVo=patientService.getPatientInfo(idCard);
        log.info("当前患者为：{}",patientVo);

        return patientVo;
    }

    /**
     * 退费的方法
     * @param idCard
     * @param status
     * @return
     */
    @DeleteMapping("refund")
    public String refund(String idCard,Integer status){
        log.info("患者{}的就诊状态为{}",idCard,status);
        String msg="";
        if (status==0){//就诊状态为0，表示该患者还未就诊，直接删除挂号记录
            msg=patientService.deletePatientById(idCard);
        }else {//如果状态为1，表示已经就诊执行以下方法
             msg=patientService.logOutPatient(idCard);
        }
        return msg;
    }

    /**
     * 更新账户余额
     * @param idCard
     * @param newBalance
     * @return
     */
    @PutMapping("addBalance")
    public boolean addBalance(String idCard,Double newBalance){
        boolean msg=patientService.updateBalance(idCard,newBalance);
        return msg;
    }

    /**
     *患者取完药后打印自己的就诊信息
     * @param patientId
     * @return
     */
    @RequestMapping("print")
    public Map<String,Object> printMsg(String patientId){
        Map<String,Object> map=new HashMap<>();
        //首先先查询该患者存不存在
        PatientVo patientVo = patientService.getPatientInfo(patientId);
//        log.info("您查询的患者的为{}",patientVo);

        if (patientVo==null){//患者信息不存在，直接返回false
            map.put("flag",false);
            map.put("msg","该患者信息不存在!");
            return map;
        }

//        患者信息存在，查询该患者的所有药方是否存在未支付或未取药
        boolean res=prescriptionService.selectCountNotPayOrTake(patientId);
        if (!res){
            map.put("flag",false);
            map.put("msg","该患者存在未支付或未取走的药品，暂时不能打印!");

            return map;
        }
        //该患者的所有药品都已支付并取药，可以直接打印信息
        //获取所有的药方信息
        List<Prescription> prescriptionList=prescriptionService.getAllPrescription(patientId);
        log.info("医嘱{}",prescriptionList.get(0).getDoctorOrder());
        //获取当前患者的病情信息
        MedicalRecord medicalRecord=medicalRecordService.getRecord(patientId);
        map.put("description",medicalRecord.getDescription());
        map.put("doctorName",medicalRecord.getDoctorName());
        map.put("seeTime",medicalRecord.getSeeTime());
        map.put("list",prescriptionList);
        map.put("flag",true);
        map.put("patient",patientVo);
        //计算年龄
        Integer birth=Integer.parseInt(patientId.substring(6,10));
        log.info("出生年份{}",birth);
        Integer now=Integer.parseInt(DateTimeUtil.getSysTime().substring(0,4));
        log.info("现在年份{}",now);

        int age=now-birth;
        log.info("患者年龄{}",age);
        map.put("age",age);
        return map;
    }
}
