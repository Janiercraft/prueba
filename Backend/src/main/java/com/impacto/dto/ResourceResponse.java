package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record ResourceResponse(UUID id,String code,ResourceType type,String description,Integer quantity,Integer availableQuantity,
    ResourceStatus status,UUID donationId,Instant createdAt,Instant updatedAt) {
}
