package com.impacto.controller;
import com.impacto.dto.*;
import com.impacto.service.DeliveryService;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/volunteer") public class VolunteerController {
    private final DeliveryService s;
    public VolunteerController(DeliveryService s) {
        this.s=s;
    }
    @GetMapping("/tasks") PageResponse<DeliveryResponse> tasks(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size) {
        return s.find(page,size);
    }
    @GetMapping("/deliveries") PageResponse<DeliveryResponse> deliveries(@RequestParam(defaultValue="0")int page,
        @RequestParam(defaultValue="20")int size) {
        return s.find(page,size);
    }
}
