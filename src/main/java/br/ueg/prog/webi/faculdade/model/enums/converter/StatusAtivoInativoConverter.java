/*
 * StatusAtivoInativoConverter.java
 * Copyright (c) UEG.
 */
package br.ueg.prog.webi.faculdade.model.enums.converter;


import br.ueg.prog.webi.faculdade.model.enums.StatusAtivoInativo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Classe de conversão JPA referente ao enum {@link StatusAtivoInativo}.
 * 
 * @author UEG
 */
@Converter(autoApply = true)
public class StatusAtivoInativoConverter implements AttributeConverter<StatusAtivoInativo, String> {

	@Override
	public String convertToDatabaseColumn(StatusAtivoInativo status) {
		return status != null ? status.getId() : null;
	}

	@Override
	public StatusAtivoInativo convertToEntityAttribute(String id) {
		return StatusAtivoInativo.getById(id);
	}

}
