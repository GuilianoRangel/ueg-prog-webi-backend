/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.dto.model;

import br.ueg.prog.webi.adminmodule.model.ModuloSistema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.List;

/**
 * Classe de transferência referente a entidade {@link ModuloSistema}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de Modulos do sistema")
public @Data class ModuloSistemaDTO implements Serializable {

	private static final long serialVersionUID = -3191642221341828960L;

	@Schema(description =  "Código do Modulo do sistema")
	private String id;

	@Size(max = 200)
	@Schema(description =  "Nome do Modulo do sistema")
	private String nome;

	@Size(max = 40)
	@Schema(description =  "Código Mnemonico da Funcionalidade")
	private String mnemonico;

	@Schema(description =  "Código do Status do Usuário")
	private String idStatus;

	@Schema(description =  "Descrição do Status do Usuário")
	private String descricaoStatus;

	@Schema(description =  "Lista de Funcionalidades do Módulo")
	private List<FuncionalidadeDTO> funcionalidades;

	@Schema(description = "Indica se todos os modulos estão checados, onlyFront")
	private Boolean todosChecked;

	@Schema(description = "Indica se o acordeon está aberto, onlyFront")
	private Boolean isAccordionOpen;

	/**
	 * @return the id
	 */
	@JsonIgnore
	public Long getIdLong() {
		Long idLong = null;

		if (Strings.isNotEmpty(id)) {
			idLong = Long.parseLong(id);
		}
		return idLong;
	}
}
