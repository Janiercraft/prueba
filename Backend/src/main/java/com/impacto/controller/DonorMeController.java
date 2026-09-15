package com.impacto.controller;

import com.impacto.dto.*;
import com.impacto.service.DonationService;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/donor")
public class DonorMeController {
    private final DonationService service;

    public DonorMeController(DonationService service) {
        this.service = service;
    }

    @GetMapping("/impact")
    public DonorImpactResponse impact(@RequestParam UUID donorId) {
        return service.impact(donorId);
    }

    @GetMapping("/donations")
    public PageResponse<DonationResponse> donations(@RequestParam UUID donorId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        return service.find(donorId, null, page, size);
    }
}
