package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record NeedResponse(UUID id,UUID familyId,UUID childId,NeedType type,Priority priority,NeedStatus status,String description,
    Integer requestedQuantity,Integer fulfilledQuantity,Integer remainingQuantity,Instant createdAt,Instant updatedAt) {
}
