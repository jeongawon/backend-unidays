package com.example.unidays.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:3000"               // ✅ 로컬 개발용
                                                      // ✅ 배포될 프론트 주소 (나중에 필요시 추가)
        ));
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedHeader("Content-Type");
        config.addAllowedMethod("*");  // GET, POST 등 전부 허용
        config.addAllowedMethod("OPTIONS");
        config.addExposedHeader("Set-Cookie");
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
