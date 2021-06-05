package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.PositionMapper;
import com.graduate.hospital.model.Position;
import com.graduate.hospital.service.PositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Resource
    private PositionMapper positionMapper;
    @Override
    public List<Position> selectAllPosition() {
        List<Position> positionList=positionMapper.selectAllPosition();
        System.out.println(positionList.get(0));
        return positionList;
    }
}
