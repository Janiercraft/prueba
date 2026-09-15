package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record ChildRequest(@NotNull UUID familyId,@NotBlank @Size(max=160) String name,@NotNull @Past LocalDate birthDate,
    @Size(max=180) String school,@Size(max=80) String grade,@NotNull ChildStatus status) {
}
