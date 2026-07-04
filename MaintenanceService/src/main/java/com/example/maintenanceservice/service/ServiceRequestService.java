package com.example.maintenanceservice.service;

import com.example.maintenanceservice.dto.CreateServiceRequestDto;
import com.example.maintenanceservice.dto.ServiceFullRequestResponse;
import com.example.maintenanceservice.dto.ServiceRequestResponseDto;
import com.example.maintenanceservice.dto.UpdateServiceRequestDto;
import com.example.maintenanceservice.model.Priority;
import com.example.maintenanceservice.model.ServiceRequestStatus;

import java.util.List;

public interface ServiceRequestService {

    ServiceFullRequestResponse create(CreateServiceRequestDto dto);

    ServiceRequestResponseDto findById(Long id);

    List<ServiceRequestResponseDto> findAll();

    ServiceRequestResponseDto update(Long id, UpdateServiceRequestDto dto);

    void delete(Long id);

    ServiceRequestResponseDto changeStatus(Long id, ServiceRequestStatus status);

    List<ServiceRequestResponseDto> findAll(
            ServiceRequestStatus status,
            Priority priority,
            Long equipmentId
    );


    public boolean hasActiveRequests(Long equipmentId);

}
