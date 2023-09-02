/*
 * TipoTelefoneUsuario.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.model.enums;

import java.util.Arrays;

/**
 * Enum com as possíveis representações de Tipos de Telefone.
 *
 * @author UEG
 */
public enum TipoTelefone {

	CELULAR(1L, "Celular"), RESIDENCIAL(2L, "Residencial"), COMERCIAL(3L, "Comercial");

	private final Long id;

	private final String descricao;

	/**
	 * Construtor da classe.
	 * 
	 * @param id
	 * @param descricao
	 */
	TipoTelefone(final Long id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Retorna a instância de {@link TipoTelefone} conforme o 'id' informado.
	 * 
	 * @param id
	 * @return
	 */
	public static TipoTelefone getById(final Long id) {
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
	}
}
