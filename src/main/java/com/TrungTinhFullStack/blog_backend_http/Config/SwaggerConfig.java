package com.TrungTinhFullStack.blog_backend_http.Config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicAPI() {

        return GroupedOpenApi.builder()
                .group("spring-public")
                .pathsToMatch("/api/**")
                .build();
    }
}
