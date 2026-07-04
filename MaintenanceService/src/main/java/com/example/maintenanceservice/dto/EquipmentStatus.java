package com.example.maintenanceservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус оборудования")
public enum EquipmentStatus {

    @Schema(description = "Оборудование доступно для использования")
    AVAILABLE,

    @Schema(description = "Оборудование находится на техническом обслуживании")
    UNDER_MAINTENANCE,

    @Schema(description = "Оборудование списано и не может использоваться")
    DECOMMISSIONED
}
