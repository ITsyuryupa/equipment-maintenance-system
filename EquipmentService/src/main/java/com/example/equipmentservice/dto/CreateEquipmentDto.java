package com.example.equipmentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию нового оборудования")
public class CreateEquipmentDto {

    @NotNull
    @Schema(
            description = "Идентификатор типа оборудования",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long equipmentTypeId;

    @NotBlank
    @Schema(
            description = "Наименование оборудования",
            example = "Ноутбук Dell Latitude 5520",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @NotBlank
    @Schema(
            description = "Уникальный инвентарный номер оборудования",
            example = "INV-2026-001",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String inventoryNumber;

    @Schema(
            description = "Местоположение оборудования",
            example = "Кабинет 305"
    )
    private String location;

}
