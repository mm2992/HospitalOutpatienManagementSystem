package com.graduate.hospital.web;

import com.graduate.hospital.model.Department;
import com.graduate.hospital.model.Doctor;
import com.graduate.hospital.model.Level;
import com.graduate.hospital.service.DeptService;
import com.graduate.hospital.service.DoctorService;
import com.graduate.hospital.service.LevelService;
import com.graduate.hospital.vo.DoctorVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {
    @Autowired
    DeptService deptService;
    @Autowired
    LevelService levelService;
    @Autowired
    DoctorService doctorService;

    //查询科室和职称填充到页面
    @RequestMapping("fillMsg")
    public Map<String,Object> getDeptAndLev(){
        log.info("执行了");
        //查所有的科室
       List<Department> deptList=deptService.getAllDept();
       //查询所有职称
        List<Level> levelList=levelService.getAllLevel();
        Map<String,Object> map=new HashMap<>();
        map.put("deptList",deptList);
        map.put("levelList",levelList);

        return map;
    }

    //查询所有的科室
    @RequestMapping("getAllDept")
    public List<Department> getAllDept(){
        List<Department> departmentList=deptService.getAllDept();
        return departmentList;
    }

    //添加新医生信息
    @PostMapping("addDoctor")
    public String addDoctor(Doctor doctor){
        String res=doctorService.addDoctor(doctor);
        return res;
    }

    //分页查询的方法
    @RequestMapping("pageList")
    public PageListVo<DoctorVo> pageList(Integer pageNo, Integer pageSize, Doctor doctor){

        int skinPage=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("skinPage",skinPage);
        map.put("doctor",doctor);
        PageListVo<DoctorVo> doctorVoPageListVo=doctorService.pageList(map);
        return doctorVoPageListVo;

    }

    //删除医生信息
    @PostMapping("deleteDoctor")
    public boolean deleteDoctor(String[] id){
        System.out.println(id[0]);
        boolean res=doctorService.deleteDoctor(id);

        return res;
    }

    //拿到用户信息，填充修改密码的模态框
    @RequestMapping("fillDoctorModal")
    public Map fillDoctorModal(String doctorId){
        Map<String,Object> map=getDeptAndLev();
        DoctorVo doctor=doctorService.searchDoctor(doctorId);
        map.put("doctor",doctor);
        return map;
    }

    //修改用户信息
    @PostMapping("updateDoctor")
    public boolean updateDoctor(Doctor doctor ,String xzDoctorId){
        log.info("，修改的信息：{}",doctor);
        log.info("选中的医生id为：{}",xzDoctorId);
        boolean flag=doctorService.updateDoctor(doctor,xzDoctorId);
        return flag;
    }

//    修改密码
    @RequestMapping("changePwd")
    public boolean changePwd(String oldPwd, String newPwd, HttpSession session){
        Doctor doctor = (Doctor) session.getAttribute("user");
        if (!doctor.getPassword().equals(oldPwd)){
            return false;
        }
        boolean res=doctorService.changePwd(doctor.getDoctorId(),newPwd);
        return res;
    }
}