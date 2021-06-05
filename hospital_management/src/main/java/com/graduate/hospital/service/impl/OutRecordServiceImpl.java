package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.DrugMapper;
import com.graduate.hospital.mapper.OutRecordsMapper;
import com.graduate.hospital.model.OutRecords;
import com.graduate.hospital.service.OutRecordService;
import com.graduate.hospital.vo.OutRecordsVo;
import com.graduate.hospital.vo.PageListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OutRecordServiceImpl implements OutRecordService {

    @Autowired
    OutRecordsMapper outRecordsMapper;
    @Autowired
    DrugMapper drugMapper;
    @Override

    /**
     *
     * 过期药品的删除时，添加出库记录
     */
    @Transactional
    public boolean addRecord(OutRecords outRecords) throws NullPointerException{
        int res=outRecordsMapper.insert(outRecords);
        if (res==1){
            //插入成功，删除药品表中的记录
            int delNum=drugMapper.deleteByPrimaryKey(outRecords.getDrugId());
            if (delNum==1){
                return true;
            }else {
                throw new NullPointerException();
            }
        }
        return false;
    }

    /**
     * 售卖药品时添加出库记录
     * @param outRecords
     * @return
     */
    @Override
    public boolean insertRecord(OutRecords outRecords) {
        int insert = outRecordsMapper.insert(outRecords);
        if (insert==1){
            return true;
        }
        return false;
    }

    /**
     * 根据条件分页查询符合条件的出库记录
     * @param map
     * @return
     */
    @Override
    public PageListVo<OutRecordsVo> selectRecordPageList(Map<String, Object> map) {
        int num=outRecordsMapper.getCount((OutRecords)map.get("outRecord"));
        log.info("记录总条数{}",num);
        List<OutRecordsVo> outRecordsVoList=outRecordsMapper.selectRecordPageList(map);
        PageListVo<OutRecordsVo> outRecordsVoPageListVo=new PageListVo<>();
        log.info("记录为{}",outRecordsVoList);
        outRecordsVoPageListVo.setCount(num);
        outRecordsVoPageListVo.setList(outRecordsVoList);
        return outRecordsVoPageListVo;
    }

    @Override
    public boolean delRecordByPrimaryKey(Integer recordId) {

        int i = outRecordsMapper.deleteByPrimaryKey(recordId);
        if (i==1){
            return true;
        }
        return false;

    }
}
