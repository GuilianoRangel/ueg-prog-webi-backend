/*
 * EmailException.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.prog.webi.adminmodule.util.email;

/**
 * Exception class to be thrown, no emailing process.
 * 
 * @author UEG
 */
public class EmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe.
	 *
	 * @param msg -
	 */
	public EmailException(final String msg) {
		super(msg);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param msg  -
	 * @param e -
	 */
	public EmailException(final String msg, final Throwable e) {
		super(msg, e);
	}

}
