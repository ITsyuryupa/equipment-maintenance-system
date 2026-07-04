package com.example.equipmentservice.repository;

import com.example.equipmentservice.entity.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {

    Optional<EquipmentType> findByName(String name);

    boolean existsByName(String name);

}
