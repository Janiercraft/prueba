package com.impacto.infrastructure.security;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.*;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwt;
    public JwtAuthenticationFilter(JwtService j) {
        jwt=j;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException {
        String h=req.getHeader(HttpHeaders.AUTHORIZATION);
        if(h!=null&&h.startsWith("Bearer ")) {
            try {
                var c=jwt.parse(h.substring(7));
                String email=c.getSubject();
                String role=c.get("role",String.class);
                var a=new UsernamePasswordAuthenticationToken(email,null,List.of(new SimpleGrantedAuthority("ROLE_"+role)));
                a.setDetails(c.get("uid",String.class));
                SecurityContextHolder.getContext().setAuthentication(a);
            } catch(JwtException|IllegalArgumentException ignored) {
            }
        }
        chain.doFilter(req,res);
    }
}
