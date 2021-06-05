package com.graduate.hospital.web;

import com.graduate.hospital.model.*;
import com.graduate.hospital.service.DrugService;
import com.graduate.hospital.service.OutRecordService;
import com.graduate.hospital.service.PatientService;
import com.graduate.hospital.service.PrescriptionService;
import com.graduate.hospital.util.DateTimeUtil;
import com.graduate.hospital.vo.DoctorVo;
import com.graduate.hospital.vo.DrugVo;
import com.graduate.hospital.vo.PageListVo;
import com.graduate.hospital.vo.PatientVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prescription")
@Slf4j
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;
    @Autowired
    DrugService drugService;
    @Autowired
    OutRecordService outRecordService;

//    添加药方
    @PutMapping("operation")
    public boolean  addPrescription(Prescription prescription, HttpSession session){
    Doctor doctor=(Doctor) session.getAttribute("user");
    prescription.setDoctorName(doctor.getDoctorName());
    Patient patient=(Patient) session.getAttribute("patient");
    prescription.setPatientId(patient.getIdCard());
    boolean res=prescriptionService.addPrescription(prescription);
      log.info("当前的药方为：{}",prescription);
      return res;
    }

//    查询药方
    @GetMapping("operation")
    public Object getPrescription(HttpSession session,int pageNo,int pageSize){
       int skinPage=(pageNo-1)*pageSize;
       Patient patient=(Patient) session.getAttribute("patient");
       log.info("当前患者为：{}",patient);
       String patientId=patient.getIdCard();
        Map<String,Object> map=new HashMap<>();
        map.put("skinPage",skinPage);
        map.put("pageSize",pageSize);
        map.put("patientId",patientId);
       PageListVo<Prescription> prescription =prescriptionService.getPrescription(map);
       log.info("当前患者所有的药方：{}",prescription.getList());
       return prescription;

    }

    //修改药品数量及添加医嘱
    @PostMapping("update")
    public boolean updatePrescription(Prescription prescription){
        int quantity=prescription.getQuantity();
        double price=prescription.getDrugPrice();
        double totalPrice=quantity*price;
        log.info("总价为{}",totalPrice);
        prescription.setTotalPrice(totalPrice);
        log.info("这个待修改药方的修改信息：{}",prescription);
        boolean res=prescriptionService.updatePrescription(prescription);
        return res;
    }

    //删除某条药方
    @DeleteMapping("operation")
    public boolean deletePrescription(Prescription prescription){
        log.info("删除的药方信息为：{}",prescription.getPatientId()+prescription.getDrugId());
        boolean res=prescriptionService.deletePrescription(prescription);
        return res;
    }

    //查询某位患者的药方信息，通过身份证号
    @GetMapping("getById/{patientId}/{status}")
    public PageListVo<Prescription> getPrescriptionById(@PathVariable("patientId") String patientId,int pageNo,int pageSize,@PathVariable("status") String status){
        log.info("您要查询的药方的患者为：{}", patientId);
        int skinPage=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("skinPage",skinPage);
        map.put("pageSize",pageSize);
        map.put("patientId",patientId);
        map.put("status",status);
        PageListVo<Prescription> prescriptionPageListVo=prescriptionService.queryPrescriptionByPatientId(map);
        return prescriptionPageListVo;
    }


    /**
     *
     * 计算某个患者的总账单
     * @param drugId
     * @param patientId
     * @return
     */
    @PostMapping("calculateBill")
    public double calculateBill(String[] drugId,String patientId){
        /*log.info("该患者的身份证号码为：{}",patientId);
        log.info("一共选择了：{}条数据",drugId.length);*/
        double bill=prescriptionService.calculateBill(drugId,patientId);
        return bill;
    }

    /**
     * 支付的方法
     * @param drugId
     * @param patientId
     * @param money
     * @return
     */
    @PutMapping("pay")
    public String pay(String[] drugId,String patientId,double money){
          /* log.info("该患者的身份证号码为：{}",patientId);
        log.info("一共选择了：{}条数据",drugId.length);
        log.info("待支付的钱数{}",money);*/
          String message=prescriptionService.pay(drugId,patientId,money);
        return message;
    }

    @PostMapping("takeMedicine")
    public Boolean takeMedicine(String patientId,String drugId,Integer quantity,HttpSession session){
        boolean res=drugService.updateStock(drugId,quantity);
        if (res){
            //现根据药品id查出该药品
            User user=(User)session.getAttribute("user");
            String sysTime = DateTimeUtil.getSysTime();
            Drug drug = drugService.getDrug(drugId);
            OutRecords outRecords=new OutRecords();
            outRecords.setDrugId(drugId);
            outRecords.setDrugName(drug.getDrugName());
            outRecords.setManeId(drug.getManuId());
            outRecords.setQuantity(quantity);
            outRecords.setReason("售卖");
            outRecords.setOperateBy(user.getUserName());
            outRecords.setOutTime(sysTime);
            boolean b = outRecordService.insertRecord(outRecords);
            if (b){//出售记录添加成功
//                将该条药方的处理状态变为已取药
                boolean f=prescriptionService.updateOutStatus(patientId,drugId);
                if (f) return true;
            }
        }

        return false;
    }
}
