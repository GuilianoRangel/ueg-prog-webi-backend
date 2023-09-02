package br.ueg.prog.webi.faculdade.service.impl;

import br.ueg.prog.webi.adminmodule.mapper.UsuarioMapper;
import br.ueg.prog.webi.adminmodule.model.Usuario;
import br.ueg.prog.webi.adminmodule.service.GrupoService;
import br.ueg.prog.webi.adminmodule.service.UsuarioService;
import br.ueg.prog.webi.api.dto.CredencialDTO;
import br.ueg.prog.webi.api.dto.UsuarioSenhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class UserProviderService implements br.ueg.prog.webi.api.service.UserProviderService {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Override
    public CredencialDTO getCredentialByLogin(String username) {
        Usuario byLogin = this.usuarioService.getByLogin(username);
        CredencialDTO credencialDTO = this.usuarioMapper.toCredentialDTO(byLogin);
        credencialDTO.setRoles(grupoService.getRolesByUsuario(byLogin.getId()));
        return credencialDTO;
    }

    private static CredencialDTO getCredencialDTO() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senhaCodificada = bCryptPasswordEncoder.encode("admin");
        return CredencialDTO.builder()
                .login("admin")
                .id(1L)
                .nome("Admin")
                .email("admin@admin.com.br")
                .roles(Arrays.asList("ROLE_ADMIN", "ROLE_TIPO_INCLUIR"))
                .statusAtivo(true)
                .senha(senhaCodificada)
                .build();
    }

    @Override
    public CredencialDTO redefinirSenha(UsuarioSenhaDTO usuarioSenhaDTO) {
        return null;
        //return usuarioMapper.toCredentialDTO(this.usuarioService.redefinirSenha(usuarioSenhaDTO));
    }

    @Override
    public CredencialDTO getCredentialByEmail(String email) {
        return this.usuarioMapper.toCredentialDTO(this.usuarioService.findByLoginUsuario(email));
    }
}
