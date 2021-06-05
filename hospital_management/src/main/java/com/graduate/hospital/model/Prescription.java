package com.graduate.hospital.model;

import lombok.Data;

@Data
public class Prescription {

    private String patientId;

    private String drugId;

    private String drugName;

    private Integer quantity;

    private Double drugPrice;

    private Double totalPrice;

    private Integer status;

    private String doctorName;

    private String doctorOrder;
    private Integer outStatus;

}