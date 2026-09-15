package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record FamilyResponse(UUID id,String contactName,String phone,String address,String municipality,FamilyStatus status,Instant createdAt,
    Instant updatedAt) {
}
