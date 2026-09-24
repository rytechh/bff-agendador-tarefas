package com.rytech.bffagendadortarefas.business;

import com.rytech.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.rytech.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.rytech.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import com.rytech.bffagendadortarefas.infrastructure.message.producer.EmailProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {

    private final TarefasService tarefasService;
    private final EmailProducer emailProducer;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;


    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {
        String token = login(converterParaRequestDTO());

        log.info("Iniciada a busca de tarefas");
        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);

        List<TarefasDTOResponse> listaTarefas = tarefasService.buscaTarefasAgendadasPorPeriodo(horaAtual,
                horaFutura, token);
        log.info("Tarefas encontradas: " + listaTarefas);
        listaTarefas.forEach(tarefas -> {
            emailProducer.enviarEmail(tarefas);
            log.info("Email enviado para o usuario: " + tarefas.getEmailUsuario());
            tarefasService.alteraStatus(StatusNotificacaoEnum.AGUARDANDO, tarefas.getId(),
                    token);
        });
        log.info("Finalizadas a busca e notificação de tarefas");

    }

    public String login(LoginRequestDTO dto) {
        return usuarioService.loginUsuario(dto);
    }

    public LoginRequestDTO converterParaRequestDTO() {
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }

}
