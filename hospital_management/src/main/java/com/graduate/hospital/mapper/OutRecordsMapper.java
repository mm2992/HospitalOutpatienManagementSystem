package com.graduate.hospital.mapper;

import com.graduate.hospital.model.OutRecords;
import com.graduate.hospital.vo.OutRecordsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface OutRecordsMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(OutRecords record);

    int insertSelective(OutRecords record);

    OutRecords selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(OutRecords record);

    int updateByPrimaryKey(OutRecords record);

    int getCount(OutRecords outRecord);

    List<OutRecordsVo> selectRecordPageList(Map<String, Object> map);
}