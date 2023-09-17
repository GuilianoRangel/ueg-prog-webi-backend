/*
 * SistemaMessageCode.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.faculdade.exception;

import br.ueg.prog.webi.api.exception.ApiMessageCode;
import br.ueg.prog.webi.api.exception.MessageCode;

/**
 * Enum com os código de exceções/mensagens de negócio.
 * 
 * @author UEG S/A.
 */
public enum SistemaMessageCode implements MessageCode {
	ERRO_CAMPOS_OBRIGATORIOS("MSG-001", 400),
	MSG_OPERACAO_REALIZADA_SUCESSO("MSG-002", 200),
	MSG_CHAVE_JA_UTILIZADA("MSG-003", 400),
	MSG_CHAVE_JA_EXISTE("MSG-004", 400),
	MSG_LOCAL_JA_EXISTE("MSG-005", 400);
	;

	private final String code;

	private final Integer status;

	/**
	 * Construtor da classe.
	 * 
	 * @param code -
	 * @param status -
	 */
	SistemaMessageCode(final String code, final Integer status) {
		this.code = code;
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @see Enum#toString()
	 */
	@Override
	public String toString() {
		return code;
	}
}
