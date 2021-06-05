package com.graduate.hospital.model;

import lombok.Data;

@Data
public class OutRecords {
    private Integer recordId;

    private String drugId;

    private String drugName;

    private Integer quantity;

    private String maneId;

    private String reason;

    private String operateBy;

    private String outTime;

    private String remark;


}