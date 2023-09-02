/*
 * StatusSimNaoConverter.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.model.enums.converter;

import br.ueg.prog.webi.adminmodule.model.enums.StatusSimNao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Classe de conversão JPA referente ao enum {@link StatusSimNao}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class StatusSimNaoConverter implements AttributeConverter<StatusSimNao, String> {

	/**
	 * @see AttributeConverter#convertToDatabaseColumn(Object)
	 */
	@Override
	public String convertToDatabaseColumn(final StatusSimNao status) {
		return status != null ? status.getId() : null;
	}

	/**
	 * @see AttributeConverter#convertToEntityAttribute(Object)
	 */
	@Override
	public StatusSimNao convertToEntityAttribute(String id) {
		return StatusSimNao.getById(id);
	}

}
