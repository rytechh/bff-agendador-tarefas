package com.rytech.bffagendadortarefas.infrastructure.client;


import com.rytech.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.rytech.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.rytech.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.rytech.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.rytech.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.rytech.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.rytech.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.rytech.bffagendadortarefas.business.dto.out.ViaCepDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping
    UsuarioDTOResponse buscarUsuarioPorEmail(@RequestParam("email") String email,
                                             @RequestHeader("Authorization") String token);


    @PostMapping
    UsuarioDTOResponse salvarUsuario(@RequestBody UsuarioDTORequest dto);

    @PostMapping("/login")
    String login(@RequestBody LoginRequestDTO dto);


    @DeleteMapping("/{email}")
    void deletarUsuarioPorEmail(@PathVariable String email,
                                @RequestHeader("Authorization") String token);

    @PutMapping
    UsuarioDTOResponse atualizaDadoUsuario(@RequestBody UsuarioDTORequest dto,
                                           @RequestHeader("Authorization") String token);

    @PutMapping("/endereco")
    EnderecoDTOResponse atualizaEndereco(@RequestBody EnderecoDTORequest dto,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization") String token);

    @PutMapping("/telefone")
    TelefoneDTOResponse atualizaTelefone(@RequestBody TelefoneDTORequest dto,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization") String token);

    @PostMapping("/endereco")
    EnderecoDTOResponse cadastraEndereco(@RequestBody EnderecoDTORequest dto,
                                         @RequestHeader("Authorization") String token);

    @PostMapping("/telefone")
    TelefoneDTOResponse cadastraTelefone(@RequestBody TelefoneDTORequest dto,
                                         @RequestHeader("Authorization") String token);

    @GetMapping("/endereco/{cep}")
    ViaCepDTOResponse buscarDadosCep(@PathVariable("cep") String cep);
}