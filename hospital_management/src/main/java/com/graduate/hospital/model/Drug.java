package com.graduate.hospital.model;

import lombok.Data;

@Data
public class Drug {
    private String drugId;

    private String drugName;

    private Double drugPrice;

    private Integer drugStock;

    private String categoryId;

    private String productTime;

    private String expiryDate;

    private String inDate;

    private String operatorBy;

    private String manuId;

    private String drugAttr;

}