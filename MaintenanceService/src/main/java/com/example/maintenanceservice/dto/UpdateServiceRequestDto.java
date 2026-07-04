package com.example.maintenanceservice.dto;

import com.example.maintenanceservice.model.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос на обновление заявки на обслуживание оборудования")
public class UpdateServiceRequestDto {

    @NotBlank
    @Schema(
            description = "Краткое название проблемы",
            example = "Не включается",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Подробное описание проблемы",
            example = "После нажатия кнопки питания отсутствует индикация."
    )
    private String description;

    @NotNull
    @Schema(
            description = "Приоритет заявки",
            allowableValues = {
                    "LOW",
                    "MEDIUM",
                    "HIGH"
            },
            example = "MEDIUM",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Priority priority;

}
