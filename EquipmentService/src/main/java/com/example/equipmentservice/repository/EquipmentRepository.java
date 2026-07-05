package com.example.equipmentservice.repository;


import com.example.equipmentservice.dto.EquipmentResponse;
import com.example.equipmentservice.entity.Equipment;
import com.example.equipmentservice.entity.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;



public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    boolean existsByInventoryNumberAndDeletedFalse(String inventoryNumber);

    List<Equipment> findByEquipmentTypeIdAndDeletedFalse(Long equipmentTypeId);

    Optional<Equipment> findByInventoryNumberAndDeletedFalse(String inventoryNumber);

    List<Equipment> findByStatusAndDeletedFalse(EquipmentStatus status);

    boolean existsByEquipmentTypeIdAndDeletedFalse(Long id);

    Optional<Equipment> findByIdAndDeletedFalse(Long id);

    @Query(value = """
            SELECT *
            FROM equipment.equipment
            WHERE deleted = false
            """, nativeQuery = true)
    List<Equipment> findAllAndDeletedFalse();
}