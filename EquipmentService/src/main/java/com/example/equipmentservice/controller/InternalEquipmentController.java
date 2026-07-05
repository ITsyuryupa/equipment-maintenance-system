package com.example.equipmentservice.controller;


import com.example.equipmentservice.dto.EquipmentResponse;

import com.example.equipmentservice.entity.EquipmentStatus;
import com.example.equipmentservice.service.EquipmentService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/equipment")
@RequiredArgsConstructor
@Hidden
public class InternalEquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping("/{id}")
    public EquipmentResponse getEquipment(@PathVariable Long id) {
        return equipmentService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public void changeStatus(@PathVariable Long id,
                             @RequestParam @Valid EquipmentStatus status) {
        equipmentService.changeStatus(id, status);
    }

}
