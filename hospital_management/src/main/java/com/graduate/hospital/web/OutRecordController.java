package com.graduate.hospital.web;

import com.graduate.hospital.model.OutRecords;
import com.graduate.hospital.model.User;
import com.graduate.hospital.service.ManufacturerService;
import com.graduate.hospital.service.OutRecordService;
import com.graduate.hospital.util.DateTimeUtil;
import com.graduate.hospital.vo.OutRecordsVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/outRecord")
@Slf4j
public class OutRecordController {

    @Autowired
    ManufacturerService manufacturerService;
    @Autowired
    OutRecordService outRecordService;
    @PutMapping("addRecord")
    public boolean addRecord(OutRecords outRecords, String maneName, HttpSession session){
        //先拿到生产厂商的id
        String maneId=manufacturerService.getManeIdByName(maneName);
        outRecords.setManeId(maneId);
        String time=DateTimeUtil.getSysTime();
        outRecords.setOutTime(time);
        User user = (User)session.getAttribute("user");
        outRecords.setOperateBy(user.getUserName());
        outRecords.setRecordId(0);
        boolean res=outRecordService.addRecord(outRecords);
        log.info("当前退订数据：{}",outRecords);
        return res;
    }

    @GetMapping("pageList")
    public PageListVo<OutRecordsVo> selectRecord(OutRecords outRecords, Integer pageNo, Integer pageSize){
//        log.info("查询条件的记录为{}",outRecords);
        Integer skinPage=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("skinPage",skinPage);
        map.put("pageSize",pageSize);
        map.put("outRecord",outRecords);
        PageListVo<OutRecordsVo> outRecordsVoPageListVo=outRecordService.selectRecordPageList(map);
        return outRecordsVoPageListVo;
    }

    @DeleteMapping("delRecord/{recordId}")
    public boolean delRecord(@PathVariable("recordId") Integer recordId)
    {
        boolean flag=outRecordService.delRecordByPrimaryKey(recordId);
        return flag;
    }
}
