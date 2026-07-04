package com.example.maintenanceservice.rest.dto;

import com.example.maintenanceservice.dto.EquipmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Информация об оборудовании")
public class EquipmentResponse {

    @Schema(
            description = "Уникальный идентификатор оборудования",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Информация о типе оборудования"
    )
    private EquipmentTypeResponse equipmentType;

    @Schema(
            description = "Наименование оборудования",
            example = "Ноутбук Dell Latitude 5520"
    )
    private String name;

    @Schema(
            description = "Инвентарный номер оборудования",
            example = "INV-2025-001"
    )
    private String inventoryNumber;

    @Schema(
            description = "Местоположение оборудования",
            example = "Кабинет 305"
    )
    private String location;

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
            example = "AVAILABLE"
    )
    private EquipmentStatus status;

    @Schema(
            description = "Дата и время регистрации оборудования",
            example = "2026-07-04T10:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Дата и время списания оборудования. Заполняется только для списанного оборудования",
            example = "2026-08-15T14:20:00",
            nullable = true
    )
    private LocalDateTime decommissionedAt;

}
