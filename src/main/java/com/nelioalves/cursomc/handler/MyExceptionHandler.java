package com.nelioalves.cursomc.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nelioalves.cursomc.exception.AuthorizationException;
import com.nelioalves.cursomc.exception.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger LOG = LoggerFactory.getLogger(MyExceptionHandler.class);

	private static final String ERROR_MESSAGE = "Application Error";

	/**
	 * This exception is thrown when data not found
	 */
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
		LOG.error(ERROR_MESSAGE, ex);
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), "error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * This exception is thrown when constraint validation
	 */
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected ResponseEntity<Object> handleConstraint(RuntimeException ex, WebRequest request) {
		LOG.error(ERROR_MESSAGE, ex);
		List<String> errors = new ArrayList<String>();
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Constraint violation", errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ApiError> authorization(AuthorizationException e, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		ApiError err = new ApiError(HttpStatus.FORBIDDEN, e.getMessage(), errors);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	/**
	 * This exception is thrown when argument annotated with @Valid failed
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.error(ERROR_MESSAGE, ex);
		List<String> errors = new ArrayList<>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid parameters", errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	class ApiError {

		private HttpStatus status;
		private String message;
		private List<String> errors;

		public ApiError(HttpStatus status, String message, List<String> errors) {
			super();
			this.status = status;
			this.message = message;
			this.errors = errors;
		}

		public ApiError(HttpStatus status, String message, String error) {
			super();
			this.status = status;
			this.message = message;
			errors = Arrays.asList(error);
		}

		public HttpStatus getStatus() {
			return this.status;
		}

		public String getMessage() {
			return this.message;
		}

		public List<String> getErrors() {
			return this.errors;
		}
	}

}
