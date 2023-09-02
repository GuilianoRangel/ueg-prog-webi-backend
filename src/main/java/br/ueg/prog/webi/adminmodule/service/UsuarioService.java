/*
 * UsuarioService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.service;

import br.ueg.prog.webi.adminmodule.dto.filtros.FiltroUsuarioDTO;
import br.ueg.prog.webi.adminmodule.dto.model.UsuarioDTO;
import br.ueg.prog.webi.adminmodule.exception.ModuleAdminMessageCode;
import br.ueg.prog.webi.adminmodule.mapper.UsuarioMapper;
import br.ueg.prog.webi.adminmodule.model.TelefoneUsuario;
import br.ueg.prog.webi.adminmodule.model.Usuario;
import br.ueg.prog.webi.adminmodule.model.UsuarioGrupo;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.repository.UsuarioRepository;
import br.ueg.prog.webi.api.dto.AuthDTO;
import br.ueg.prog.webi.api.dto.UsuarioSenhaDTO;
import br.ueg.prog.webi.api.exception.ApiMessageCode;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.UserPasswordService;
import br.ueg.prog.webi.api.util.CollectionUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe de négocio referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailEngineService emailService;

	@Autowired
	private DataSource dataSource;
	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private InicializarService inicializarService;

	/**
     * Persiste os dados do {@link Usuario}.
     *
     * @param usuario
     * @return
     */
	public Usuario salvar(Usuario usuario) {
		validarCamposObrigatorios(usuario);
		validarUsuarioDuplicadoPorLogin(usuario);

		if (usuario.getId() == null) {

			usuario.setStatus(StatusAtivoInativo.ATIVO);
			LocalDate dataCadastro = LocalDate.now();
			usuario.setDataAtualizado(dataCadastro);
			usuario.setDataCadastrado(dataCadastro);
			usuario.setSenha(new BCryptPasswordEncoder().encode("123456"));

		} else {
			Usuario vigente = getById(usuario.getId());

			usuario.setStatus(vigente.getStatus());
			usuario.setSenha(vigente.getSenha());
			usuario.setDataCadastrado(vigente.getDataCadastrado());
			usuario.setDataAtualizado(LocalDate.now());
		}

		usuario = usuarioRepository.save(usuario);
		return usuario;
	}

	/**
	 * Configura o {@link Usuario} dentro de {@link UsuarioGrupo} e {@link TelefoneUsuario} para salvar.
	 * 
	 * @param usuario
	 */
	public void configurarUsuarioGruposAndTelefones(Usuario usuario) {
		for (UsuarioGrupo usuarioGrupo : usuario.getGrupos()) {
			usuarioGrupo.setUsuario(usuario);
		}

		for (TelefoneUsuario telefoneUsuario : usuario.getTelefones()) {
			telefoneUsuario.setUsuario(usuario);
		}
	}

    /**
     * Verifica a existencia de {@link Usuario} acordo com o 'login' informado.
     *
     * @param usuario
     */
	private void validarUsuarioDuplicadoPorLogin(final Usuario usuario) {
		Long count = usuarioRepository.countByLogin(usuario.getLogin());

		if ( (count > BigDecimal.ONE.longValue() && usuario.getId()!=null) ||
				(count > BigDecimal.ZERO.longValue() && usuario.getId()==null)
		) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_LOGIN_DUPLICADO);
		}
	}


    /**
     * Verifica se os campos obrigatorios de {@link Usuario} foram preenchidos.
     *
     * @param usuario
     */
	private void validarCamposObrigatorios(final Usuario usuario) {
		boolean invalido = Boolean.FALSE;

		if (Strings.isEmpty(usuario.getLogin())) {
			invalido = Boolean.TRUE;
		}

		if (usuario.getGrupos() == null)
			invalido = Boolean.TRUE;

		if (invalido) {
			throw new BusinessException(ApiMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}
	}

    /**
     * Retorna a instância do {@link Usuario} conforme o 'login' informado.
     * 
     * @param login
     * @return
     */
	public Usuario getByLogin(String login) {
		Usuario byLogin = usuarioRepository.findByLogin(login);
		if(Objects.isNull(byLogin)){
			return null;
		}
		Optional<Usuario> byIdFetch = usuarioRepository.findByIdFetch(byLogin.getId());
		return byIdFetch.get();
	}

    /**
     * Retorna a Lista de {@link UsuarioDTO} conforme o filtro pesquisado.
     * 
     * @param filtroDTO
     * @return
     */
	public List<Usuario> getUsuariosByFiltro(FiltroUsuarioDTO filtroDTO) {
		validarCamposObrigatoriosFiltro(filtroDTO);

		List<Usuario> usuarios = usuarioRepository.findAllByFiltro(filtroDTO);

		if (CollectionUtil.isEmpty(usuarios)) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
		}

		return usuarios;
	}

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
	private void validarCamposObrigatoriosFiltro(final FiltroUsuarioDTO filtroDTO) {
		boolean vazio = Boolean.TRUE;

		if (Strings.isNotEmpty(filtroDTO.getNome())) {
			vazio = Boolean.FALSE;
		}

		if (Strings.isNotEmpty(filtroDTO.getLogin())) {
			vazio = Boolean.FALSE;
		}

		if (Objects.nonNull(filtroDTO.getAtivo())) {
			vazio = Boolean.FALSE;
		}

		if (vazio) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_FILTRO_NAO_INFORMADO);
		}
	}

    /**
     * Retorna a instância de {@link Usuario} conforme o 'id' informado.
     *
     * @param id -
     * @return -
     */
	public Usuario getById(final Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	/**
	 * Retorna a instância de {@link Usuario} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	public Usuario getByIdFetch(final Long id) {
		return usuarioRepository.findByIdFetch(id).orElse(null);
	}

    /**
     * Valida se as senhas foram preenchidas e se são iguais
     *
     * @param redefinirSenhaDTO -
     */
	private void validarConformidadeSenha(final UsuarioSenhaDTO redefinirSenhaDTO) {
		if (Strings.isEmpty(redefinirSenhaDTO.getNovaSenha()) || Strings.isEmpty(redefinirSenhaDTO.getConfirmarSenha())) {
			throw new BusinessException(ApiMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}

		if (!redefinirSenhaDTO.getNovaSenha().equals(redefinirSenhaDTO.getConfirmarSenha())) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_SENHAS_DIFERENTES);
		}
	}

    /**
     * Valida se a senha corrente foi preenchida e corresponde a senha armazenada no
     * keycloak.
     *
     * @param usuario -
     * @param senhaAntiga -
     */
	private void validarSenhaCorrente(Usuario usuario, String senhaAntiga) {
		if (Strings.isEmpty(senhaAntiga)) {
			throw new BusinessException(ApiMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
		}

		AuthDTO authDTO = new AuthDTO();
		authDTO.setLogin(usuario.getLogin());
		authDTO.setSenha(senhaAntiga);
		if (!UserPasswordService.loginByPassword(this.usuarioMapper.toCredentialDTO(usuario), authDTO)) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_SENHA_ANTERIOR_INCORRETA);
		}
	}

    /**
     * Realiza a inclusão ou alteração de senha do {@link Usuario}.
     *
     * @param usuarioSenhaDTO -
     */
	public Usuario redefinirSenha(final UsuarioSenhaDTO usuarioSenhaDTO) {
		Usuario usuario = getById(usuarioSenhaDTO.getIdUsuario());

		validarConformidadeSenha(usuarioSenhaDTO);

		if (!usuarioSenhaDTO.isAtivacao() && !usuarioSenhaDTO.isRecuperacao()) {
			validarSenhaCorrente(usuario, usuarioSenhaDTO.getSenhaAntiga());
		} else {
			usuario.setStatus(StatusAtivoInativo.ATIVO);
		}
		usuario.setSenha(usuarioSenhaDTO.getNovaSenha());
		return usuarioRepository.save(usuario);
	}

	/**
	 * Retorna a instância de {@link Usuario} conforme o 'login' informado.
	 * 
	 * @param login
	 * @return
	 */
	public Usuario findByLoginUsuario(final String login) {
		return usuarioRepository.findByLogin(login);
	}

	/**
	 * Retorna a instância do {@link Usuario} conforme o 'login' informado
	 * e que não tenha o 'id' informado.
	 * 
	 * @param login
	 * @param id
	 * @return
	 */
	public Usuario findByLoginUsuarioAndNotId(final String login, final Long id) {
		return usuarioRepository.findByLoginAndNotId(login, id);
	}

    /**
     * Solicita a recuperação de senha do {@link Usuario}.
     *
     * @param login -
     * @return -
     */
	public Usuario recuperarSenha(final String login) {
		Usuario usuario = findByLoginUsuario(login);

		if (usuario == null) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_USUARIO_NAO_ENCONTRADO);
		}

		emailService.enviarEmailEsqueciSenha(usuario);
		return usuario;
	}

    /**
     * Inativa o {@link Usuario}.
     *
     * @param id
     * @return
     */
	public Usuario inativar(final Long id) {
		Usuario usuario = getById(id);
		usuario.setStatus(StatusAtivoInativo.INATIVO);
		return usuarioRepository.save(usuario);
	}

    /**
     * Ativa o {@link Usuario}.
     *
     * @param id
     * @return
     */
	public Usuario ativar(final Long id) {
		Usuario usuario = getById(id);
		usuario.setStatus(StatusAtivoInativo.ATIVO);
		return usuarioRepository.save(usuario);
	}

	/**
	 * Verifica se o Login informado é tem um valor válido.
	 *
	 * @param login
	 * @return
	 */
	private boolean isLoginValido(final String login) {
		boolean valido = false;

		//Colocar outras vaidações se necessário
		if (Strings.isNotEmpty(login)) {
			valido = true;
		}

		if(Strings.isNotEmpty(login) && login.length()>3){
			valido = true;
		}
		return valido;
	}

	/**
	 * Verifica se o login informado é válido e se está em uso.
	 * 
	 * @param login
	 */
	public void validarLogin(final String login) {
		validarLogin(login, null);
	}

	/**
	 * Verifica se o Login informado é válido e se está em uso.
	 * 
	 * @param login
	 * @param id
	 */
	public void validarLogin(final String login, final Long id) {
		if (!isLoginValido(login)) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_LOGIN_INVALIDO);
		}

		Usuario usuario;

		if (id == null) {
			usuario = findByLoginUsuario(login);
		} else {
			usuario = findByLoginUsuarioAndNotId(login, id);
		}

		if (usuario != null) {
			throw new BusinessException(ModuleAdminMessageCode.ERRO_LOGIN_EM_USO);
		}
	}
	public JasperPrint gerarRelatorio(Long idGrupo){
		try {
			Connection connection = dataSource.getConnection();
			Map<String, Object> params = new HashMap<>();
			params.put("id_grupo",idGrupo);
			JasperDesign jd =
					JRXmlLoader.load(
							this.getClass()
									.getResourceAsStream("/module-admin/relatorios/usuarios_por_grupo.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(jd);
			JasperPrint jasperPrint =
					JasperFillManager.fillReport(jasperReport, params, connection);
			return jasperPrint;
			//TODO Não foi feito o tratamento correto ainda
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void inicializar(String senha) {
		if(Strings.isNotEmpty(senha) && senha.equals("inicializar")){
			inicializarService.inicializar();
		}else{
			throw new BusinessException(ModuleAdminMessageCode.ERRO_LOGIN_INVALIDO);
		}
	}
}
