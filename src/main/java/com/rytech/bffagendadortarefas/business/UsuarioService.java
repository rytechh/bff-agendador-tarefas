package com.rytech.bffagendadortarefas.business;


import com.rytech.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.rytech.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.rytech.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.rytech.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.rytech.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.rytech.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.rytech.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.rytech.bffagendadortarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioClient usuarioClient;


    public UsuarioDTOResponse salvaUsuario(UsuarioDTORequest usuarioDTO) {

        return usuarioClient.salvarUsuario(usuarioDTO);
    }

    public String loginUsuario(LoginRequestDTO dto) {
        return usuarioClient.login(dto);
    }


    public UsuarioDTOResponse buscarUsuarioPorEmail(String email, String token) {
        return usuarioClient.buscarUsuarioPorEmail(email, token);
    }

    public void deletaUsuarioPorEmail(String email, String token) {
        usuarioClient.deletarUsuarioPorEmail(email, token);
    }

    public UsuarioDTOResponse atualizaDadosUsuario(String token, UsuarioDTORequest dto) {
        return usuarioClient.atualizaDadoUsuario(dto, token);
    }

    public EnderecoDTOResponse atualizaEndereco(EnderecoDTORequest dto, Long idEndereco, String token) {

        return usuarioClient.atualizaEndereco(dto, idEndereco, token);
    }

    public TelefoneDTOResponse atualizaTelefone(Long idTelefone, TelefoneDTORequest dto, String token) {

        return usuarioClient.atualizaTelefone(dto, idTelefone, token);
    }


    public EnderecoDTOResponse cadastraEndereco(String token, EnderecoDTORequest dto) {

        return usuarioClient.cadastraEndereco(dto, token);
    }

    public TelefoneDTOResponse cadastraTelefone(String token, TelefoneDTORequest dto) {
        return usuarioClient.cadastraTelefone(dto, token);
    }

}
