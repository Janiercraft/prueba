package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DonorResponse(UUID id,UUID userId,String name,DonorType type,String email,String phone,UserStatus status) {
}
