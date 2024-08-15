package com.dangabito.projects.MovieManagement.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dangabito.projects.MovieManagement.dto.response.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({ Exception.class, ObjectNotfoundException.class, InvalidPasswordException.class,
			MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class,
			HttpRequestMethodNotSupportedException.class, HttpMessageNotReadableException.class })
	public ResponseEntity<ApiError> handleAllException(Exception exception, HttpServletRequest request,
			HttpServletResponse response) {
		LocalDateTime timeStamp = LocalDateTime.now();
		logger.info("HORARIO: CDMX:{}", timeStamp);
		if (exception instanceof ObjectNotfoundException objectNotfoundException) {
			return this.handleObjectNotFoundException(objectNotfoundException, request, response, timeStamp);
		} else if (exception instanceof InvalidPasswordException invalidPasswordException) {
			return this.handleInvalidPasswordException(invalidPasswordException, request, response, timeStamp);
		} else if (exception instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
			return this.handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException, request,
					response, timeStamp);
		} else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
			return this.handleMethodArgumentNotValidException(methodArgumentNotValidException, request, response,
					timeStamp);
		}else if (exception instanceof HttpRequestMethodNotSupportedException  httpRequestMethodNotSupportedException) {
			return this.handleHttpRequestMethodNotSupportedException(httpRequestMethodNotSupportedException, request,
					response, timeStamp);
		}else if (exception instanceof HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
			return this.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, request, response,
					timeStamp);
		} else if (exception instanceof HttpMessageNotReadableException httpMessageNotReadableException) {
			return this.handleHttpMessageNotReadableException(httpMessageNotReadableException, request, response,
					timeStamp);
		}else {
			return this.handleException(exception, request, response, timeStamp);
		}
	}

	private ResponseEntity<ApiError> handleObjectNotFoundException(ObjectNotfoundException objectNotfoundException,
			HttpServletRequest request, HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.NOT_FOUND.value();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"I´m sorry, the requested information could not be found. Please check the URL or try another search.",
				objectNotfoundException.getMessage(), timeStamp, null);
		return ResponseEntity.status(httpStatus).body(apiError);
	}

	private ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException invalidPasswordException,
			HttpServletRequest request, HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.BAD_REQUEST.value();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"Invalid Password: The provided password does not meet the required criteria, "
						+ invalidPasswordException.getErrorDescription(),
				invalidPasswordException.getMessage(), timeStamp, null);
		return ResponseEntity.status(httpStatus).body(apiError);
	}

	private ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request,
			HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"Ooop! Something went wrong on our server. Please try again later.", exception.getMessage(), timeStamp,
				null);
		return ResponseEntity.status(httpStatus).body(apiError);
	}

	private ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, HttpServletRequest request,
			HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.BAD_REQUEST.value();
		Object valueRejected = methodArgumentTypeMismatchException.getValue();
		String propertyName = methodArgumentTypeMismatchException.getName();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"Invalid Request: The provided value '" + valueRejected
						+ "' does not have the expected data type for the " + propertyName + ".",
				methodArgumentTypeMismatchException.getMessage(), timeStamp, null);
		return ResponseEntity.status(httpStatus).body(apiError);
	}

	private ResponseEntity<ApiError> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request,
			HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.BAD_REQUEST.value();

		List<ObjectError> errors = methodArgumentNotValidException.getAllErrors();
		List<String> details = errors.stream().map(error -> {
			if (error instanceof FieldError fieldError) {
				return fieldError.getField() + ":" + fieldError.getDefaultMessage();
			}
			return error.getDefaultMessage();
		}).toList();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"The request contains invalid or incomplete parameters. "
						+ "Please verify and provide the required information before trying again.",
				methodArgumentNotValidException.getMessage(), timeStamp, details);
		return ResponseEntity.status(httpStatus).body(apiError);
	}

	/**
	 * HttpRequestMethodNotSupportedException se lanza cuando se hace una+ solicitud
	 * HTTP con un método que no está soportado por el endpoint en cuestión.
	 * 
	 * @param httpRequestMethodNotSupportedException
	 * @param request
	 * @param response
	 * @param timeStamp
	 * @return
	 */
	private ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException, HttpServletRequest request,
			HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"Oops! Method Not Allowed. Check the HTTP method of your request.",
				httpRequestMethodNotSupportedException.getMessage(), timeStamp, null);
		return ResponseEntity.status(httpStatus).body(apiError);
	}

	private ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, HttpServletRequest request,
			HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"Unsupported Media Type: The server is unable to process the requested entity in the format provided in the request. "+
						" Supported media types are:" + httpMediaTypeNotSupportedException.getSupportedMediaTypes()
						+ " and you send: " + httpMediaTypeNotSupportedException.getContentType(),
				httpMediaTypeNotSupportedException.getMessage(), timeStamp, null);
		return ResponseEntity.status(httpStatus).body(apiError);
	}

	private ResponseEntity<ApiError> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException httpMessageNotReadableException,
			HttpServletRequest request, HttpServletResponse response, LocalDateTime timeStamp) {
		int httpStatus = HttpStatus.BAD_REQUEST.value();
		ApiError apiError = new ApiError(httpStatus, request.getRequestURL().toString(), request.getMethod(),
				"Oops! Error reading the HTTP message body. "
						+ "Make sure the request is correctly formatted and contains valid dat.",
				httpMessageNotReadableException.getMessage(), timeStamp, null);
		return ResponseEntity.status(httpStatus).body(apiError);
	}
}
