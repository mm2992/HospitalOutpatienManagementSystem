package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.PatientMapper;
import com.graduate.hospital.mapper.PrescriptionMapper;
import com.graduate.hospital.model.Patient;
import com.graduate.hospital.model.Prescription;
import com.graduate.hospital.service.PatientService;
import com.graduate.hospital.vo.PatientVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Override
    public String insert(Patient patient) {
        String idCard=patient.getIdCard();

        //判断挂号信息是否已经存在
        Patient patient1=patientMapper.selectByPrimaryKey(idCard);
        //存在返回挂号信息"已经存在"
        if (patient1!=null){
            return "您已挂号，请勿重复挂号";
        }else {
            //不存在
            //先根据当前患者所挂科室的编号查询该科室已经挂号的总数量count
            int deptId=Integer.valueOf(patient.getDeptId());
            int count=patientMapper.selectAllByDeptId(deptId);

            //将当前patient的号码修改为count+1
            patient.setPatientNo(count+1);
            //将当前患者的挂号信息存入数据库，如果返回值为1，就返回挂号挂号成功并返回患者号码，否则返回失败；
            int res=patientMapper.insert(patient);
            if (res==1){
                return "挂号成功！ 您的就诊号码为第"+(count+1)+"位。";
            }else {
                return "挂号失败";
            }


        }
    }

//    查看具体挂号信息
    @Override
    public PatientVo getPatientInfo(String idCard) {
        PatientVo patient=patientMapper.getPatientInfo(idCard);
        if (patient==null){
            return null;
        }else {

            return patient;

        }
    }

    /**
     * 查找下一个未看病的的患者
     * @param deptId
     * @return
     */
    @Override
    public Patient getNoSeePatient(String deptId) {
        Patient patient=patientMapper.getNoSeePatient(deptId);
        log.info("当前就诊的患者：{}",patient);
        return patient;
    }


    /**
     * 更改就诊状态
     * @param patientId
     * @return
     */
    @Override
    public int updateStatus(String patientId) {
        int res=patientMapper.updateStatus(patientId);
        return res;
    }

    /**
     * 删除患者的挂号信息
     * @return
     */
    @Override
    public String deletePatientById(String idCard) {
        int res=patientMapper.deleteByPrimaryKey(idCard);
        if (res==1){
            return "账户注销成功！";
        }
        return "账户注销失败！";
    }

    /**
     * 患者已经就诊，执行退费操作
     * @param idCard
     * @return
     */
    @Override
    public String logOutPatient(String idCard) {
        //检查该患者的所有药品是否已经全部结算或无需要结算的药品
        List<Prescription> prescriptionList=prescriptionMapper.selectByIdAndStatus(idCard);
        if (prescriptionList.size()!=0){
            return "您还有未结算的药品请先结算..";
        }

        int num=patientMapper.deleteByPrimaryKey(idCard);
        if (num!=1){
            return "账户注销失败，请与管理员联系" ;
        }
        return "账户注销成功，请取走您的现金！";
    }

    /**
     * 更新账户余额
     * @param idCard
     * @param newBalance
     * @return
     */
    @Override
    public boolean updateBalance(String idCard, Double newBalance) {
        int res=patientMapper.updateBalance(idCard,newBalance);
        if (res==1){
            return true;
        }
        return false;
    }
}
