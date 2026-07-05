package com.example.equipmentservice.repository;

import com.example.equipmentservice.dto.EquipmentResponse;
import com.example.equipmentservice.entity.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {

    Optional<EquipmentType> findByNameAndDeletedFalse(String name);

    boolean existsByNameAndDeletedFalse(String name);

    Optional<EquipmentType> findByIdAndDeletedFalse(Long id);

    boolean existsByIdAndDeletedFalse(Long id);

    @Query(value = """
            SELECT *
            FROM equipment.equipment_type
            WHERE deleted = false
            """, nativeQuery = true)
    List<EquipmentType> findAllAndDeletedFalse();

}
