package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record TraceabilityResponse(UUID resourceId,String resourceCode,ResourceType resourceType,UUID donationId,String donorName,UUID campaignId,
    String campaignName,List<TraceEvent> timeline) {
}
