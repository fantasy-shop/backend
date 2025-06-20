package net.supercoding.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** → 로컬 디렉토리 uploads/ (파일 시스템 기반)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        // /images/** → classpath의 static/images/ 디렉토리 (정적 리소스 캐싱)
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/")
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic().immutable());

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/home/ec2-user/images/")
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic().immutable());
    }
}

