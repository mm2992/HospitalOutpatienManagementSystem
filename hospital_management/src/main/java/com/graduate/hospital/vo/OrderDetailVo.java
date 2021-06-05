package com.graduate.hospital.vo;

import lombok.Data;

@Data
public class OrderDetailVo {
    private String orderId;

    private String drugName;

    private Integer quantity;

    private String maneName;

    private String orderTime;

    private String orderBy;

    private Integer orderStatus;
}
