package com.rytech.bffagendadortarefas.infrastructure.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public FeignError feingError() {
        return new FeignError();
    }
}
