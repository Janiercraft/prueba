package com.impacto.infrastructure.persistence;
import com.impacto.domain.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.math.BigDecimal;
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    Page<Campaign> findByStatus(CampaignStatus status, Pageable pageable);
}
