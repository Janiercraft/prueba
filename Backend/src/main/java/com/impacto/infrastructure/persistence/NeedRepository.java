package com.impacto.infrastructure.persistence;
import com.impacto.domain.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.math.BigDecimal;
public interface NeedRepository extends JpaRepository<Need, UUID>, JpaSpecificationExecutor<Need> {
    Page<Need> findByStatus(NeedStatus status, Pageable pageable);
    Page<Need> findByPriority(Priority priority, Pageable pageable);
    Page<Need> findByType(NeedType type, Pageable pageable);
    Page<Need> findByFamilyId(UUID familyId, Pageable pageable);
    Page<Need> findByFamilyIdAndStatus(UUID familyId, NeedStatus status, Pageable pageable);
    long countByStatus(NeedStatus status);
}
