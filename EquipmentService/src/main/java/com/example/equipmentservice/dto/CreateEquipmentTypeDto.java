package com.example.equipmentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание нового типа оборудования")
public class CreateEquipmentTypeDto {

    @NotBlank
    @Schema(
            description = "Наименование типа оборудования",
            example = "Ноутбук",
            requiredMode = Schema.RequiredMode.REQUIRED
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

    @Min(1)
    @Schema(
            description = "Периодичность технического обслуживания в днях",
            example = "180",
            minimum = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer maintenanceIntervalDays;

}
