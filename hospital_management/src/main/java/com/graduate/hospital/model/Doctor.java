package com.graduate.hospital.model;

import lombok.Data;

@Data
public class Doctor extends DocAndUser{
    private String doctorId;

    private String doctorName;

    private Integer doctorAge;

    private String doctorSex;

    private String deptId;

    private String levelId;

    private String entryDate;

    private String password;

}