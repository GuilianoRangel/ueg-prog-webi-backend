/*
 * StatusAtivoInativo.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.faculdade.model.enums;

import java.util.Arrays;

/**
 * Enum com as possíveis representações de Status Ativo/Inativo.
 * 
 * @author UEG
 */
public enum StatusAtivoInativo {

	ATIVO("A", "Ativo"), INATIVO("I", "Inativo");

	private final String id;

	private final String descricao;

	/**
	 * Construtor da classe.
	 *
	 * @param id -
	 * @param descricao -
	 */
	StatusAtivoInativo(final String id, final String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Retorna a instância de {@link StatusAtivoInativo} conforme o 'id' informado.
	 * 
	 * @param id -
	 * @return -
	 */
	public static StatusAtivoInativo getById(final String id) {
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
	}

	/**
	 * Retorna a instância de {@link StatusAtivoInativo} conforme o 'id' informado.
	 *
	 * @param id -
	 * @return -
	 */
	public static StatusAtivoInativo getById(final boolean id) {
		return id?StatusAtivoInativo.ATIVO:StatusAtivoInativo.INATIVO;
	}

}
