package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record AssignmentResponse(UUID id,UUID needId,UUID resourceId,String resourceCode,Integer quantity,Instant createdAt) {
}
