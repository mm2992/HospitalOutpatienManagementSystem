package com.graduate.hospital.service;

import com.graduate.hospital.model.OrderDetail;
import com.graduate.hospital.vo.OrderDetailVo;
import com.graduate.hospital.vo.PageListVo;

import java.util.Map;

public interface OrderService {
    String addOrder(OrderDetail orderDetail);

    PageListVo<OrderDetailVo> selectOrderWithMane(Map map);

    OrderDetail getOrderById(String orderId);

    String modifyCompleted(String orderId);

    String deleteOrderById(String orderId);

    PageListVo<OrderDetailVo> selectHisOrder(Map<String, Object> map);
}
