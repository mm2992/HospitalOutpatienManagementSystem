package com.graduate.hospital.config;

import com.graduate.hospital.interceptor.ReturnLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.rmi.registry.Registry;

@Configuration
public class ReturnLoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReturnLoginInterceptor())
                .addPathPatterns("/loginIndex");
    }
}
