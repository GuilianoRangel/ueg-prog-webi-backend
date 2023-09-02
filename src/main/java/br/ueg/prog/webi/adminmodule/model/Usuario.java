/*
 * Usuario.java
 * Copyright (c) UEG.
 */
package br.ueg.prog.webi.adminmodule.model;


import br.ueg.prog.webi.adminmodule.model.enums.StatusAtivoInativo;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusAtivoInativoConverter;
import br.ueg.prog.webi.adminmodule.model.enums.StatusSimNao;
import br.ueg.prog.webi.adminmodule.model.enums.converter.StatusSimNaoConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Classe de representação de 'Sistema'.
 *
 * @author UEG
 */
@Entity
@Table(name = "TBL_USUARIO")
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name = "TBL_S_USUARIO", sequenceName = "TBL_S_USUARIO", allocationSize = 1)
public @Data class Usuario implements Serializable{

	private static final long serialVersionUID = -8899975090870463525L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_USUARIO")
	@Column(name = "id_usuario", nullable = false)
	private Long id;

	@CreationTimestamp
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDate dataAtualizado;

	@UpdateTimestamp
	@Column(name = "data_cadastro", nullable = false)
	private LocalDate dataCadastrado;

	@Column(name = "email", length = 75, nullable = false)
	private String email;

	@Column(name = "login", length = 20, nullable = false)
	private String login;

	@Column(name = "senha", length = 255, nullable = false)
	private String senha;

	@Column(name = "nome", length = 65, nullable = false)
	private String nome;

	@Convert(converter = StatusAtivoInativoConverter.class)
	@Column(name = "status", nullable = false, length = 1)
	private StatusAtivoInativo status;

	@Convert(converter = StatusSimNaoConverter.class)
	@Column(name = "bloqueado", length = 1)
	private StatusSimNao acessoBloqueado;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UsuarioGrupo> grupos;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TelefoneUsuario> telefones;

	/**
	 * Verifica se o Status do Usuário é igual a 'Ativo'.
	 *
	 * @return -
	 */
	@Transient
	public boolean isStatusAtivo() {
		return status != null && StatusAtivoInativo.ATIVO.getId().equals(status.getId());
	}

}
