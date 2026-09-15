package com.impacto.api.controller;
import com.impacto.api.dto.*;
import com.impacto.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/auth") public class AuthController {
    private final AuthService s;
    public AuthController(AuthService s) {
        this.s=s;
    }
    @PostMapping("/register") ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(s.register(r));
    }
    @PostMapping("/login") ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest r) {
        return ResponseEntity.ok(s.login(r));
    }
    @GetMapping("/me") UserResponse me() {
        return s.me();
    }
}
