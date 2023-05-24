/*
 * ResponseExceptionHandler.java
 * Copyright (c) UEG.
 */
package br.ueg.prog.webi.faculdade.exception;

import br.ueg.prog.webi.api.exception.ApiRestResponseExceptionHandler;
import br.ueg.prog.webi.api.exception.MessageCode;
import org.springframework.web.bind.annotation.ControllerAdvice;


/**
 * Classe handler responsável por interceptar e tratar as exceções de forma
 * amigavel para o client.
 * Para incluir novos tratamentos de exceção inclua sua exceção aqui nesse arquivo
 * 
 * @author Guiliano Rangel
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ApiRestResponseExceptionHandler {

}
