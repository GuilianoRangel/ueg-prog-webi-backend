/*
 * UsuarioTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.dto.model;

import br.ueg.prog.webi.adminmodule.model.GrupoFuncionalidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link GrupoFuncionalidade}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de Grupo funcionalidades")
public @Data class GrupoFuncionalidadeDTO implements Serializable {

	private static final long serialVersionUID = -3858126875464908214L;

	@Schema(description =  "Código do Grupo Funcionalidade")
	private String id;

	@Schema(description =  "Código do Grupo")
	private String idGrupo;

	@Schema(description =  "Funcionalidade do Grupo")
	private FuncionalidadeDTO funcionalidade;



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
