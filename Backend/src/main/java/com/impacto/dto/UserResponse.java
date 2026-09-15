package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record UserResponse(UUID id,String name,String email,Role role,UserStatus status,Instant createdAt,Instant updatedAt) {
}
