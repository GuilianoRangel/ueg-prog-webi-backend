package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.api.dto.CredencialDTO;
import br.ueg.prog.webi.api.dto.UsuarioSenhaDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class UserProviderService implements br.ueg.prog.webi.api.service.UserProviderService {
    @Override
    public CredencialDTO getCredentialByLogin(String username) {
        if(Objects.nonNull(username) && username.equals("admin")){
            return getCredencialDTO();
        }
        return null;
    }

    private static CredencialDTO getCredencialDTO() {
        return CredencialDTO.builder()
                .login("admin")
                .id(1L)
                .nome("Admin")
                .email("admin@admin.com.br")
                .roles(Arrays.asList("ROLE_ADMIN"))
                .statusAtivo(true)
                .senha("admin")
                .build();
    }

    @Override
    public CredencialDTO redefinirSenha(UsuarioSenhaDTO usuarioSenhaDTO) {
        return null;
    }

    @Override
    public CredencialDTO getCredentialByEmail(String email) {
        if(Objects.nonNull(email) && email.equals("admin@admin.com.br")){
            return getCredencialDTO();
        }
        return null;
    }
}
