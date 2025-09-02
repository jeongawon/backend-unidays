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

        // 허용할 Origin (credentials 사용 시 반드시 정확한 도메인 명시)
        config.setAllowedOriginPatterns(List.of(
                "https://zesty-mermaid-f2b857.netlify.app",
                "https://unidays-project.com"
        ));

        // 자격 증명 허용 (쿠키 포함)
        config.setAllowCredentials(true);

        // 허용할 HTTP 메서드
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 허용할 요청 헤더 (Content-Type 등 명시)
        config.setAllowedHeaders(List.of("Content-Type", "Authorization", "Accept", "Origin"));

        // 노출할 응답 헤더 (JS에서 접근 가능)
        config.addExposedHeader("Set-Cookie");
        config.addExposedHeader("Authorization");

        // 모든 경로에 대해 위 설정을 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
