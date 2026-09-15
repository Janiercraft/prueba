package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DeliveryResponse(UUID id,UUID assignmentId,UUID volunteerId,String volunteerName,Instant deliveryDate,String observations,
    DeliveryStatus status,String evidencePath,Instant createdAt) {
}
