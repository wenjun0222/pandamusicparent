package com.ning.repository;

import com.ning.interceptor.WebInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private WebInterceptor  webInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/register").excludePathPatterns("/login")
                .excludePathPatterns("/sendCode/*").excludePathPatterns("/uploadAvatar")
                .excludePathPatterns("/updatePwdByAccount");
    }
}
