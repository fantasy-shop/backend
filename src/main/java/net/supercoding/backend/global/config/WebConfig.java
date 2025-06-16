package net.supercoding.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // URL 패턴 /uploads/** 가 실제 파일 저장 위치 ./uploads/** 로 매핑됨
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
        // "file:" 접두어는 파일 시스템 경로임을 나타냅니다.
    }
}
