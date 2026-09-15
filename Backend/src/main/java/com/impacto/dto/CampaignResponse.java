package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record CampaignResponse(UUID id,String name,String description,BigDecimal goal,LocalDate startDate,LocalDate endDate,CampaignStatus status) {
}
