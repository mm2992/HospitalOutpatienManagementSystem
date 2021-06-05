package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.DoctorMapper;
import com.graduate.hospital.model.Doctor;
import com.graduate.hospital.service.DoctorService;
import com.graduate.hospital.vo.DoctorVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;
    @Override
    public Doctor queryDoctor(Doctor doctor) {
        Doctor doctor1=doctorMapper.queryDoctor(doctor);
        return doctor1;
    }


    //添加医生

    @Override
    public String addDoctor(Doctor doctor) {
        //查询是否该医生已经存在
        Doctor doc=doctorMapper.selectByPrimaryKey(doctor.getDoctorId());
        if (doc!=null){
            return "该医生信息已经存在，请重新填写！";
        }
        //不存在，继续添加
        int res=doctorMapper.insertSelective(doctor);
        if (res!=1){
            return "添加失败！";
        }
        return "添加成功！";
    }

    //分页

    @Override
    public PageListVo<DoctorVo> pageList(Map<String, Object> map) {
        //获得与条件相匹配的总记录条数
        int count=doctorMapper.getDoctorCount(map);
        //取得所有的符合条件的对象
        List<DoctorVo> doctorVos=doctorMapper.getDoctorVoList(map);
        PageListVo<DoctorVo> doctorVoPageListVo=new PageListVo<>();
        doctorVoPageListVo.setCount(count);
        doctorVoPageListVo.setList(doctorVos);
        return doctorVoPageListVo;
    }

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    @Override
    public boolean deleteDoctor(String[] id) {
        System.out.println(Arrays.toString(id));
        int count=doctorMapper.deleteByPrimaryKeys(id);
        log.info("受影响的行数:{}",count);
        if (count==id.length){
            return true;
        }
        return false;
    }


    /**
     * 根据主键查单条
     */

    @Override
    public DoctorVo searchDoctor(String doctorId) {
        DoctorVo doctor=doctorMapper.selectDoctor(doctorId);
        return doctor;
    }

    /**
     * 修改用户信息
     */

    @Override
    public boolean updateDoctor(Doctor doctor,String xzDoctorId) {
        int res=0;
        Doctor doctor1=doctorMapper.selectByPrimaryKey(doctor.getDoctorId());
        if (doctor1==null||doctor.getDoctorId().equals(xzDoctorId)){
            res = doctorMapper.updateByPrimaryKeySelective(doctor);
        }

        if (res==1){
            return true;
        }
        return false;
    }

    /**
     * 查doctorVo
     * @param doctorId
     * @return
     */
    @Override
    public DoctorVo getDoctorVoById(String doctorId)
    {
        DoctorVo doctorVo = doctorMapper.selectDoctor(doctorId);
        return doctorVo;
    }

    /**
     * 修改密码
     * @param doctorId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @Override
    public boolean changePwd(String doctorId, String newPwd) {
        int res=doctorMapper.changePwd(doctorId,newPwd);
        if (res==1){
            return true;
        }
        return false;
    }
}
