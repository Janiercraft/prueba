package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record ChildResponse(UUID id,UUID familyId,String familyName,String name,LocalDate birthDate,String school,String grade,ChildStatus status,
    Instant createdAt,Instant updatedAt) {
}
