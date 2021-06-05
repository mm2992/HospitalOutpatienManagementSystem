package com.graduate.hospital.vo;

import lombok.Data;

@Data
public class DoctorVo {
    private String doctorId;

    private String doctorName;

    private Integer doctorAge;

    private String doctorSex;

    private String deptName;

    private String levelName;

    private String entryDate;

    private String password;
}
