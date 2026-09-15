package com.impacto.infrastructure.security;
import com.impacto.config.CorsProperties;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.*;
@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    @Bean DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService uds,PasswordEncoder pe) {
        var p=new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(pe);
        return p;
    }
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c)throws Exception {
        return c.getAuthenticationManager();
    }
    @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtAuthenticationFilter jwt,CorsConfigurationSource cors) throws Exception {
        http.csrf(c->c.disable()).cors(c->c.configurationSource(cors)).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy
            .STATELESS)) .authorizeHttpRequests(a->a .requestMatchers("/api/v1/auth/**","/swagger-ui/**","/v3/api-docs/**",
                "/actuator/health").permitAll() .requestMatchers("/api/v1/dashboard/**").hasRole("ADMIN") .requestMatchers("/api/v1/donors/**","/api/v1/campaigns/**").hasRole("ADMIN") .requestMatchers(org
                    .springframework.http.HttpMethod.POST,"/api/v1/families/**","/api/v1/children/**","/api/v1/needs/**","/api/v1/resources/**","/api/v1/assignments/**").hasRole("ADMIN") .requestMatchers(org
                        .springframework.http.HttpMethod.PUT,"/api/v1/families/**","/api/v1/children/**","/api/v1/needs/**","/api/v1/campaigns/**").hasRole("ADMIN") .requestMatchers(org
                            .springframework.http.HttpMethod.PATCH,"/api/v1/needs/**","/api/v1/resources/**").hasRole("ADMIN") .requestMatchers(org
                                .springframework.http.HttpMethod.DELETE,"/api/v1/families/**","/api/v1/children/**","/api/v1/campaigns/**").hasRole("ADMIN") .requestMatchers(org
                                    .springframework.http.HttpMethod.POST,"/api/v1/donations").hasAnyRole("ADMIN","DONOR") .requestMatchers("/api/v1/donor/**").hasRole("DONOR") .requestMatchers("/api/v1/volunteer/**").hasRole("VOLUNTEER") .requestMatchers("/api/v1/deliveries/**").hasAnyRole("ADMIN","VOLUNTEER") .requestMatchers(org.springframework.http.HttpMethod.GET,"/api/v1/families/**","/api/v1/children/**","/api/v1/needs/**","/api/v1/resources/**","/api/v1/campaigns/**").hasAnyRole("ADMIN","VOLUNTEER") .requestMatchers(org.springframework.http.HttpMethod.GET,"/api/v1/assignments/**").hasAnyRole("ADMIN","VOLUNTEER") .requestMatchers(org.springframework.http.HttpMethod.GET,"/api/v1/donations/**").hasAnyRole("ADMIN","DONOR") .anyRequest().authenticated()) .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource(CorsProperties p) {
        var c=new CorsConfiguration();
        c.setAllowedOrigins(p.origins());
        c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        c.setAllowedHeaders(List.of("Authorization","Content-Type"));
        c.setAllowCredentials(false);
        var s=new UrlBasedCorsConfigurationSource();
        s.registerCorsConfiguration("/**",c);
        return s;
    }
}
