package com.savemoney.utils.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addExposedHeader(getExposedHeaders());
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private String getExposedHeaders() {
        StringBuilder builder = new StringBuilder();
        builder.append("Authorization, ");
        builder.append("x-xsrf-token, ");
        builder.append("Access-Control-Allow-Headers, ");
        builder.append("Origin, ");
        builder.append("Accept, ");
        builder.append("X-Requested-With, ");
        builder.append("Content-Type, ");
        builder.append("Access-Control-Request-Method, ");
        builder.append("Custom-Filter-Header");

        return builder.toString();
    }
}
