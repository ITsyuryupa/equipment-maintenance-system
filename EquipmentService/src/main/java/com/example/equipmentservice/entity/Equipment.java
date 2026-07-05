package com.example.equipmentservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "equipment", schema = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipment_type_id")
    private Long equipmentTypeId;

    private String name;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    private String location;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "decommissioned_at")
    private LocalDateTime decommissionedAt;

    @Column(nullable = false)
    private Boolean deleted = false;

}
