package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DonationRequest(@NotNull UUID donorId,UUID campaignId,@NotNull DonationType type,@Positive BigDecimal amount,
    @Size(max=500) String description,@Positive Integer quantity,ResourceType materialType,@NotNull DonationStatus status) {
}
