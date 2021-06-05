package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.PatientMapper;
import com.graduate.hospital.mapper.PrescriptionMapper;
import com.graduate.hospital.model.Prescription;
import com.graduate.hospital.service.PrescriptionService;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    PrescriptionMapper prescriptionMapper;
    @Autowired
    PatientMapper patientMapper;
    @Override
    public boolean addPrescription(Prescription prescription) {
        prescription.setOutStatus(0);
        int res=prescriptionMapper.insert(prescription);
        if (res==1){
            return true;
        }
        return false;
    }


    //    拿到所有的药方
    @Override
    public PageListVo<Prescription> getPrescription(Map<String, Object> map) {
        String patientId=(String)map.get("patientId");
        //先查出所有条数
        int count=prescriptionMapper.getCountByPatientId(patientId);
        log.info("总药方条数：{}",count);
        List<Prescription> prescriptionList=prescriptionMapper.getPrescriptionList(map);
        log.info("所有的药方信息：{}",prescriptionList);
        PageListVo<Prescription> prescriptionPageListVo=new PageListVo<>();
        prescriptionPageListVo.setCount(count);
        prescriptionPageListVo.setList(prescriptionList);
        return prescriptionPageListVo;
    }

    @Override
    public boolean updatePrescription(Prescription prescription) {
        Prescription prescription1 = prescriptionMapper.selectByPrimaryKey(prescription);
        log.info("待修改的东西：{}",prescription1);
        int res=prescriptionMapper.updateByPrimaryKeySelective(prescription);
        if (res==1){
            return true;
        }
        return false;
    }

    /**
     * 删除某一条药方
     * @param drugId
     * @param patientId
     * @return
     */
    @Override
    public boolean deletePrescription(Prescription prescription) {
        int res = prescriptionMapper.deleteByPrimaryKey(prescription);
        if (res==1){
            return true;
        }

        return false;
    }

    /**
     * 根据 patientId查询对应的药方并分页
     * @param
     * @return
     */
    @Override
    public PageListVo<Prescription> queryPrescriptionByPatientId(Map map) {
        int count = prescriptionMapper.getCountByPatientId((String) map.get("patientId"));
        List<Prescription> prescriptionList=prescriptionMapper.selectByPrimaryKeyPageList(map);
        log.info("该患者的所有药方数量：{}",count);
        log.info("该患者的所有药方为：{}",prescriptionList);
        PageListVo<Prescription> prescriptionPageListVo=new PageListVo<>();
        prescriptionPageListVo.setList(prescriptionList);
        prescriptionPageListVo.setCount(count);
        return prescriptionPageListVo;

    }

    /**
     * 计算账单
     * @param drugId
     * @param patientId
     * @return
     */
    @Override
    public double calculateBill(String[] drugId, String patientId) {
        double bill=prescriptionMapper.calculateBill(drugId,patientId);
        log.info("该患者的总费用为：{}",bill);
        return bill;
    }

    /**
     * 完成支付过程
     * @param drugId
     * @param patientId
     * @param money
     * @return
     */
    @Override
    @Transactional
    public String pay(String[] drugId, String patientId, double money) {
        //计算该患者账户内余额是否充足
        double balance=patientMapper.getPatientBalance(patientId);
//        log.info("当前患者的账户余额为：{}",balance);

        if (money>balance){//余额不足，前往缴费
            return "账户余额不足，请尽快前往充值..";
        }

        //余额充足，完成扣费
        int num=patientMapper.updateBalance(patientId,balance-money);
        if (num==1){//如果扣费成功，修改对应账单的状态为已结算
            int suc=prescriptionMapper.updateStatus(patientId,drugId);
            if (suc!=drugId.length){
                return "失败！";
            }
        }

        return "支付成功！";
    }

    @Override
    public boolean updateOutStatus(String patientId, String drugId) {
        int num=prescriptionMapper.updateOutStatus(patientId,drugId);
        if (num==1){
            return true;
        }
        return false;
    }

    /**
     * 查询是否存在未支付或为取药的记录
     * @param patientId
     * @return
     */
    @Override
    public boolean selectCountNotPayOrTake(String patientId) {
        int num=prescriptionMapper.selectCountNotPayOrTake(patientId);
        if (num==0){
            return true;
        }
        return false;

    }

    /**
     * 直接查询所有的药方信息不分页
     * @param patientId
     * @return
     */
    @Override
    public List<Prescription> getAllPrescription(String patientId) {
        List<Prescription> prescriptionList=prescriptionMapper.getAllPrescription(patientId);
        return prescriptionList;
    }


}
