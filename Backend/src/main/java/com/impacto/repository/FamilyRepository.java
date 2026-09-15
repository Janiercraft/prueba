package com.impacto.repository;
import com.impacto.entity.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.math.BigDecimal;
public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Page<Family> findByMunicipalityContainingIgnoreCase(String municipality, Pageable pageable);
    Page<Family> findByStatus(FamilyStatus status, Pageable pageable);
    Page<Family> findByMunicipalityContainingIgnoreCaseAndStatus(String municipality, FamilyStatus status, Pageable pageable);
}
