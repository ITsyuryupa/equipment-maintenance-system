package com.example.equipmentservice.controller;

import com.example.equipmentservice.dto.CreateEquipmentTypeDto;
import com.example.equipmentservice.dto.EquipmentTypeResponse;
import com.example.equipmentservice.dto.UpdateEquipmentTypeDto;
import com.example.equipmentservice.service.EquipmentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/equipment-types")
@RequiredArgsConstructor
@Tag(
        name = "Equipment Types",
        description = "API для управления типами оборудования"
)
public class EquipmentTypeController {

    private final EquipmentTypeService equipmentTypeService;

    @PostMapping
    @Operation(
            summary = "Создать тип оборудования",
            description = "Создает новый тип оборудования."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тип оборудования успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    })
    public EquipmentTypeResponse create(
            @RequestBody @Valid CreateEquipmentTypeDto dto) {

        return equipmentTypeService.create(dto);
    }

    @GetMapping
    @Operation(
            summary = "Получить список типов оборудования",
            description = "Возвращает список всех типов оборудования."
    )
    @ApiResponse(responseCode = "200", description = "Список типов оборудования успешно получен")
    public List<EquipmentTypeResponse> findAll() {

        return equipmentTypeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить тип оборудования по идентификатору",
            description = "Возвращает информацию о типе оборудования."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тип оборудования найден"),
            @ApiResponse(responseCode = "404", description = "Тип оборудования не найден")
    })
    public EquipmentTypeResponse findById(
            @Parameter(
                    description = "Идентификатор типа оборудования",
                    example = "1"
            )
            @PathVariable Long id) {

        return equipmentTypeService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить тип оборудования",
            description = "Обновляет информацию о типе оборудования."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тип оборудования успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
            @ApiResponse(responseCode = "404", description = "Тип оборудования не найден")
    })
    public EquipmentTypeResponse update(
            @Parameter(
                    description = "Идентификатор типа оборудования",
                    example = "1"
            )
            @PathVariable Long id,
            @RequestBody @Valid UpdateEquipmentTypeDto dto) {

        return equipmentTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить тип оборудования",
            description = "Удаляет тип оборудования из системы."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тип оборудования успешно удален"),
            @ApiResponse(responseCode = "404", description = "Тип оборудования не найден"),
            @ApiResponse(responseCode = "409", description = "Удаление невозможно, так как существуют связанные записи оборудования")
    })
    public void delete(
            @Parameter(
                    description = "Идентификатор типа оборудования",
                    example = "1"
            )
            @PathVariable Long id) {

        equipmentTypeService.delete(id);
    }

}