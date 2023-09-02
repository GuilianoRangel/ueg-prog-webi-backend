/*
 * UsuarioDTO.java  
 * Copyright UEG.
 * 
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.dto.model;

import br.ueg.prog.webi.adminmodule.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe de transferência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Entidade de transferência de Usuario")
public @Data class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = -3145730384721847808L;

	@Schema(description =  "Código do Usuário")
	private String id;

	@JsonFormat(shape = Shape.STRING)
	@Schema(description =  "Data da última atualização do cadastro do Usuário")
	private LocalDate dataAtualizado;

	@JsonFormat(shape = Shape.STRING)
	@Schema(description =  "Data do cadastro do Usuário")
	private LocalDate dataCadastrado;

	@Size(max = 75)
	@Schema(description =  "Email do usuário")
	private String email;

	@Size(max = 20)
	@Schema(description =  "Login do Usuário")
	private String login;

	@Size(max = 65)
	@Schema(description =  "Nome do Usuário")
	private String nome;

	@Schema(description =  "Código do Status do Usuário")
	private boolean status;

	@Schema(description =  "Acesso do Usuário Bloqueado")
	private boolean acessoBloqueado;

	@Schema(description =  "Grupos do Usuário")
	private List<UsuarioGrupoDTO> grupos;

	@Schema(description =  "Telefones do Usuário")
	private List<TelefoneUsuarioDTO> telefones;

	@JsonIgnore
	@Schema(hidden = true)
	private Long idUsuarioLogado;

	public UsuarioDTO(String id, String login) {
		this.id = id;
		this.login = login;
	}

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
