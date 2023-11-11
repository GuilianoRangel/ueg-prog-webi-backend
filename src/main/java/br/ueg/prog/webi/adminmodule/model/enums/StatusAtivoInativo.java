/*
 * StatusAtivoInativo.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.model.enums;



import br.ueg.prog.webi.api.interfaces.ISearchFieldData;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enum com as possíveis representações de Status Ativo/Inativo.
 * 
 * @author UEG
 */
@Getter
public enum StatusAtivoInativo implements ISearchFieldData<String> {

	ATIVO("A", "Ativo"),
	INATIVO("I", "Inativo");

	private final String id;

	private final String description;

	/**
	 * Construtor da classe.
	 *
	 * @param id -
	 * @param descricao -
	 */
	StatusAtivoInativo(final String id, final String descricao) {
		this.id = id;
		this.description = descricao;
	}

	/**
	 * Retorna a instância de {@link StatusAtivoInativo} conforme o 'id' informado.
	 * 
	 * @param id -
	 * @return -
	 */
	public static StatusAtivoInativo getById(final String id) {
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(StatusAtivoInativo.INATIVO);
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
