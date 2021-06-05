package com.graduate.hospital.web;

import com.graduate.hospital.model.Department;
import com.graduate.hospital.model.Patient;
import com.graduate.hospital.service.DeptService;
import com.graduate.hospital.service.PatientService;
import com.graduate.hospital.util.DateTimeUtil;
import com.graduate.hospital.vo.PatientVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/self")
@Slf4j
public class SelfServiceController {
    @Autowired
    private DeptService deptService;
    @Autowired
    private PatientService patientService;

//    获取所有的科室对象
    @RequestMapping("getDept")
    @ResponseBody
    public Object getAllDept(){
        List<Department> departmentList=deptService.getAllDept();
        return departmentList;

    }

//    写入患者挂号信息
    @RequestMapping("setPatient")
    @ResponseBody
    public String setRegister(Patient patient){
            patient.setStatus(0);
            patient.setBalance(0.00);
            String time = DateTimeUtil.getSysTime();
            patient.setRegisterTime(time);
            String result = patientService.insert(patient);
            System.out.println(result);
            return result;
    }


//    查询挂号信息
    @RequestMapping("getPatientInfo")
    @ResponseBody
    public Object getPatientInfo(String idCard/*,HttpServletRequest request*/){
        PatientVo patient=patientService.getPatientInfo(idCard);
        System.out.println(patient);
        //request.getSession().setAttribute("patient",patient);
        if (patient==null){
            return false;
        }
        return patient;
    }
}
