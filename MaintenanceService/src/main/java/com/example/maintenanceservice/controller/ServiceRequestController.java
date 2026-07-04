package com.example.maintenanceservice.controller;

import com.example.maintenanceservice.dto.CreateServiceRequestDto;
import com.example.maintenanceservice.dto.ServiceFullRequestResponse;
import com.example.maintenanceservice.dto.ServiceRequestResponseDto;
import com.example.maintenanceservice.dto.UpdateServiceRequestDto;
import com.example.maintenanceservice.model.Priority;
import com.example.maintenanceservice.model.ServiceRequestStatus;
import com.example.maintenanceservice.service.ServiceRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
@RequiredArgsConstructor
@Tag(
        name = "Service Requests",
        description = "Управление заявками на обслуживание оборудования"
)
public class ServiceRequestController {

    private final ServiceRequestService service;

    @PostMapping
    @Operation(
            summary = "Создать заявку",
            description = "Создает новую заявку на обслуживание оборудования"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заявка успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Оборудование не найдено")
    })
    public ServiceFullRequestResponse create(
            @Valid @RequestBody CreateServiceRequestDto dto
    ) {
        return service.create(dto);
    }

    @GetMapping
    @Operation(
            summary = "Получить список заявок",
            description = "Возвращает список всех заявок"
    )
    public List<ServiceRequestResponseDto> findAll(

            @RequestParam(required = false)
            ServiceRequestStatus status,

            @RequestParam(required = false)
            Priority priority,

            @RequestParam(required = false)
            Long equipmentId

    ) {

        return service.findAll(status, priority, equipmentId);

    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить заявку",
            description = "Возвращает заявку по идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Заявка найдена"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена")
    })
    public ServiceRequestResponseDto findById(
            @PathVariable Long id
    ) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить заявку",
            description = "Обновляет данные существующей заявки"
    )
    public ServiceRequestResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateServiceRequestDto dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить заявку",
            description = "Помечает заявку как удаленную"
    )
    public void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Изменить статус заявки",
            description = "Изменяет статус заявки"
    )
    public ServiceRequestResponseDto changeStatus(
            @PathVariable Long id,
            @RequestParam ServiceRequestStatus status
    ) {
        return service.changeStatus(id, status);
    }

}
