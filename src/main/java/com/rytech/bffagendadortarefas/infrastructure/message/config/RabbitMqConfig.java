package com.rytech.bffagendadortarefas.infrastructure.message.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String FILA_EMAIL = "fila.email";
    public static final String EXCHANGE_EMAIL = "email.exchange";
    public static final String ROUNTING_KEY = "email.key";


    @Bean
    public Queue filaEmail() {
        return new Queue(FILA_EMAIL);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_EMAIL);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(filaEmail())
                .to(directExchange())
                .with(ROUNTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
