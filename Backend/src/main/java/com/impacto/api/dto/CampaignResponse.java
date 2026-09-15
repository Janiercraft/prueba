package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record CampaignResponse(UUID id,String name,String description,BigDecimal goal,LocalDate startDate,LocalDate endDate,CampaignStatus status) {
}
