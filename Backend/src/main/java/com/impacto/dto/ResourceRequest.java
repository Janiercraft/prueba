package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record ResourceRequest(@NotNull ResourceType type,@NotBlank @Size(max=500) String description,@NotNull @Positive Integer quantity,
    @NotNull UUID donationId) {
}
