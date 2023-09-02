package br.ueg.prog.webi.adminmodule.model.enums;

import java.util.Arrays;

/**
 * Enum com as possíveis representações de Status Sim/Não.
 *
 * @author UEG
 */
public enum StatusSimNao {

	SIM("S", "Sim"), NAO("N", "Não");

	private final String id;

	private final String descricao;

	/**
	 * Construtor da classe.
	 *
	 * @param id -
	 * @param descricao -
	 */
	StatusSimNao(final String id, final String descricao) {
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
	 * Retorna a instância de {@link StatusSimNao} conforme o 'id' informado.
	 *
	 * @param id -
	 * @return -
	 */
	public static StatusSimNao getById(final String id) {
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
	}


	/**
	 * Retorna a instância de true ou false conforme o 'id' informado.
	 *
	 * @param idb -
	 * @return -
	 */
	public static StatusSimNao getByIdStatusSimNao(final boolean idb) {
		String id = idb ? "S" : "N";
		return Arrays.stream(values()).filter(value -> value.getId().equals(id)).findFirst().orElse(null);
	}

}
