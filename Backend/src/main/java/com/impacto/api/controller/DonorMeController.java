package com.impacto.api.controller;
import com.impacto.api.dto.*;
import com.impacto.application.service.DonationService;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/donor") public class DonorMeController {
    private final DonationService s;
    public DonorMeController(DonationService s) {
        this.s=s;
    }
    @GetMapping("/impact") com.impacto.api.dto.DonorImpactResponse impact() {
        return s.impact();
    }
    @GetMapping("/donations") PageResponse<DonationResponse> donations(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size) {
        return s.find(null,null,page,size);
    }
}
