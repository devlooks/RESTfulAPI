package com.example.myrestfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// API 문서 정보 출력용
@OpenAPIDefinition(
        info = @Info(title = "My Restful Service API 명세서",
            description = "Spring boot 로 개발하는 RESTful API 명세서 입니다",
                version = "v1.0.0")
)
@Configuration
@RequiredArgsConstructor
public class NewSwaggerConfig {

    // API 정보 경로 제한
    @Bean
    public GroupedOpenApi customTestOpenAPI() {
        String[] paths = {"/users/**", "/admin/**"};

        return GroupedOpenApi.builder()
                .group("일반 사용자와 관리자를 위한 User 도메인 API 입니다.")
                .pathsToMatch(paths)
                .build();
    }


}
