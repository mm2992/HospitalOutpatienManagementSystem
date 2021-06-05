package com.graduate.hospital.service.impl;

import com.graduate.hospital.mapper.ManufacturerMapper;
import com.graduate.hospital.model.Manufacturer;
import com.graduate.hospital.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    ManufacturerMapper manufacturerMapper;
    @Override
    public List<Manufacturer> getAllManufacturer() {
        List<Manufacturer> manufacturers=manufacturerMapper.getAllManufacturer();
        return manufacturers;
    }

    /**
     * 根据生产厂商名字查其id
     * @param maneName
     * @return
     */
    @Override
    public String getManeIdByName(String maneName) {
       String maneId= manufacturerMapper.getManeIdByName(maneName);
        return maneId;
    }
}
