package com.graduate.hospital.model;


import lombok.Data;

@Data
public class Patient {
    private String idCard;

    private String patientName;

    private String patientSex;

    private String deptId;

    private Double balance;

    private String registerTime;

    private Integer patientNo;
    private Integer status;

}