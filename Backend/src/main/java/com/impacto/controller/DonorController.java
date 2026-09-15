package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.DonorService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/donors") public class DonorController {
    private final DonorService s;
    public DonorController(DonorService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<DonorResponse> create(@Valid @RequestBody DonorRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<DonorResponse> find(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size) {
        return s.find(page,size);
    }
    @GetMapping("/{id}")DonorResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
    @PutMapping("/{id}")DonorResponse update(@PathVariable UUID id,@Valid @RequestBody DonorRequest r) {
        return s.update(id,r);
    }
}
