package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.ChildService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/children") public class ChildController {
    private final ChildService s;
    public ChildController(ChildService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<ChildResponse> create(@Valid @RequestBody ChildRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<ChildResponse> find(@RequestParam(required=false)UUID familyId,@RequestParam(defaultValue="0")int page,
        @RequestParam(defaultValue="20")int size) {
        return s.find(familyId,page,size);
    }
    @GetMapping("/{id}")ChildResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
    @PutMapping("/{id}")ChildResponse update(@PathVariable UUID id,@Valid @RequestBody ChildRequest r) {
        return s.update(id,r);
    }
    @DeleteMapping("/{id}")ResponseEntity<Void> delete(@PathVariable UUID id) {
        s.delete(id);
        return ResponseEntity.noContent().build();
    }
}
