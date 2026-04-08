package com.rytech.bffagendadortarefas.business.dto.in;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTORequest {


    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;


}
