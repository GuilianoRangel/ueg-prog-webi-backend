/*
 * UsuarioController.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.controller;

import br.ueg.prog.webi.adminmodule.dto.filtros.FiltroUsuarioDTO;
import br.ueg.prog.webi.adminmodule.dto.model.UsuarioDTO;
import br.ueg.prog.webi.adminmodule.mapper.UsuarioMapper;
import br.ueg.prog.webi.adminmodule.model.Usuario;
import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.service.UsuarioService;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de controle referente a entidade {@link Usuario}.
 * 
 * @author UEG
 */
@Tag(name = "Usuario API", description = "Manutenção de usuários do sistema")
@RestController
@RequestMapping("${app.api.base}/usuarios")
public class UsuarioController extends ModuleAdminAbstractController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioMapper usuarioMapper;

	/**
	 * Salva uma instância de {@link Usuario} na base de dados.
	 * 
	 * @param usuarioDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USUARIO_INCLUIR')")
	@Operation(description = "Inclui um novo Usuário na base de dados.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário INcluído",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UsuarioDTO.class))),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> incluir(
			@Parameter(description = "Informações do Usuário", required = true)
				@Valid @RequestBody UsuarioDTO usuarioDTO
	) {
		Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
		usuarioService.configurarUsuarioGruposAndTelefones(usuario);
		usuario = usuarioService.salvar(usuario);
		usuarioDTO = usuarioMapper.toDTO(usuario);
		return ResponseEntity.ok(usuarioDTO);
	}

    /**
	 * Altera a instância de {@link Usuario} na base de dados.
	 * 
	 * @param id
	 * @param usuarioDTO
	 * @return
	 */
    @PreAuthorize("hasRole('ROLE_USUARIO_ALTERAR')")
	@Operation(description = "Altera as informações de um Usuário na base de dados.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário Alterado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UsuarioDTO.class))),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
			@Parameter(description = "Código do Usuário", required = true)
				@PathVariable final BigDecimal id,
			@Parameter(description = "Informações do Usuário", required = true)
				@Valid @RequestBody UsuarioDTO usuarioDTO
	) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioService.configurarUsuarioGruposAndTelefones(usuario);
        usuario.setId(id.longValue());
        usuarioService.salvar(usuario);
		return ResponseEntity.ok(usuarioDTO);
    }

	/**
	 * Retorna a instância de {@link UsuarioDTO} conforme o 'id'
	 * informado.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USUARIO_VISUALIZAR')")
	@Operation(description = "Recupera o usuario pela id.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário Recuperado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UsuarioDTO.class))),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUsuarioById(
			@Parameter(description = "Id do Usuario")
				@PathVariable final BigDecimal id
	) {
		Usuario usuario = usuarioService.getByIdFetch(id.longValue());
		UsuarioDTO usuarioTO = new UsuarioDTO();

		if(usuario != null)
			usuarioTO = usuarioMapper.toDTO(usuario);

		return ResponseEntity.ok(usuarioTO);
	}

	/**
	 * Retorna a lista de {@link UsuarioDTO} de acordo com os filtros
	 * informados.
	 * 
	 * @param filtroDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USUARIO_PESQUISAR')")
	@Operation(description = "Recupera os usuarios pelo Filtro Informado de usuários ativos.", responses = {
			@ApiResponse(responseCode = "200", description = "Lista de Grupo pelo filtro",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "/filtro-ativo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUsuariosAtivosByFiltro(
			@Parameter(description = "Filtro de pesquisa", required = true)
				@Valid @ModelAttribute("filtroDTO") FiltroUsuarioDTO filtroDTO
	) {
		filtroDTO.setAtivo(true);
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		List<Usuario> usuarios = usuarioService.getUsuariosByFiltro(filtroDTO);
		if(usuarios != null){
			for (Usuario usuario: usuarios) {
				usuariosDTO.add(usuarioMapper.toDTO(usuario));
			}
		}

		return ResponseEntity.ok(usuariosDTO);
	}

	/**
	 * Retorna a lista de {@link UsuarioDTO} de acordo com os filtros
	 * informados.
	 * 
	 * @param filtroDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USUARIO_PESQUISAR')")
	@Operation(description = "Recupera os usuarios pelo Filtro Informado.", responses = {
			@ApiResponse(responseCode = "200", description = "Lista de Grupo pelo filtro",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class)))),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getUsuariosByFiltro(@Parameter(description = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") final FiltroUsuarioDTO filtroDTO) {
		List<Usuario> usuarios = usuarioService.getUsuariosByFiltro(filtroDTO);
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		for (Usuario usuario: usuarios) {
			usuario.setTelefones(null);
			usuariosDTO.add (usuarioMapper.toDTO(usuario));
		}

		return ResponseEntity.ok(usuariosDTO);
	}

	/**
	 * Inativa o {@link Usuario}.
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USUARIO_ATIVAR_INATIVAR')")
	@Operation(description = "Inativa o usuario.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@PutMapping(path = "/{id:[\\d]+}/inativo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inativar(
			@Parameter(description = "Código do Usuário", required = true)
				@PathVariable final BigDecimal id
	) {
		usuarioService.inativar(id.longValue());
		return ResponseEntity.ok().build();
	}

	/**
	 * Ativa o {@link Usuario}.
	 *
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USUARIO_ATIVAR_INATIVAR')")
	@Operation(description = "Ativa o usuário.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@PutMapping(path = "/{id:[\\d]+}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ativar(
			@Parameter(description = "Código do Usuário", required = true)
			@PathVariable final BigDecimal id
	) {
		usuarioService.ativar(id.longValue());
		return ResponseEntity.ok().build();
	}

	/**
	 * Verifica se o Login informado é válido e se está em uso.
	 * 
	 * @param login
	 * @return
	 */
	@Operation(description = "Verifica se o Login informado é válido e se está em uso.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "login/valido/{login}")
	public ResponseEntity<?> validarLogin(
			@Parameter(description = "LOGIN") @PathVariable final String login
	) {
		usuarioService.validarLogin(login);
		return ResponseEntity.ok().build();
	}

	/**
	 * Verifica se o Login informado é válido e se está em uso.
	 * 
	 * @param login
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@Operation(description = "Verifica se o Login informado é válido e se está em uso.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "/{id:[\\d]+}/login/valido/{login}")
	public ResponseEntity<?> validarLoginUsuario(
			@Parameter(description = "Código do Usuário") @PathVariable final BigDecimal id,
			@Parameter(description = "LOGIN") @PathVariable final String login) {
		usuarioService.validarLogin(login, id.longValue());
		return ResponseEntity.ok().build();
	}

	//@PreAuthorize("isAuthenticated()")
	@Operation(description = "Retorna Relatório de Usuários.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE)),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "/relatorio-usuarios/{idGrupo}",produces = { MediaType.APPLICATION_PDF_VALUE })
	public ResponseEntity<?> getRelatorioGrupos (
			@Parameter(description = "Código do Grupo") @PathVariable final Long idGrupo
	) throws IOException, JRException {
		return this.toPDF(usuarioService.gerarRelatorio(idGrupo),"Relatorio-usuarios.pdf");
	}

	//@PreAuthorize("isAuthenticated()")
	@Operation(description = "Retorna Relatório de Grupos.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE)),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "/relatorio-usuarios",produces = { MediaType.APPLICATION_PDF_VALUE })
	public ResponseEntity<?> getRelatorioGrupos2 () throws IOException, JRException {
		return this.toPDF(usuarioService.gerarRelatorio(null),"Relatorio-grupo2.pdf");
	}

	@PreAuthorize("isAnonymous()")
	@Operation(description = "Carregar dados iniciais - sistema admin Module", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Registro não encontrado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Acesso negado",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Erro de Negócio",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = MessageResponse.class)))
	})
	@GetMapping(path = "/inicializar/{senha}")
	public ResponseEntity<?> inicializar(
			@Parameter(description = "senha") @PathVariable final String senha) {
		usuarioService.inicializar(senha);
		return ResponseEntity.ok().build();
	}
}
