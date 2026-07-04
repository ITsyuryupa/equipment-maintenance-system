package com.example.maintenanceservice.controller;

import com.example.maintenanceservice.service.ServiceRequestService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Внутренний API для взаимодействия с Equipment Service.
 */
@RestController
@RequestMapping("/internal/service-requests")
@RequiredArgsConstructor
@Hidden
public class InternalServiceRequestController {

    private final ServiceRequestService service;

    /**
     * Проверяет наличие активных заявок для указанного оборудования.
     *
     * @param equipmentId идентификатор оборудования
     * @return {@code true}, если существует хотя бы одна не удаленная заявка
     *         со статусом NEW или IN_PROGRESS, иначе {@code false}
     */
    @GetMapping("/active")
    public boolean hasActiveRequests(
            @RequestParam Long equipmentId
    ) {

        return service.hasActiveRequests(equipmentId);

    }
}
