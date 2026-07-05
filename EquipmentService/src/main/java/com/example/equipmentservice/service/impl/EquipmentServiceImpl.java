package com.example.equipmentservice.service.impl;

import com.example.equipmentservice.client.MaintenanceClient;
import com.example.equipmentservice.dto.*;
import com.example.equipmentservice.entity.Equipment;
import com.example.equipmentservice.entity.EquipmentStatus;
import com.example.equipmentservice.entity.EquipmentType;
import com.example.equipmentservice.exception.BusinessException;
import com.example.equipmentservice.repository.EquipmentRepository;
import com.example.equipmentservice.repository.EquipmentTypeRepository;
import com.example.equipmentservice.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final MaintenanceClient maintenanceClient;


    @Override
    public EquipmentResponse create(CreateEquipmentDto dto) {

        if (!equipmentTypeRepository.existsByIdAndDeletedFalse(dto.getEquipmentTypeId())) {
            throw new BusinessException("Тип оборудования не найден");
        }

        if (equipmentRepository.existsByInventoryNumberAndDeletedFalse(dto.getInventoryNumber())) {
            throw new BusinessException("Инвентарный номер уже существует");
        }

        Equipment equipment = new Equipment();

        equipment.setEquipmentTypeId(dto.getEquipmentTypeId());
        equipment.setName(dto.getName());
        equipment.setInventoryNumber(dto.getInventoryNumber());
        equipment.setLocation(dto.getLocation());

        equipment.setStatus(EquipmentStatus.AVAILABLE);
        equipment.setCreatedAt(LocalDateTime.now());

        Equipment saved = equipmentRepository.save(equipment);

        return toResponse(saved);
    }

    private EquipmentResponse toResponse(Equipment equipment) {

        EquipmentType equipmentType = equipmentTypeRepository.findByIdAndDeletedFalse(equipment.getEquipmentTypeId())
                .orElseThrow(() -> new BusinessException("Тип оборудования не найден"));

        EquipmentTypeResponse typeResponse = new EquipmentTypeResponse();

        typeResponse.setId(equipmentType.getId());
        typeResponse.setName(equipmentType.getName());
        typeResponse.setDescription(equipmentType.getDescription());
        typeResponse.setManufacturer(equipmentType.getManufacturer());
        typeResponse.setMaintenanceIntervalDays(equipmentType.getMaintenanceIntervalDays());
        typeResponse.setCreatedAt(equipmentType.getCreatedAt());
        typeResponse.setUpdatedAt(equipmentType.getUpdatedAt());

        EquipmentResponse response = new EquipmentResponse();

        response.setId(equipment.getId());
        response.setEquipmentType(typeResponse);
        response.setName(equipment.getName());
        response.setInventoryNumber(equipment.getInventoryNumber());
        response.setLocation(equipment.getLocation());
        response.setStatus(equipment.getStatus());
        response.setCreatedAt(equipment.getCreatedAt());
        response.setDecommissionedAt(equipment.getDecommissionedAt());

        return response;
    }

    @Override
    public List<EquipmentResponse> findAll(Long equipmentTypeId) {

        List<Equipment> equipment;

        if (equipmentTypeId == null) {
            equipment = equipmentRepository.findAllAndDeletedFalse();
        } else {
            equipment = equipmentRepository.findByEquipmentTypeIdAndDeletedFalse(equipmentTypeId);
        }

        return equipment.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public EquipmentResponse findById(Long id) {

        Equipment equipment = equipmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new BusinessException("Оборудование не найдено"));

        return toResponse(equipment);
    }

    @Override
    public EquipmentResponse update(Long id, UpdateEquipmentDto dto) {

        Equipment equipment = equipmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new BusinessException("Оборудование не найдено"));

        if (!equipmentTypeRepository.existsByIdAndDeletedFalse(dto.getEquipmentTypeId())) {
            throw new BusinessException("Тип оборудования не найден");
        }

        Optional<Equipment> equipmentWithInventory =
                equipmentRepository.findByInventoryNumberAndDeletedFalse(dto.getInventoryNumber());

        if (equipmentWithInventory.isPresent()
                && !equipmentWithInventory.get().getId().equals(id)) {

            throw new BusinessException("Инвентарный номер уже существует");
        }

        if (equipment.getStatus() == EquipmentStatus.UNDER_MAINTENANCE
                && dto.getStatus() == EquipmentStatus.DECOMMISSIONED) {

            throw new BusinessException(
                    "Нельзя списать оборудование, находящееся на обслуживании");
        }

        equipment.setEquipmentTypeId(dto.getEquipmentTypeId());
        equipment.setName(dto.getName());
        equipment.setInventoryNumber(dto.getInventoryNumber());
        equipment.setLocation(dto.getLocation());

        if (dto.getStatus() == EquipmentStatus.DECOMMISSIONED
                && equipment.getStatus() != EquipmentStatus.DECOMMISSIONED) {

            equipment.setDecommissionedAt(LocalDateTime.now());
        }

        equipment.setStatus(dto.getStatus());

        Equipment saved = equipmentRepository.save(equipment);

        return toResponse(saved);
    }

    @Override
    public void changeStatus(Long id, EquipmentStatus status) {

        Equipment equipment = equipmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new BusinessException("Оборудование не найдено"));

        if (equipment.getStatus() == EquipmentStatus.UNDER_MAINTENANCE
                && status == EquipmentStatus.DECOMMISSIONED) {
            throw new BusinessException(
                    "Нельзя списать оборудование, находящееся на обслуживании");
        }

        if (status == EquipmentStatus.DECOMMISSIONED
                && equipment.getStatus() != EquipmentStatus.DECOMMISSIONED) {

            equipment.setDecommissionedAt(LocalDateTime.now());
        }

        equipment.setStatus(status);

        equipmentRepository.save(equipment);
    }

    @Override
    public void delete(Long id) {

        Equipment equipment = equipmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new BusinessException("Оборудование не найдено"));

        if (maintenanceClient.hasActiveRequests(id)) {
            throw new BusinessException(
                    "Нельзя удалить оборудование, по которому есть активные заявки");
        }

        equipment.setDeleted(true);
        equipmentRepository.save(equipment);
    }

}
