package com.rytech.bffagendadortarefas.business;


import com.rytech.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.rytech.bffagendadortarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;


    public TarefasDTOResponse enviaEmail(TarefasDTOResponse dto) {
        return emailClient.enviarEmail(dto);

    }
}
