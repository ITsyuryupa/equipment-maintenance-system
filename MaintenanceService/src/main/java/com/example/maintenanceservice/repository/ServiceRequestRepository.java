package com.example.maintenanceservice.repository;

import com.example.maintenanceservice.model.Priority;
import com.example.maintenanceservice.model.ServiceRequest;
import com.example.maintenanceservice.model.ServiceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceRequestRepository
        extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByStatus(ServiceRequestStatus status);

    List<ServiceRequest> findByPriority(Priority priority);

    List<ServiceRequest> findByEquipmentId(Long equipmentId);

    List<ServiceRequest> findByStatusAndPriority(
            ServiceRequestStatus status,
            Priority priority
    );

    boolean existsByEquipmentIdAndStatusIn(
            Long equipmentId,
            List<ServiceRequestStatus> statuses
    );

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM maintenance.service_request
                WHERE equipment_id = :equipmentId
                  AND deleted = false
                  AND status IN ('NEW', 'IN_PROGRESS')
            )
            """, nativeQuery = true)
    boolean hasActiveRequests(@Param("equipmentId") Long equipmentId);

    Optional<ServiceRequest> findByIdAndDeletedFalse(Long id);

    List<ServiceRequest> findAllByDeletedFalse();
}
