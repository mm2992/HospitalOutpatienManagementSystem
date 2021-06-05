package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.LevelMapper;
import com.graduate.hospital.model.Level;
import com.graduate.hospital.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {
    @Autowired
    LevelMapper levelMapper;

    @Override
    public List<Level> getAllLevel() {
        List<Level> levelList=levelMapper.getAllLevel();
        return levelList;
    }
}
