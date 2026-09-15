package com.impacto.infrastructure.security;
import com.impacto.infrastructure.persistence.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;
    public CustomUserDetailsService(UserRepository r) {
        repo=r;
    }
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
        return repo.findByEmailIgnoreCase(email).filter(u->u.getStatus().name().equals("ACTIVE")).map(u->User.withUsername(u.getEmail()).password(u
            .getPassword()).roles(u.getRole().name()).build()).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));
    }
}
