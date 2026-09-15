package com.impacto.api.controller;
import com.impacto.application.service.AuthService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.mockito.Mockito.*;
class AuthControllerTest {
    @Test void controllerDelegatesLogin() {
        var s=mock(AuthService.class);
        var c=new AuthController(s);
        c.login(new com.impacto.api.dto.LoginRequest("a@b.com","x"));
        verify(s).login(any());
    }
}
