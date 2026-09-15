package com.impacto.infrastructure.persistence;
import com.impacto.domain.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.math.BigDecimal;
public interface DonationRepository extends JpaRepository<Donation, UUID> {
    Page<Donation> findByDonorId(UUID donorId, Pageable pageable);
    Page<Donation> findByStatus(DonationStatus status, Pageable pageable);
    long countByStatus(DonationStatus status);
    @Query("select coalesce(sum(d.amount),0) from Donation d where d.donor.id = :donorId and d.type = :type and d.status = :status") BigDecimal sumAmountByDonorId(@Param("donorId") UUID donorId,@Param("type") DonationType type,
        @Param("status") DonationStatus status);
    @Query("select count(d) from Donation d where d.donor.id = :donorId and d.type = :type and d.status = :status") long countByDonorIdAndTypeAndStatus(@Param("donorId") UUID donorId,@Param("type") DonationType type,
        @Param("status") DonationStatus status);
    @Query("select coalesce(sum(d.amount),0) from Donation d where d.type = :type and d.status = :status") BigDecimal sumAmount(@Param("type") DonationType type, @Param("status") DonationStatus status);
}
