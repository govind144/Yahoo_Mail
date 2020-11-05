package com.teama.yahoomail.exception;

public class EmailAlreadyUsedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmailAlreadyUsedException(String message) {
		super(message);
	}
}
