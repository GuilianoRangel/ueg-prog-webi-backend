/*
 * ModuloController.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.controller;

import br.ueg.prog.webi.adminmodule.dto.model.ModuloSistemaDTO;
import br.ueg.prog.webi.adminmodule.mapper.ModuloSistemaMapper;
import br.ueg.prog.webi.adminmodule.model.ModuloSistema;
import br.ueg.prog.webi.adminmodule.service.ModuloSistemaService;
import br.ueg.prog.webi.api.exception.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de controle referente a entidade {@link ModuloSistema}.
 * 
 * @author UEG
 */
@RestController
@Tag(name = "Módulo Sistema API", description = "Informações dos Modulos do Sistema")
@RequestMapping("${app.api.base}/modulos")
public class ModuloController extends ModuleAdminAbstractController {

	@Autowired
	private ModuloSistemaService moduloSistemaService;

	@Autowired
	private ModuloSistemaMapper moduloSistemaMapper;

	/**
	 * Retorna uma lista de {@link ModuloSistemaDTO} ativos conforme o 'id' do Sistema informado.
	 *
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@Operation(description = "Retorna uma lista de Módulos ativos.", responses = {
			@ApiResponse(responseCode = "200", description = "Listagem de Modulos do Sistema",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							array = @ArraySchema(schema = @Schema(implementation = ModuloSistemaDTO.class)))),
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
	@GetMapping(path = "/modulo/ativos", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getModulosAtivos() {
		List<ModuloSistema> modulos = moduloSistemaService.getAtivos();
		List<ModuloSistemaDTO> modulosDTO = new ArrayList<>();
		for(ModuloSistema modulo: modulos){
			modulosDTO.add(moduloSistemaMapper.toDTO(modulo));
		}
		return ResponseEntity.ok(modulosDTO);
	}
}
