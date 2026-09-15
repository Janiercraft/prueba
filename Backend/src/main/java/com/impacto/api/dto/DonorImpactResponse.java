package com.impacto.api.dto;
import com.impacto.domain.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DonorImpactResponse(long totalDonations, java.math.BigDecimal moneyDonated, long materialDonations) {
}
