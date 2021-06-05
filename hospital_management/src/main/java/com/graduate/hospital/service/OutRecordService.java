package com.graduate.hospital.service;

import com.graduate.hospital.model.OutRecords;
import com.graduate.hospital.vo.OutRecordsVo;
import com.graduate.hospital.vo.PageListVo;

import java.util.Map;

public interface OutRecordService {
    boolean addRecord(OutRecords outRecords);

    boolean insertRecord(OutRecords outRecords);

    PageListVo<OutRecordsVo> selectRecordPageList(Map<String, Object> map);

    boolean delRecordByPrimaryKey(Integer recordId);
}
