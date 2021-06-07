package com.usian.config;

import com.usian.interceptor.SubmitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SubmitConfig implements WebMvcConfigurer {

    //<mvc:interceptors>
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SubmitInterceptor()).addPathPatterns("/frontend/order/**");

    }
}
