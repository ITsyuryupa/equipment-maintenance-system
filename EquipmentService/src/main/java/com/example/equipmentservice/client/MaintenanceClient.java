package com.example.equipmentservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class MaintenanceClient {

    private final RestClient restClient;

    @Value("${maintenance-service.url}")
    private String maintenanceServiceUrl;

    public boolean hasActiveRequests(Long equipmentId) {

        Boolean response = restClient.get()
                .uri(maintenanceServiceUrl +
                                "/internal/service-requests/active?equipmentId={equipmentId}",
                        equipmentId)
                .retrieve()
                .body(new ParameterizedTypeReference<Boolean>() {
                });

        return Boolean.TRUE.equals(response);
    }

}
