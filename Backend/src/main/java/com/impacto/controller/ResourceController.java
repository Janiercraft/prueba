package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.ResourceService;
import com.impacto.entity.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/resources") public class ResourceController {
    private final ResourceService s;
    public ResourceController(ResourceService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<ResourceResponse> create(@Valid @RequestBody ResourceRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<ResourceResponse> find(@RequestParam(required=false)ResourceStatus status,@RequestParam(defaultValue="0")int page,
        @RequestParam(defaultValue="20")int size) {
        return s.find(status,page,size);
    }
    @GetMapping("/{id}")ResourceResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
    @PatchMapping("/{id}/status")ResourceResponse status(@PathVariable UUID id,@Valid @RequestBody ResourceStatusRequest r) {
        return s.changeStatus(id,r);
    }
    @GetMapping("/{id}/traceability")TraceabilityResponse trace(@PathVariable UUID id) {
        return s.trace(id);
    }
}
