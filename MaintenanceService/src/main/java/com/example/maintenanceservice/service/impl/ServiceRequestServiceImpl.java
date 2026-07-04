package com.example.maintenanceservice.service.impl;

import com.example.maintenanceservice.dto.*;
import com.example.maintenanceservice.exception.BusinessException;
import com.example.maintenanceservice.exception.NotFoundException;
import com.example.maintenanceservice.model.Priority;
import com.example.maintenanceservice.model.ServiceRequest;
import com.example.maintenanceservice.model.ServiceRequestStatus;
import com.example.maintenanceservice.repository.ServiceRequestRepository;
import com.example.maintenanceservice.rest.EquipmentClient;
import com.example.maintenanceservice.rest.dto.EquipmentResponse;
import com.example.maintenanceservice.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository repository;
    private final EquipmentClient equipmentClient;

    @Override
    public ServiceFullRequestResponse create(CreateServiceRequestDto dto) {

        EquipmentResponse equipment =
                equipmentClient.getEquipment(dto.getEquipmentId());

        if (equipment.getStatus().equals(EquipmentStatus.DECOMMISSIONED.toString())) {
            throw new BusinessException(
                    "Нельзя создать заявку на списанное оборудование"
            );
        }

        ServiceRequest request = new ServiceRequest();

        request.setEquipmentId(dto.getEquipmentId());
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setPriority(dto.getPriority());

        request.setStatus(ServiceRequestStatus.NEW);

        ServiceRequest saved = repository.save(request);

        ServiceFullRequestResponse response = new ServiceFullRequestResponse();

        response.setId(saved.getId());

        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setPriority(saved.getPriority());
        response.setStatus(saved.getStatus());
        response.setCreatedAt(saved.getCreatedAt());
        response.setUpdatedAt(saved.getUpdatedAt());
        response.setCompletedAt(saved.getCompletedAt());
        response.setEquipment(equipment);
        equipmentClient.changeStatus(
                saved.getEquipmentId(),
                EquipmentStatus.UNDER_MAINTENANCE
        );
        return response;
    }

    @Override
    public ServiceRequestResponseDto findById(Long id) {

        ServiceRequest request = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        ServiceRequestResponseDto response = new ServiceRequestResponseDto();

        response.setId(request.getId());
        response.setEquipmentId(request.getEquipmentId());
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        response.setPriority(request.getPriority());
        response.setStatus(request.getStatus());
        response.setCreatedAt(request.getCreatedAt());
        response.setUpdatedAt(request.getUpdatedAt());
        response.setCompletedAt(request.getCompletedAt());

        return response;
    }

    @Override
    public List<ServiceRequestResponseDto> findAll() {

        return repository.findAll()
                .stream()
                .map(request -> {
                    ServiceRequestResponseDto response = new ServiceRequestResponseDto();

                    response.setId(request.getId());
                    response.setEquipmentId(request.getEquipmentId());
                    response.setTitle(request.getTitle());
                    response.setDescription(request.getDescription());
                    response.setStatus(request.getStatus());
                    response.setPriority(request.getPriority());
                    response.setCreatedAt(request.getCreatedAt());
                    response.setUpdatedAt(request.getUpdatedAt());
                    response.setCompletedAt(request.getCompletedAt());

                    return response;
                })
                .toList();
    }

    @Override
    public ServiceRequestResponseDto update(Long id, UpdateServiceRequestDto dto) {

        ServiceRequest request = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        if (request.getStatus() == ServiceRequestStatus.DONE) {
            throw new BusinessException("Заявка завершена и не может быть изменена");
        }
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setPriority(dto.getPriority());

        ServiceRequest updated = repository.save(request);

        ServiceRequestResponseDto response = new ServiceRequestResponseDto();

        response.setId(updated.getId());
        response.setEquipmentId(updated.getEquipmentId());
        response.setTitle(updated.getTitle());
        response.setDescription(updated.getDescription());
        response.setStatus(updated.getStatus());
        response.setPriority(updated.getPriority());
        response.setCreatedAt(updated.getCreatedAt());
        response.setUpdatedAt(updated.getUpdatedAt());
        response.setCompletedAt(updated.getCompletedAt());

        return response;
    }

    @Override
    public void delete(Long id) {

        ServiceRequest request = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        if (request.getStatus() == ServiceRequestStatus.IN_PROGRESS) {
            throw new BusinessException(
                    "Нельзя удалить заявку в работе"
            );
        }

        repository.delete(request);
    }

    @Override
    public ServiceRequestResponseDto changeStatus(Long id, ServiceRequestStatus status) {

        ServiceRequest request = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        if (request.getStatus() == ServiceRequestStatus.DONE
                && status == ServiceRequestStatus.IN_PROGRESS) {

            throw new BusinessException(
                    "Нельзя перевести заявку из DONE в IN_PROGRESS"
            );
        }
        if (request.getStatus() == ServiceRequestStatus.CANCELLED
                && status == ServiceRequestStatus.IN_PROGRESS) {

            throw new BusinessException(
                    "Нельзя перевести заявку из CANCELLED в IN_PROGRESS"
            );
        }
        if (request.getStatus() == ServiceRequestStatus.DONE) {
            throw new BusinessException("Заявка завершена и не может быть изменена");
        }
        request.setStatus(status);

        if (status == ServiceRequestStatus.DONE) {
            request.setCompletedAt(LocalDateTime.now());
        }

        ServiceRequest updated = repository.save(request);

        ServiceRequestResponseDto response = new ServiceRequestResponseDto();

        response.setId(updated.getId());
        response.setEquipmentId(updated.getEquipmentId());
        response.setTitle(updated.getTitle());
        response.setDescription(updated.getDescription());
        response.setPriority(updated.getPriority());
        response.setStatus(updated.getStatus());
        response.setCreatedAt(updated.getCreatedAt());
        response.setUpdatedAt(updated.getUpdatedAt());
        response.setCompletedAt(updated.getCompletedAt());

        return response;
    }

    @Override
    public List<ServiceRequestResponseDto> findAll(
            ServiceRequestStatus status,
            Priority priority,
            Long equipmentId
    ) {

        List<ServiceRequest> requests;

        if (status != null && priority != null) {
            requests = repository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            requests = repository.findByStatus(status);
        } else if (priority != null) {
            requests = repository.findByPriority(priority);
        } else if (equipmentId != null) {
            requests = repository.findByEquipmentId(equipmentId);
        } else {
            requests = repository.findAll();
        }

        return requests.stream()
                .map(request -> {
                    ServiceRequestResponseDto response = new ServiceRequestResponseDto();

                    response.setId(request.getId());
                    response.setEquipmentId(request.getEquipmentId());
                    response.setTitle(request.getTitle());
                    response.setDescription(request.getDescription());
                    response.setStatus(request.getStatus());
                    response.setPriority(request.getPriority());
                    response.setCreatedAt(request.getCreatedAt());
                    response.setUpdatedAt(request.getUpdatedAt());
                    response.setCompletedAt(request.getCompletedAt());

                    return response;
                })
                .toList();
    }

    @Override
    public boolean hasActiveRequests(Long equipmentId) {

//        return repository.existsByEquipmentIdAndStatusIn(
//                equipmentId,
//                List.of(
//                        ServiceRequestStatus.NEW,
//                        ServiceRequestStatus.IN_PROGRESS
//                )
//        );

        return repository.hasActiveRequests(equipmentId);

    }
}
