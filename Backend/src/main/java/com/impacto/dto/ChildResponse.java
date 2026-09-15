package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record ChildResponse(UUID id,UUID familyId,String familyName,String name,LocalDate birthDate,String school,String grade,ChildStatus status,
    Instant createdAt,Instant updatedAt) {
}
