package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.DrugMapper;
import com.graduate.hospital.model.Drug;
import com.graduate.hospital.service.DrugService;
import com.graduate.hospital.vo.DrugVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DrugServiceImpl implements DrugService {

    @Autowired
    DrugMapper drugMapper;

    @Override
    public String enterDrug(Drug drug) {
        Drug drug1=drugMapper.selectByPrimaryKey(drug.getDrugId());
        System.out.println("该药品是否已经存在："+drug1);
        if (drug1==null){
            System.out.println("service层的药品信息:"+drug);
            int res=drugMapper.insert(drug);
            if (res==1){
                return "添加成功";
            }
            return "添加失败";
        }

        return "药品已经存在";


    }

//    分页查询
    @Override
    public PageListVo<DrugVo> getAllDrug(Map map) {
        //查询总记录条数
        int count=drugMapper.getCountSelected((Drug)map.get("drug"));
        System.out.println("总记录条数为："+count);
        PageListVo<DrugVo> drugPageListVo=new PageListVo<>();
        drugPageListVo.setCount(count);
        //查询所有符合条件的药品
        List<DrugVo> drugVoList=drugMapper.getDrugsSelected(map);
        System.out.println(drugVoList);

        drugPageListVo.setList(drugVoList);
        return drugPageListVo;
    }

//    查询单条数据

    @Override
    public DrugVo getDrugById(String drugId) {
        DrugVo drugVo = drugMapper.getOneDrugVo(drugId);
        return drugVo;
    }

    /**
     * 按日期查过期药品
     * @param map
     * @return
     */
    @Override
    public PageListVo<DrugVo> selectDrugByDate(Map<String, Object> map) {
        //先查总条数
        PageListVo<DrugVo> drugVoPageListVo=new PageListVo<>();
        int count=drugMapper.getCountByDate((String)map.get("date"));
        log.info("过期的药品数量为：{}",count);
        List<DrugVo> drugVos=drugMapper.selectByDate(map);
        log.info("过期药品为：{}",drugVos);
        drugVoPageListVo.setCount(count);
        drugVoPageListVo.setList(drugVos);
        return drugVoPageListVo;
    }

    /**
     * 查余量不足的药品
     * @param map
     * @return
     */
    @Override
    public PageListVo<DrugVo> selectDrugByStock(Map<String, Object> map) {
        PageListVo<DrugVo> drugVoPageListVo=new PageListVo<>();
        int count=drugMapper.getCountByStock((Integer)map.get("stock"));
        log.info("库存数量不足的药品共：{}",count);
        List<DrugVo> drugVos=drugMapper.selectByStock(map);
        log.info("库存不足的药品有：{}",drugVos);
        drugVoPageListVo.setCount(count);
        drugVoPageListVo.setList(drugVos);
        return drugVoPageListVo;
    }


    /**
     * 药品售卖时减少库存
     * @param drugId
     * @param quantity
     * @return
     */
    @Override
    public boolean updateStock(String drugId, Integer quantity) {
//        先查出该药品的库存量
        int num=drugMapper.getStockById(drugId);
        if (num<quantity) { //库存不够
            return false;
        }

        int i=drugMapper.updateStock(drugId,num-quantity);
        if (i==1){
            return true;
        }

        return false;

    }

    /**
     * 查询原生药品信息
     * @param drugId
     * @return
     */
    @Override
    public Drug getDrug(String drugId) {
        Drug drug = drugMapper.selectByPrimaryKey(drugId);
        return drug;
    }
}
