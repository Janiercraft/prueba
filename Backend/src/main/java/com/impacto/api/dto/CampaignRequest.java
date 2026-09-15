package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record CampaignRequest(@NotBlank @Size(max=180) String name,@Size(max=1000) String description,@NotNull @Positive BigDecimal goal,
    @NotNull LocalDate startDate,@NotNull @FutureOrPresent LocalDate endDate,@NotNull CampaignStatus status) {
}
