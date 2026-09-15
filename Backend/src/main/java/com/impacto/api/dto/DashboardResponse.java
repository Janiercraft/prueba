package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DashboardResponse(long totalFamilies,long totalChildren,long pendingNeeds,long fulfilledNeeds,long availableResources,
    long deliveredResources,BigDecimal totalMoneyDonations,long totalDonations,long completedDeliveries,long volunteers,List<MonthlyStat> monthly) {
}
