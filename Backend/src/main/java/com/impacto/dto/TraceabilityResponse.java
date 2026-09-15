package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record TraceabilityResponse(UUID resourceId,String resourceCode,ResourceType resourceType,UUID donationId,String donorName,UUID campaignId,
    String campaignName,List<TraceEvent> timeline) {
}
