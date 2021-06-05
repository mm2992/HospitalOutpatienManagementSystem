package com.graduate.hospital.model;

import lombok.Data;

@Data
public class OrderDetail {
    private String orderId;

    private String drugName;

    private Integer quantity;

    private String maneId;

    private String orderTime;

    private String orderBy;

    private Integer orderStatus;


}