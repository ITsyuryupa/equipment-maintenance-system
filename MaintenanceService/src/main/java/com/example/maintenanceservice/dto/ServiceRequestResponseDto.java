package com.example.maintenanceservice.dto;

import com.example.maintenanceservice.model.Priority;
import com.example.maintenanceservice.model.ServiceRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Информация о заявке на обслуживание оборудования")
public class ServiceRequestResponseDto {

    @Schema(
            description = "Уникальный идентификатор заявки",
            example = "15"
    )
    private Long id;

    @Schema(
            description = "Идентификатор оборудования",
            example = "3"
    )
    private Long equipmentId;

    @Schema(
            description = "Краткое название проблемы",
            example = "Не включается"
    )
    private String title;

    @Schema(
            description = "Подробное описание проблемы",
            example = "После нажатия кнопки питания отсутствует индикация."
    )
    private String description;

    @Schema(
            description = "Текущий статус заявки",
            allowableValues = {
                    "NEW",
                    "IN_PROGRESS",
                    "DONE",
                    "CANCELLED"
            },
            example = "NEW"
    )
    private ServiceRequestStatus status;

    @Schema(
            description = "Приоритет заявки",
            allowableValues = {
                    "LOW",
                    "MEDIUM",
                    "HIGH"
            },
            example = "HIGH"
    )
    private Priority priority;

    @Schema(
            description = "Дата и время создания заявки",
            example = "2026-07-04T15:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Дата и время последнего обновления заявки",
            example = "2026-07-04T16:15:00"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Дата и время завершения заявки",
            example = "2026-07-05T10:20:00",
            nullable = true
    )
    private LocalDateTime completedAt;

}
