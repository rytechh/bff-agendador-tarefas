package com.rytech.bffagendadortarefas.infrastructure.client;


import com.rytech.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface EmailClient {

    @PostMapping
    TarefasDTOResponse enviarEmail(@RequestBody TarefasDTOResponse dto);
}