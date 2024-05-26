package com.rajendra.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;

@Configuration
public class HibernateConfig {
    
    @Bean
    public OpenSessionInViewInterceptor openSessionInViewInterceptor() {
        return new OpenSessionInViewInterceptor();
    }
}

