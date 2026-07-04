package com.example.maintenanceservice.rest;

import com.example.maintenanceservice.dto.EquipmentStatus;
import com.example.maintenanceservice.exception.BusinessException;
import com.example.maintenanceservice.rest.dto.EquipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class EquipmentClient {

    private final RestClient restClient;
    @Qualifier("equipmentServiceProperties")
    private final EquipmentServiceProperties properties;

    public EquipmentResponse getEquipment(Long id) {
        try {
            return restClient.get()
                    .uri(properties.getUrl() + "/internal/equipment/{id}", id)
                    .retrieve()
                    .body(EquipmentResponse.class);
        } catch (Exception ex) {
            throw new BusinessException("Оборудование не найдено");
        }
    }

    public void changeStatus(Long equipmentId, EquipmentStatus status) {

        restClient.patch()
                .uri(properties.getUrl()
                                + "/internal/equipment/{id}/status?status={status}",
                        equipmentId,
                        status)
                .retrieve()
                .toBodilessEntity();
    }

}


