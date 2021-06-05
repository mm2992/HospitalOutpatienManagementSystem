package com.graduate.hospital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new com.graduate.hospital.interceptor.LoginInterceptor())
                .addPathPatterns("/doctor/**","/drug/**","/record/**","/order/**","/outOrder/**","/patient/**","/prescription/**","/user/**")
                .excludePathPatterns("/prescription/pay","/prescription/calculateBill","/prescription/getById/{patientId}/{status}","/patient/getPatientById","/patient/addBalance","/patient/refund","/doctor/fillMsg");

    }
}
