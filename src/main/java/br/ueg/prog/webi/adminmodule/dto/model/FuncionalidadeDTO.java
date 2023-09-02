/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.dto.model;

import br.ueg.prog.webi.adminmodule.model.Funcionalidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link Funcionalidade}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de Funcionalidade")
public @Data class FuncionalidadeDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 5911613757185877040L;

	@Schema(description = "Código da Funcionalidade")
	private String id;

	@Size(max = 200)
	@Schema(description = "Nome da Funcionalidade")
	private String nome;

	@Size(max = 40)
	@Schema(description = "Código Mnemonico da Funcionalidade")
	private String mnemonico;

	@Schema(description = "Código do Status do Usuário")
	private String idStatus;

	@Schema(description = "Descrição do Status do Usuário")
	private String descricaoStatus;

	@Schema(description = "Indica se a funcionalidade está marcada, OnlyFront")
	private Boolean checked;

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
