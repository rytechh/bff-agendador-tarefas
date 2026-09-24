package com.rytech.bffagendadortarefas.infrastructure.message.producer;

import com.rytech.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.rytech.bffagendadortarefas.infrastructure.message.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarEmail(TarefasDTOResponse dtoResponse) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_EMAIL,
                RabbitMqConfig.ROUNTING_KEY,
                dtoResponse);
    }
}
