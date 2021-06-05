package com.graduate.hospital.vo;

import lombok.Data;

@Data
public class DrugVo {
    private String drugId;

    private String drugName;

    private Double drugPrice;

    private Integer drugStock;

    private String categoryName;

    private String productTime;

    private String expiryDate;

    private String inDate;

    private String operatorBy;

    private String maneName;

    private String drugAttr;
}
