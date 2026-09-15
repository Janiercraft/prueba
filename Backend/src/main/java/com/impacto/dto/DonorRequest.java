package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DonorRequest(UUID userId,@NotBlank @Size(max=180) String name,@NotNull DonorType type,@NotBlank @Email String email,
    @Size(max=30) String phone,@NotNull UserStatus status) {
}
