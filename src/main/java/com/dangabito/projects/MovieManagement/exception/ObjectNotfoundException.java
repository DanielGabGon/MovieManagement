package com.dangabito.projects.MovieManagement.exception;

public class ObjectNotfoundException extends RuntimeException {

	private static final long serialVersionUID = -6472582166204179025L;

	private final String objectNotFoundName;

	private final Throwable cause;

	public ObjectNotfoundException(String objectNotFoundName) {
		this.objectNotFoundName = objectNotFoundName;
		this.cause = null;
	}

	public ObjectNotfoundException(String objectNotFoundName, Throwable cause) {
		this.objectNotFoundName = objectNotFoundName;
		this.cause = cause;
	}

	@Override
	public String getMessage() {
		return super.getMessage().concat("(object not found").concat(this.objectNotFoundName).concat(")");
	}

	public String getObjectNotFoundName() {
		return objectNotFoundName;
	}

}
