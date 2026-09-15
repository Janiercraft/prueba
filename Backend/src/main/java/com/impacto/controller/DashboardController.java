package com.impacto.controller;
import com.impacto.dto.DashboardResponse;
import com.impacto.service.DashboardService;
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
