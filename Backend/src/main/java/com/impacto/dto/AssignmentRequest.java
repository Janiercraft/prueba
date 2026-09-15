package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record AssignmentRequest(@NotNull UUID needId,@NotNull UUID resourceId,@NotNull @Positive Integer quantity) {
}
