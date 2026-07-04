package com.example.equipmentservice.controller;

import com.example.equipmentservice.dto.CreateEquipmentDto;
import com.example.equipmentservice.dto.EquipmentResponse;
import com.example.equipmentservice.dto.UpdateEquipmentDto;
import com.example.equipmentservice.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
@Tag(
        name = "Equipment",
        description = "API для управления оборудованием"
)
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    @Operation(
            summary = "Создать оборудование",
            description = "Регистрирует новое оборудование в системе."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оборудование успешно создано"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    })
    public EquipmentResponse create(
            @RequestBody @Valid CreateEquipmentDto dto) {

        return equipmentService.create(dto);
    }

    @GetMapping
    @Operation(
            summary = "Получить список оборудования",
            description = "Возвращает список всего оборудования. При указании идентификатора типа выполняется фильтрация."
    )
    @ApiResponse(responseCode = "200", description = "Список оборудования успешно получен")
    public List<EquipmentResponse> findAll(
            @Parameter(
                    description = "Идентификатор типа оборудования для фильтрации",
                    example = "1"
            )
            @RequestParam(required = false) Long equipmentTypeId) {

        return equipmentService.findAll(equipmentTypeId);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить оборудование по идентификатору",
            description = "Возвращает информацию об оборудовании."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оборудование найдено"),
            @ApiResponse(responseCode = "404", description = "Оборудование не найдено")
    })
    public EquipmentResponse findById(
            @Parameter(
                    description = "Идентификатор оборудования",
                    example = "1"
            )
            @PathVariable Long id) {

        return equipmentService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить оборудование",
            description = "Обновляет информацию об оборудовании."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оборудование успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
            @ApiResponse(responseCode = "404", description = "Оборудование не найдено")
    })
    public EquipmentResponse update(
            @Parameter(
                    description = "Идентификатор оборудования",
                    example = "1"
            )
            @PathVariable Long id,
            @RequestBody @Valid UpdateEquipmentDto dto) {

        return equipmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить оборудование",
            description = "Удаляет оборудование из системы."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оборудование успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Оборудование не найдено"),
            @ApiResponse(responseCode = "409", description = "Удаление невозможно, так как существуют активные заявки на обслуживание")
    })
    public void delete(
            @Parameter(
                    description = "Идентификатор оборудования",
                    example = "1"
            )
            @PathVariable Long id) {

        equipmentService.delete(id);
    }

}