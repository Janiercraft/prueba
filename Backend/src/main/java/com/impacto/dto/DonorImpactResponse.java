package com.impacto.dto;
import com.impacto.entity.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
public record DonorImpactResponse(long totalDonations, java.math.BigDecimal moneyDonated, long materialDonations) {
}
