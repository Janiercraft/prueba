package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/assignments") public class AssignmentController {
    private final AssignmentService s;
    public AssignmentController(AssignmentService s) {
        this.s=s;
    }
    @PostMapping ResponseEntity<AssignmentResponse> create(@Valid @RequestBody AssignmentRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.create(r));
    }
    @GetMapping PageResponse<AssignmentResponse> find(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size) {
        return s.find(page,size);
    }
    @GetMapping("/{id}")AssignmentResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
}
