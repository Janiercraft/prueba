package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DonorResponse(UUID id,UUID userId,String name,DonorType type,String email,String phone,UserStatus status) {
}
