package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record AssignmentRequest(@NotNull UUID needId,@NotNull UUID resourceId,@NotNull @Positive Integer quantity) {
}
