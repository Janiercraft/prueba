package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.api.mapper.EntityMapper;
import com.impacto.application.exception.ConflictException;
import com.impacto.domain.model.User;
import com.impacto.infrastructure.persistence.UserRepository;
import com.impacto.infrastructure.security.JwtService;
import org.slf4j.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
    private static final Logger log=LoggerFactory.getLogger(AuthService.class);
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager auth;
    private final JwtService jwt;
    private final EntityMapper mapper;
    public AuthService(UserRepository u,PasswordEncoder e,AuthenticationManager a,JwtService j,EntityMapper m) {
        users=u;
        encoder=e;
        auth=a;
        jwt=j;
        mapper=m;
    }
    public AuthResponse register(RegisterRequest r) {
        if(r.role()==com.impacto.domain.model.Role.ADMIN)throw new ConflictException("ADMIN no puede registrarse públicamente");
        if(users.existsByEmailIgnoreCase(r.email()))throw new ConflictException("El email ya está registrado");
        User u=new User();
        u.setName(r.name());
        u.setEmail(r.email());
        u.setPassword(encoder.encode(r.password()));
        u.setRole(r.role());
        users.save(u);
        String t=jwt.generate(u.getId(),u.getEmail(),u.getRole().name());
        log.info("Registro exitoso email={} role={}",u.getEmail(),u.getRole());
        return new AuthResponse(t,"Bearer",jwt.expiry(t),mapper.toUserResponse(u));
    }
    public AuthResponse login(LoginRequest r) {
        try {
            auth.authenticate(new UsernamePasswordAuthenticationToken(r.email(),r.password()));
        } catch(AuthenticationException e) {
            log.warn("Intento de login fallido email={}",r.email());
            throw e;
        }
        User u=users.findByEmailIgnoreCase(r.email()).orElseThrow();
        String t=jwt.generate(u.getId(),u.getEmail(),u.getRole().name());
        log.info("Login exitoso userId={}",u.getId());
        return new AuthResponse(t,"Bearer",jwt.expiry(t),mapper.toUserResponse(u));
    }
    public UserResponse me() {
        var e=users.findByEmailIgnoreCase(org.springframework.security.core.context.SecurityContextHolder
            .getContext().getAuthentication().getName()).orElseThrow();
        return mapper.toUserResponse(e);
    }
}
