package com.graduate.hospital.mapper;

import com.graduate.hospital.model.OrderDetail;
import com.graduate.hospital.vo.OrderDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Mapper
@Component
public interface OrderDetailMapper {
    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    int selectCount(OrderDetail orderDetail);

    List<OrderDetailVo> queryOrderSelective(Map map);

    OrderDetail getOrderById(String orderId);

    int modifyCompleted(String orderId);

    int deleteOrderById(String orderId);

    List<OrderDetailVo> queryHisOrderSelective(Map<String, Object> map);

    int selectHisCount(OrderDetail orderDetail);

}