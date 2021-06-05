package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.OrderDetailMapper;
import com.graduate.hospital.model.OrderDetail;
import com.graduate.hospital.service.OrderService;
import com.graduate.hospital.vo.OrderDetailVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDetailMapper detailMapper;
    @Override
    public String addOrder(OrderDetail orderDetail) {

        int res =detailMapper.insert(orderDetail);
        if (res==1){
            return "成功！";
        }
        return "失败";
    }


//    分页查询的方法
    @Override
    public PageListVo<OrderDetailVo> selectOrderWithMane(Map map) {
        OrderDetail orderDetail=(OrderDetail) map.get("orderDetail");
        int count=detailMapper.selectCount(orderDetail);
        log.info("未处理订单数量",count);
        List<OrderDetailVo> orderDetailVos=detailMapper.queryOrderSelective(map);
        PageListVo<OrderDetailVo> pageListVo=new PageListVo<>();
        pageListVo.setList(orderDetailVos);
        pageListVo.setCount(count);
        return pageListVo;
    }

//    根据id查单条数据
    @Override
    public OrderDetail getOrderById(String orderId) {
        OrderDetail orderDetail=detailMapper.getOrderById(orderId);

        return orderDetail;
    }

//    修改订单状态为已完成
    @Override
    public String modifyCompleted(String orderId) {
        int count=detailMapper.modifyCompleted(orderId);
        if (count==1) {
            return "订单已完成。";
        }
        return "订单完成失败";
    }

//    删除订单的方法

    @Override
    public String deleteOrderById(String orderId) {
        int i=detailMapper.deleteOrderById(orderId);
        if (i==1){
            return "订单删除成功!";
        }
        return "订单删除失败！";
    }


//    查询历史订单

    @Override
    public PageListVo<OrderDetailVo> selectHisOrder(Map<String, Object> map) {
        OrderDetail orderDetail=(OrderDetail) map.get("orderDetail");
        int count=detailMapper.selectHisCount(orderDetail);
        log.info("已处理订单条数：",count);
        List<OrderDetailVo> orderDetailVos=detailMapper.queryHisOrderSelective(map);
        log.info("所有的历史订单：",orderDetailVos);
        PageListVo<OrderDetailVo> pageListVo=new PageListVo<>();
        pageListVo.setList(orderDetailVos);
        pageListVo.setCount(count);
        return pageListVo;
    }
}
