package com.impacto.repository;
import com.impacto.entity.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.math.BigDecimal;
public interface ResourceRepository extends JpaRepository<Resource, UUID> {
    Page<Resource> findByStatus(ResourceStatus status, Pageable pageable);
    long countByStatus(ResourceStatus status);
}
