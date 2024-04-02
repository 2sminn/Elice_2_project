package com.elice.kittyandpuppy.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("구해줘멍냥")
                .version(springdocVersion)
                .description("Elice Cloud Track 2기 팀 프로젝트의 일환으로 유기견과 유기묘에 대한 커뮤니티 사이트입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}