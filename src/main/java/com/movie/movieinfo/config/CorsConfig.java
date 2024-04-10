package com.movie.movieinfo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/movieInfo/**")
                .allowedOrigins("*") // 모든 도메인에서의 접근을 허용
                .allowedMethods("GET")
                .allowCredentials(true)
                .maxAge(3600); // pre-flight 요청의 결과를 캐시하는 시간(초)
    }
}
