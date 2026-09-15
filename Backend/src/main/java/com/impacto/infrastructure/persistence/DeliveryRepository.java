package com.impacto.infrastructure.persistence;
import com.impacto.domain.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.math.BigDecimal;
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    Page<Delivery> findByVolunteerId(UUID volunteerId, Pageable pageable);
    long countByStatus(DeliveryStatus status);
    boolean existsByAssignmentIdAndStatus(UUID assignmentId, DeliveryStatus status);
}
