/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.dto.model;


import br.ueg.prog.webi.adminmodule.model.Grupo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe de transferência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de Grupo")
public @Data class GrupoDTO implements Serializable {

	private static final long serialVersionUID = -4907983765144204384L;

	@Schema(description = "Código do Grupo de Usuários")
	private Long id;

	@Size(max = 50)
	@Schema(description = "Nome do Grupo de usuários")
	private String nome;

	@Size(max = 200)
	@Schema(description = "Descricao do Grupo de Usuários")
	private String descricao;

	@Schema(description = "Código do Status do Grupo de Usuários")
	private boolean status;

	@Schema(description = "indica se o Grupo de Usuários é de administradores")
	private boolean administrador;

	@Schema(description = "Lista de Grupo Funcionalidades que o Grupo de usuário possui")
	private Set<GrupoFuncionalidadeDTO> grupoFuncionalidades;
}
