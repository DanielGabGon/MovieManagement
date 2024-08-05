package com.dangabito.projects.MovieManagement.exception;

public class ObjectNotfoundException extends RuntimeException {

	private static final long serialVersionUID = -6472582166204179025L;

	private final String objectNotFoundName;

	private final Throwable cause;

	public ObjectNotfoundException(String objectNotFoundName) {
		System.out.println("ENTRA EXCEPTION:" + objectNotFoundName);
		this.objectNotFoundName = objectNotFoundName;
		this.cause = null;
	}

	public ObjectNotfoundException(String objectNotFoundName, Throwable cause) {
		this.objectNotFoundName = objectNotFoundName;
		this.cause = cause;
	}

	@Override
	public String getMessage() {
		String baseMessage = super.getMessage() != null ? super.getMessage() : "";
		System.out.println("MENSAJE DE RETORNO :" + this.objectNotFoundName);
		return baseMessage.concat("(object not found").concat(this.objectNotFoundName).concat(")");
	}

	public String getObjectNotFoundName() {
		return objectNotFoundName;
	}

}
