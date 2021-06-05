package com.graduate.hospital.vo;

import lombok.Data;

@Data
public class OutRecordsVo {
    private Integer recordId;

    private String drugId;

    private String drugName;

    private Integer quantity;

    private String maneName;

    private String reason;

    private String operateBy;

    private String outTime;

    private String remark;
}
