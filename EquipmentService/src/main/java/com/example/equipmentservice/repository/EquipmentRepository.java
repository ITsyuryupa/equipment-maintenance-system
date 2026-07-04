package com.example.equipmentservice.repository;


import com.example.equipmentservice.entity.Equipment;
import com.example.equipmentservice.entity.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;



public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    boolean existsByInventoryNumber(String inventoryNumber);

    List<Equipment> findByEquipmentTypeId(Long equipmentTypeId);

    Optional<Equipment> findByInventoryNumber(String inventoryNumber);

    List<Equipment> findByStatus(EquipmentStatus status);

    boolean existsByEquipmentTypeId(Long id);
}