package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record FamilyRequest(@NotBlank @Size(max=160) String contactName,@NotBlank @Size(max=30) String phone,@NotBlank @Size(max=250) String address,
    @NotBlank @Size(max=80) String municipality,@NotNull FamilyStatus status) {
}
