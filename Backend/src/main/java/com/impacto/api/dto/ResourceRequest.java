package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record ResourceRequest(@NotNull ResourceType type,@NotBlank @Size(max=500) String description,@NotNull @Positive Integer quantity,
    @NotNull UUID donationId) {
}
