package com.example.equipmentservice.dto;

import com.example.equipmentservice.entity.EquipmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление информации об оборудовании")
public class UpdateEquipmentDto {

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

    @NotNull
    @Schema(
            description = "Текущий статус оборудования. " +
                    "AVAILABLE — доступно для использования, " +
                    "UNDER_MAINTENANCE — находится на техническом обслуживании, " +
                    "DECOMMISSIONED — списано",
            allowableValues = {
                    "AVAILABLE",
                    "UNDER_MAINTENANCE",
                    "DECOMMISSIONED"
            },
            example = "AVAILABLE",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private EquipmentStatus status;

}
