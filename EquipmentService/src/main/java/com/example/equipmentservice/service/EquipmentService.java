package com.example.equipmentservice.service;

import com.example.equipmentservice.dto.CreateEquipmentDto;
import com.example.equipmentservice.dto.EquipmentResponse;
import com.example.equipmentservice.dto.UpdateEquipmentDto;
import com.example.equipmentservice.entity.EquipmentStatus;

import java.util.List;

public interface EquipmentService {

    EquipmentResponse create(CreateEquipmentDto dto);

    List<EquipmentResponse> findAll(Long equipmentTypeId);

    EquipmentResponse findById(Long id);

    EquipmentResponse update(Long id, UpdateEquipmentDto dto);

    void delete(Long id);

    void changeStatus(Long id, EquipmentStatus dto);
}
