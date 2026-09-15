package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record FamilyResponse(UUID id,String contactName,String phone,String address,String municipality,FamilyStatus status,Instant createdAt,
    Instant updatedAt) {
}
