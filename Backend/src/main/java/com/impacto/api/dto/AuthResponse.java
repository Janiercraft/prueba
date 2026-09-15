package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record AuthResponse(String accessToken,String tokenType,Instant expiresAt,UserResponse user) {
}
