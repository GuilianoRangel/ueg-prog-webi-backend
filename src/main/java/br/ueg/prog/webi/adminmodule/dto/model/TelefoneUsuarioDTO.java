/*
 * TelefoneUsuarioDTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.dto.model;

import br.ueg.prog.webi.adminmodule.model.TelefoneUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link TelefoneUsuario}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de Telefone")
public @Data class TelefoneUsuarioDTO implements Serializable {

	private static final long serialVersionUID = -2194758975061682611L;

	@Schema(description =  "Código do Telefone")
	private String id;

	@Schema(description =  "Código do Usuário")
	private String idUsuario;

	@Size(max = 11)
	@Schema(description =  "Número do Telefone")
	private String numero;

	@Schema(description =  "Código do Tipo do Telefone")
	private Long idTipo;

	@Schema(description =  "Descrição do Tipo do Telefone")
	private String descricaoTipo;

	@Size(max = 5)
	@Schema(description =  "DDD do Telefone")
	private String ddd;

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
