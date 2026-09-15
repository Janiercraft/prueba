package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record NeedRequest(@NotNull UUID familyId,UUID childId,@NotNull NeedType type,@NotNull Priority priority,
    @NotBlank @Size(max=500) String description,@NotNull @Positive Integer requestedQuantity,@NotNull NeedStatus status) {
}
