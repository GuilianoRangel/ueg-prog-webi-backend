package br.ueg.prog.webi.adminmodule.service;

import br.ueg.prog.webi.adminmodule.model.Grupo;
import br.ueg.prog.webi.adminmodule.repository.UsuarioGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioGrupoService {
    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    /***
     * Retorna Grupos vinculados a um usuário específico
     * @param userId
     * @return
     */
    public List<Grupo> getUsuarioGrupos(Long userId){
        return usuarioGrupoRepository.findByIdUsuario(userId);
    }

}
