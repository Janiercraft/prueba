package com.impacto.api.controller;
import com.impacto.api.dto.*;
import com.impacto.application.service.DonationService;
import com.impacto.domain.model.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/donations") public class DonationController {
    private final DonationService s;
    public DonationController(DonationService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<DonationResponse> create(@Valid @RequestBody DonationRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<DonationResponse> find(@RequestParam(required=false)UUID donorId,@RequestParam(required=false)DonationStatus status,
        @RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size) {
        return s.find(donorId,status,page,size);
    }
    @GetMapping("/{id}")DonationResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
}
