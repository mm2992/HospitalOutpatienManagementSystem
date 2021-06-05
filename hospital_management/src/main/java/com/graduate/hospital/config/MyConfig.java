package com.graduate.hospital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/templates/index.html").setViewName("index");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //告知系统static 当成 静态资源访问
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }


}
