package br.ueg.prog.webi.adminmodule.service;

import br.ueg.prog.webi.adminmodule.model.*;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.repository.GrupoRepository;
import br.ueg.prog.webi.adminmodule.repository.ModuloSistemaRepository;
import br.ueg.prog.webi.adminmodule.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class InicializarService {
    private static final Logger LOG =
            LoggerFactory.getLogger(InicializarService.class);
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private ModuloSistemaRepository moduloSistemaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void inicializar(){
        LOG.info("initiateDemoInstance");
        Usuario usuario = getNewUsuario();
        usuario = usuarioRepository.findByLogin(usuario.getLogin());
        //Caso já tenha usuário não executa novamente.
        if(Objects.nonNull(usuario)){
            return;
        }

        ModuloSistema moduloUsuario = createModuloCrud("USUARIO", "Manter Usuário");

        ModuloSistema moduloGrupo = createModuloCrud("GRUPO", "Manter Grupo");

        Grupo grupo = createGrupoAdmin(Arrays.asList(moduloUsuario, moduloGrupo));

        createUsuarioAdmin(grupo);
    }

    private ModuloSistema createModuloCrud(String moduloMNemonico, String moduloNome) {
        ModuloSistema moduloUsuario = new ModuloSistema();

        moduloUsuario.setMnemonico(moduloMNemonico);
        moduloUsuario.setNome(moduloNome);
        moduloUsuario.setStatus(StatusAtivoInativo.ATIVO);
        moduloUsuario = moduloSistemaRepository.save(moduloUsuario);

        final ModuloSistema lModuloUsuario = moduloUsuario;
        Set<Funcionalidade> funcionaldiades = getFuncionalidadesCrud();

/*        funcionaldiades.stream().map(
                funcionalidade -> {
                    funcionalidade.setModulo(lModuloUsuario);
                    return funcionalidade;
                }).collect(Collectors.toSet());
        // equivalente com for*/
        for(Funcionalidade funcionalidade: funcionaldiades){
            funcionalidade.setModuloSistema(moduloUsuario);
        }

        moduloUsuario.setFuncionalidades(funcionaldiades);
        moduloUsuario = moduloSistemaRepository.save(moduloUsuario);
        return moduloUsuario;
    }

    /**
     * retorna Funcionalidades padrão de um CRRUD
     * @return
     */
    private Set<Funcionalidade> getFuncionalidadesCrud() {
        Set<Funcionalidade> funcionalidades = new HashSet<>();

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("INCLUIR");
        fManter.setNome("Incluir");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ALTERAR");
        fManter.setNome("Alterar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ATIVAR_INATIVAR");
        fManter.setNome("Ativar/Inativar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("PESQUISAR");
        fManter.setNome("Pesquisar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("VISUALIZAR");
        fManter.setNome("Visualizar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);
        return funcionalidades;
    }

    private Grupo createGrupoAdmin(List<ModuloSistema> modulos) {
        Grupo grupo = new Grupo();
        grupo.setNome("Administradores");
        grupo.setDescricao("Grupo de Administradores");
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        grupo = grupoRepository.save(grupo);
        final Grupo lGrupo = grupo;
        grupo.setGrupoFuncionalidades(new HashSet<>());

        modulos.forEach(modulo -> {
            lGrupo.getGrupoFuncionalidades().addAll(
                    modulo.getFuncionalidades().stream().map(
                            funcionalidade -> new GrupoFuncionalidade(null, lGrupo, funcionalidade)
                    ).collect(Collectors.toSet())
            );
        });

        grupoRepository.save(grupo);
        return grupo;
    }
    private void createUsuarioAdmin(Grupo grupo) {
        Usuario usuario = getNewUsuario();

        usuario = usuarioRepository.save(usuario);

        Set<UsuarioGrupo> usuarioGrupos = new HashSet<>();
        usuarioGrupos.add(new UsuarioGrupo(null,usuario,grupo));
        usuario.setGrupos(usuarioGrupos);
        usuario = usuarioRepository.save(usuario);
    }

    private static Usuario getNewUsuario() {
        Usuario usuario = new Usuario();
        usuario.setStatus(StatusAtivoInativo.ATIVO);
        usuario.setDataCadastrado(LocalDate.now());
        usuario.setDataAtualizado(LocalDate.now());
        usuario.setTelefones(new HashSet<>());
        usuario.setLogin("admin");
        usuario.setNome("Administrador");
        usuario.setEmail("admin@teste.com.br");
        usuario.setSenha(new BCryptPasswordEncoder().encode("admin"));
        return usuario;
    }

}
