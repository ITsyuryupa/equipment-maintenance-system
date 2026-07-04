package com.example.equipmentservice.service;

import com.example.equipmentservice.dto.CreateEquipmentTypeDto;
import com.example.equipmentservice.dto.EquipmentTypeResponse;
import com.example.equipmentservice.dto.UpdateEquipmentTypeDto;

import java.util.List;

public interface EquipmentTypeService {

    EquipmentTypeResponse create(CreateEquipmentTypeDto dto);

    List<EquipmentTypeResponse> findAll();

    EquipmentTypeResponse findById(Long id);

    EquipmentTypeResponse update(Long id, UpdateEquipmentTypeDto dto);

    void delete(Long id);

}
