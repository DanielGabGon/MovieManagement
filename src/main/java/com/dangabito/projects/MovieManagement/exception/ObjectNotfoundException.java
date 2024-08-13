package com.dangabito.projects.MovieManagement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ObjectNotfoundException extends RuntimeException {

	private static Logger logger = LoggerFactory.getLogger(ObjectNotfoundException.class);

	private static final long serialVersionUID = -6472582166204179025L;

	private final String objectNotFoundName;

	private final Throwable cause;

	public ObjectNotfoundException(String objectNotFoundName) {
		logger.info("ENTRA EXCEPTION:{}", objectNotFoundName);
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
		logger.info("MENSAJE DE RETORNO :{}", this.objectNotFoundName);
		return baseMessage.concat("(object not found").concat(this.objectNotFoundName).concat(")");
	}

	public String getObjectNotFoundName() {
		return objectNotFoundName;
	}

}
