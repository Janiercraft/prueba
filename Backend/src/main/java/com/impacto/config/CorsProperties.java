package com.impacto.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.*;
@ConfigurationProperties(prefix="app.cors")
public record CorsProperties(List<String> origins) {
}
