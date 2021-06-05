package com.graduate.hospital.service;

import com.graduate.hospital.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> getAllManufacturer();

    String getManeIdByName(String maneName);
}
