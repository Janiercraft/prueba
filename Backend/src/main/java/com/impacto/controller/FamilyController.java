package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.FamilyService;
import com.impacto.entity.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/families") public class FamilyController {
    private final FamilyService s;
    public FamilyController(FamilyService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<FamilyResponse> create(@Valid @RequestBody FamilyRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<FamilyResponse> find(@RequestParam(required=false)String municipality,@RequestParam(required=false)FamilyStatus status,
        @RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size,@RequestParam(defaultValue="createdAt")String sort,@RequestParam(defaultValue="desc")String dir) {
        return s.find(municipality,status,page,size,sort,dir);
    }
    @GetMapping("/{id}")FamilyResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
    @PutMapping("/{id}")FamilyResponse update(@PathVariable UUID id,@Valid @RequestBody FamilyRequest r) {
        return s.update(id,r);
    }
    @DeleteMapping("/{id}") ResponseEntity<Void> delete(@PathVariable UUID id) {
        s.delete(id);
        return ResponseEntity.noContent().build();
    }
}
