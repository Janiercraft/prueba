package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record AssignmentResponse(UUID id,UUID needId,UUID resourceId,String resourceCode,Integer quantity,Instant createdAt) {
}
