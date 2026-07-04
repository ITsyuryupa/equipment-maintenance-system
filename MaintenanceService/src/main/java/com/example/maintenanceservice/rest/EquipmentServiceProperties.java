package com.example.maintenanceservice.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "equipment-service")
@Data
public class EquipmentServiceProperties {

    private String url;

}
