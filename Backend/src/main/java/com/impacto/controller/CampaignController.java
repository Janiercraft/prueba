package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.CampaignService;
import com.impacto.entity.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/campaigns") public class CampaignController {
    private final CampaignService s;
    public CampaignController(CampaignService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<CampaignResponse> create(@Valid @RequestBody CampaignRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<CampaignResponse> find(@RequestParam(required=false)CampaignStatus status,@RequestParam(defaultValue="0")int page,
        @RequestParam(defaultValue="20")int size) {
        return s.find(status,page,size);
    }
    @GetMapping("/{id}")CampaignResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
    @PutMapping("/{id}")CampaignResponse update(@PathVariable UUID id,@Valid @RequestBody CampaignRequest r) {
        return s.update(id,r);
    }
    @DeleteMapping("/{id}")ResponseEntity<Void> delete(@PathVariable UUID id) {
        s.delete(id);
        return ResponseEntity.noContent().build();
    }
}
