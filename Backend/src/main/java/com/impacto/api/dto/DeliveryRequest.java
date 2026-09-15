package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DeliveryRequest(@NotNull UUID assignmentId,@NotNull UUID volunteerId,Instant deliveryDate,@Size(max=1000) String observations) {
}
