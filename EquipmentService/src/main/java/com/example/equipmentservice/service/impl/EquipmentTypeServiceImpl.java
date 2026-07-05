package com.example.equipmentservice.service.impl;

import com.example.equipmentservice.dto.CreateEquipmentTypeDto;
import com.example.equipmentservice.dto.EquipmentTypeResponse;
import com.example.equipmentservice.dto.UpdateEquipmentTypeDto;
import com.example.equipmentservice.entity.EquipmentType;
import com.example.equipmentservice.exception.BusinessException;
import com.example.equipmentservice.repository.EquipmentRepository;
import com.example.equipmentservice.repository.EquipmentTypeRepository;
import com.example.equipmentservice.service.EquipmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

    private final EquipmentTypeRepository equipmentTypeRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public EquipmentTypeResponse create(CreateEquipmentTypeDto dto) {

        if (equipmentTypeRepository.existsByNameAndDeletedFalse(dto.getName())) {
            throw new BusinessException("Тип оборудования уже существует");
        }

        EquipmentType equipmentType = new EquipmentType();

        equipmentType.setName(dto.getName());
        equipmentType.setDescription(dto.getDescription());
        equipmentType.setManufacturer(dto.getManufacturer());
        equipmentType.setMaintenanceIntervalDays(dto.getMaintenanceIntervalDays());

        equipmentType.setCreatedAt(LocalDateTime.now());
        equipmentType.setUpdatedAt(LocalDateTime.now());

        EquipmentType saved = equipmentTypeRepository.save(equipmentType);

        return toResponse(saved);
    }

    @Override
    public List<EquipmentTypeResponse> findAll() {

        return equipmentTypeRepository.findAllAndDeletedFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public EquipmentTypeResponse findById(Long id) {

        EquipmentType equipmentType = equipmentTypeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new BusinessException("Тип оборудования не найден"));

        return toResponse(equipmentType);
    }

    @Override
    public EquipmentTypeResponse update(Long id, UpdateEquipmentTypeDto dto) {

        EquipmentType equipmentType = equipmentTypeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new BusinessException("Тип оборудования не найден"));

        if (!equipmentType.getName().equals(dto.getName())
                && equipmentTypeRepository.existsByNameAndDeletedFalse(dto.getName())) {

            throw new BusinessException("Тип оборудования уже существует");
        }

        equipmentType.setName(dto.getName());
        equipmentType.setDescription(dto.getDescription());
        equipmentType.setManufacturer(dto.getManufacturer());
        equipmentType.setMaintenanceIntervalDays(dto.getMaintenanceIntervalDays());

        equipmentType.setUpdatedAt(LocalDateTime.now());

        EquipmentType saved = equipmentTypeRepository.save(equipmentType);

        return toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        Optional<EquipmentType> equipmentType = equipmentTypeRepository.findByIdAndDeletedFalse(id);
        if (!equipmentType.isPresent()) {
            throw new BusinessException("Тип оборудования не найден");
        }

        if (equipmentRepository.existsByEquipmentTypeIdAndDeletedFalse(id)) {
            throw new BusinessException(
                    "Нельзя удалить тип оборудования, к которому привязано оборудование"
            );
        }
        equipmentType.get().setDeleted(true);
        equipmentTypeRepository.save(equipmentType.get());
    }


    private EquipmentTypeResponse toResponse(EquipmentType equipmentType) {

        EquipmentTypeResponse response = new EquipmentTypeResponse();

        response.setId(equipmentType.getId());
        response.setName(equipmentType.getName());
        response.setDescription(equipmentType.getDescription());
        response.setManufacturer(equipmentType.getManufacturer());
        response.setMaintenanceIntervalDays(equipmentType.getMaintenanceIntervalDays());
        response.setCreatedAt(equipmentType.getCreatedAt());
        response.setUpdatedAt(equipmentType.getUpdatedAt());

        return response;
    }
}
