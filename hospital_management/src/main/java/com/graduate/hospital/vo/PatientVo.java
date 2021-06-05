package com.graduate.hospital.vo;

import lombok.Data;

@Data
public class PatientVo {
    private String idCard;
    private String patientName;
    private String patientSex;
    private String deptName;
    private String registerTime;
    private  Integer patientNo;
    private double balance;
    private Integer status;
}
