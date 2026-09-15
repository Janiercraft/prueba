package com.impacto.api.controller;
import com.impacto.api.dto.DashboardResponse;
import com.impacto.application.service.DashboardService;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/dashboard") public class DashboardController {
    private final DashboardService s;
    public DashboardController(DashboardService s) {
        this.s=s;
    }
    @GetMapping DashboardResponse get() {
        return s.get();
    }
}
