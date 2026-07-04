package com.example.maintenanceservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Информация о типе оборудования")
public class EquipmentTypeResponse {

    @Schema(
            description = "Уникальный идентификатор типа оборудования",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Наименование типа оборудования",
            example = "Ноутбук"
    )
    private String name;

    @Schema(
            description = "Описание типа оборудования",
            example = "Портативный персональный компьютер"
    )
    private String description;

    @Schema(
            description = "Производитель оборудования",
            example = "Dell"
    )
    private String manufacturer;

    @Schema(
            description = "Рекомендуемый интервал технического обслуживания в днях",
            example = "180"
    )
    private Integer maintenanceIntervalDays;

    @Schema(
            description = "Дата и время создания типа оборудования",
            example = "2026-07-04T10:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Дата и время последнего обновления типа оборудования",
            example = "2026-07-10T15:45:00"
    )
    private LocalDateTime updatedAt;

}
