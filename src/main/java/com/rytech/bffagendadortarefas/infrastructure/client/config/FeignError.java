package com.rytech.bffagendadortarefas.infrastructure.client.config;

import com.rytech.bffagendadortarefas.infrastructure.exceptions.*;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {


    @Override
    public Exception decode(String s, Response response) {

        String mensagemErro = mensagemErro(response);


        String erroResponse = "erro";

        switch (response.status()) {
            case 409:
                return new ConflictException(erroResponse + mensagemErro);
            case 403:
                return new ResourceNotFoundException(erroResponse + mensagemErro);
            case 401:
                return new UnauthorizedException(erroResponse + mensagemErro);
            case 400:
                return new IllegalArgumentsException(erroResponse + mensagemErro);
            default:
                return new BusinessException(erroResponse + mensagemErro);
        }
    }

    private String mensagemErro(Response response) {
        try {
            if (Objects.isNull(response.body())) {
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {

            return "Erro desconhecido na comunicação com o serviço externo";
        }
    }
}
