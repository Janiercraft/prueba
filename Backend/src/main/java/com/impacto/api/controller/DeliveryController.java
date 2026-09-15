package com.impacto.api.controller;
import com.impacto.api.dto.*;
import com.impacto.application.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
@RestController @RequestMapping("/api/v1/deliveries") public class DeliveryController {
    private final DeliveryService s;
    public DeliveryController(DeliveryService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<DeliveryResponse> create(@Valid @RequestBody DeliveryRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<DeliveryResponse> find(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size) {
        return s.find(page,size);
    }
    @GetMapping("/{id}")DeliveryResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
    @PatchMapping("/{id}/status")DeliveryResponse status(@PathVariable UUID id,@Valid @RequestBody DeliveryStatusRequest r) {
        return s.changeStatus(id,r);
    }
    @PostMapping(value="/{id}/evidence",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)DeliveryResponse evidence(@PathVariable UUID id,
        @RequestPart MultipartFile file) {
        return s.addEvidence(id,file);
    }
}
