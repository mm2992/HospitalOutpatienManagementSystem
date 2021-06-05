package com.graduate.hospital.web;

import com.graduate.hospital.model.Drug;
import com.graduate.hospital.model.DrugCategory;
import com.graduate.hospital.model.Manufacturer;
import com.graduate.hospital.model.User;
import com.graduate.hospital.service.DrugCategoryService;
import com.graduate.hospital.service.DrugService;
import com.graduate.hospital.service.ManufacturerService;
import com.graduate.hospital.util.DateTimeUtil;
import com.graduate.hospital.vo.DrugVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/drug")
public class DrugController {
    @Autowired
    DrugService drugService;
    @Autowired
    DrugCategoryService drugCategoryService;
    @Autowired
    ManufacturerService manufacturerService;

    //查询所有的生产厂商和药品类别
    @GetMapping("getManuAndCate")
    public Map<String, Object> getManuAndCate(){
        List<DrugCategory> drugCategoryList=drugCategoryService.getAllCategory();
        List<Manufacturer> manufacturers=manufacturerService.getAllManufacturer();
        Map<String,Object> map=new HashMap<>();
        map.put("drugCategory",drugCategoryList);
        map.put("manufacturer",manufacturers);
        return map;
    }

    //查询生产厂商
    @GetMapping("getManu")
    public List<Manufacturer> getManufacturer(){
        List<Manufacturer> allManufacturer = manufacturerService.getAllManufacturer();
        return allManufacturer;
    }

    //添加药品信息
    @PostMapping("enterDrug")
    public String enterDrug(Drug drug, HttpSession session){
        drug.setInDate(DateTimeUtil.getSysTime());
        User user=(User)session.getAttribute("user");
        drug.setOperatorBy(user.getUserName());
        log.info("药品信息为{}",drug);
        String message=drugService.enterDrug(drug);
        return message;
    }


//    分页查询
    @GetMapping("operate")
    public PageListVo<DrugVo> getAllDrug(int pageNo, int pageSize, Drug drug){
        int skinPage=(pageNo-1)*pageSize;
        Map map=new HashMap();
        map.put("skinPage",skinPage);
        map.put("pageSize",pageSize);
        map.put("drug",drug);
        PageListVo<DrugVo> drugPageListVo = drugService.getAllDrug(map);
        System.out.println(drugPageListVo);
        return drugPageListVo;
    }

//    根据id查询单条数据
    @GetMapping("getOneDrug/{drugId}")
    public DrugVo getOneDrugById(@PathVariable("drugId") String drugId, HttpServletRequest request){
        DrugVo drugVo=drugService.getDrugById(drugId);
        request.setAttribute("drug",drugVo);
        System.out.println("查到的数据为："+drugVo);
        return drugVo;
    }

    /**
     *
     * 药品监测功能，分页查询
     * @param pageNo
     * @param pageSize
     * @param reason
     * @param num
     * @return
     */
    @GetMapping("monitor")
    public Object monitorDrug(int pageNo,int pageSize,String reason,String num){
        int skinPage=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("skinPage",skinPage);
        PageListVo<DrugVo> drugVoPageListVo=null;
        //过期
        if (reason.equals("过期")){
            String date=DateTimeUtil.getSysTime();
            map.put("date",date);
            drugVoPageListVo=drugService.selectDrugByDate(map);
        }

//        库存不足
        else {
            Integer stock=Integer.parseInt(num);
            map.put("stock",stock);
            drugVoPageListVo=drugService.selectDrugByStock(map);
        }
        return drugVoPageListVo;
    }

}
