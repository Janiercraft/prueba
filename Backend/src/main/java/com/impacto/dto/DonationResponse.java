package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DonationResponse(UUID id,UUID donorId,String donorName,UUID campaignId,DonationType type,BigDecimal amount,String description,
    Integer quantity,ResourceType materialType,DonationStatus status,Instant createdAt) {
}
