package com.impacto.application.service;
import com.impacto.application.exception.ForbiddenException;
import com.impacto.application.exception.NotFoundException;
import com.impacto.domain.model.User;
import com.impacto.infrastructure.persistence.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CurrentUserService {
    private final UserRepository repo;
    public CurrentUserService(UserRepository r) {
        repo=r;
    }
    public User get() {
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findByEmailIgnoreCase(email).orElseThrow(()->new NotFoundException("Usuario autenticado no existe"));
    }
    public UUID id() {
        return get().getId();
    }
    public void ensure(UUID id) {
        if(!id.equals(id())) throw new ForbiddenException("No tienes permisos para operar sobre este recurso");
    }
}
