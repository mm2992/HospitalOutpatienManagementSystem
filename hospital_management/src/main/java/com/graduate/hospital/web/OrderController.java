package com.graduate.hospital.web;

import com.graduate.hospital.model.Drug;
import com.graduate.hospital.model.OrderDetail;
import com.graduate.hospital.model.User;
import com.graduate.hospital.service.DrugService;
import com.graduate.hospital.service.ManufacturerService;
import com.graduate.hospital.service.OrderService;
import com.graduate.hospital.util.DateTimeUtil;
import com.graduate.hospital.util.UUIDUtil;
import com.graduate.hospital.vo.OrderDetailVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    DrugService drugService;
    @Autowired
    ManufacturerService manufacturerService;

//    添加订单
    @PutMapping("operate")
    public String addOrder(OrderDetail order, HttpSession session){
        order.setOrderId(UUIDUtil.getUUID());
        /**
         * 添加订单状态
         * 0：未处理
         * 1：已处理
         */
        order.setOrderStatus(0);
        order.setOrderTime(DateTimeUtil.getSysTime());
        User user = (User) session.getAttribute("user");
        order.setOrderBy(user.getUserName());
        String res=orderService.addOrder(order);
        log.info("药品名称为：{}",order);

        return res;
    }


//    分页查询
    @GetMapping("operate")
    public PageListVo<OrderDetailVo> selectOrder(int pageNo, int pageSize, OrderDetail orderDetail){
        int skinPage=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("skinPage",skinPage);
        map.put("pageSize",pageSize);
        map.put("orderDetail",orderDetail);
        PageListVo<OrderDetailVo> orderDetailVoPageListVo=orderService.selectOrderWithMane(map);
        return orderDetailVoPageListVo;
    }

    @GetMapping("getOneOrder/{orderIdStr}")
    public String getOrderById(@PathVariable("orderIdStr") String orderId, Drug drug,HttpSession session){
        System.out.println("-----------------------修改方法开始执行了------------------------");
        log.info("当前订单的Id为：{}",orderId);
        OrderDetail orderDetail=orderService.getOrderById(orderId);
        log.info("查询出来的订单信息为：{}",orderDetail);
        drug.setDrugName(orderDetail.getDrugName());
        drug.setDrugStock(orderDetail.getQuantity());
        drug.setInDate(DateTimeUtil.getSysTime());
        drug.setManuId(orderDetail.getManeId());
        User user = (User)session.getAttribute("user");
        drug.setOperatorBy(user.getUserName());
        log.info("添加的药品信息为：{}",drug);
        String s = drugService.enterDrug(drug);
        String msg="订单完成失败,请检查所填药品编号是否已存在！";
        if (s.equals("添加成功")){
            //如果添加成功，修改当前订单状态为已完成
             msg=orderService.modifyCompleted(orderId);
        }
        return msg;

    }

    /**
     *根据订单id删除订单
     * @param orderId
     * @return
     */
    @DeleteMapping("operate")
    public String deleteOrder(String orderId){
        String msg=orderService.deleteOrderById(orderId);
        return msg;
    }

    @GetMapping("HisOrder")
    public PageListVo<OrderDetailVo> selectHisOrder(int pageSize,int pageNo,OrderDetail orderDetail){
        log.info("历史订单查询条件",orderDetail);
        int skinPage=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("skinPage",skinPage);
        map.put("pageSize",pageSize);
        map.put("orderDetail",orderDetail);
        PageListVo<OrderDetailVo> orderDetailVoPageListVo=orderService.selectHisOrder(map);
        return orderDetailVoPageListVo;
    }

    /**
     * 加购已经存在的药品
     * @return
     */
    @RequestMapping("orderExist")
    public boolean orderExistDrug(String drugName,String maneName,int quantity,HttpSession session){
        //根据制造商名字查询其id
        String maneId=manufacturerService.getManeIdByName(maneName);
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDrugName(drugName);
        orderDetail.setManeId(maneId);
        orderDetail.setQuantity(quantity);
        orderDetail.setOrderId(UUIDUtil.getUUID());

        orderDetail.setOrderStatus(0);
        orderDetail.setOrderTime(DateTimeUtil.getSysTime());
        User user = (User) session.getAttribute("user");
        orderDetail.setOrderBy(user.getUserName());
        String res=orderService.addOrder(orderDetail);
        log.info("加购结果为：{}",res);

        if (res.equals("成功！")){
            return true;
        }

        return false;

    }


}
