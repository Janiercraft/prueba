package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.NeedService;
import com.impacto.entity.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/v1/needs") public class NeedController {
    private final NeedService s;
    public NeedController(NeedService s) {
        this.s=s;
    }
    @PostMapping NeedResponse create(@Valid @RequestBody NeedRequest r) {
        return s.create(r);
    }
    @GetMapping PageResponse<NeedResponse> find(@RequestParam(required=false)NeedStatus status,@RequestParam(required=false)Priority priority,
        @RequestParam(required=false)NeedType type,@RequestParam(required=false)UUID familyId,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size) {
        return s.find(status,priority,type,familyId,page,size);
    }
    @GetMapping("/{id}")NeedResponse get(@PathVariable UUID id) {
        return s.get(id);
    }
    @PutMapping("/{id}")NeedResponse update(@PathVariable UUID id,@Valid @RequestBody NeedRequest r) {
        return s.update(id,r);
    }
    @PatchMapping("/{id}/status")NeedResponse status(@PathVariable UUID id,@Valid @RequestBody NeedStatusRequest r) {
        return s.changeStatus(id,r);
    }
}
