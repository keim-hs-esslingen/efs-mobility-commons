/*
 * MIT License
 * 
 * Copyright (c) 2020 Hochschule Esslingen
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. 
 */
package de.hsesslingen.keim.efs.mobility.exception.handler;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import de.hsesslingen.keim.efs.mobility.exception.AbstractEfsException;
import de.hsesslingen.keim.efs.mobility.exception.EfsError;

/**
 * ExceptionHandler class for centralized exception handling with an unified error structure 
 * using @ExceptionHandler for a various number of Exceptions. This class extends {@link ResponseEntityExceptionHandler}
 * 
 * @author k.sivarasah
 * 11 Sep 2019
 */

@ControllerAdvice
@ResponseBody
public class EfsExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(EfsExceptionHandler.class);
	private static final String CAUGHT = "{} caught with message {}";
	
	@ExceptionHandler(AbstractEfsException.class)
	public ResponseEntity<EfsError> handleNotFoundException(AbstractEfsException e) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		
		EfsError error;
		if(e.getError() != null) {
			error = e.getError();
		} else {
			error = new EfsError(e.getMessage(), e.getHttpStatus().value(), null);
		}
		return new ResponseEntity<>(error, e.getHttpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		Map<String, Object> details = e.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		return handleExceptionInternal(e, new EfsError("Bad Request", HttpStatus.BAD_REQUEST.value(), details), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public EfsError handleMethodArgumentNotValid(ConstraintViolationException e, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		Map<String, Object> details = new HashMap<>(); 
		e.getConstraintViolations().forEach(violation -> details.put(violation.getPropertyPath().toString(), violation.getMessage()));
		return new EfsError("Validation failed", HttpStatus.BAD_REQUEST.value(), details);
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		Map<String, Object> details = e.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		return handleExceptionInternal(e, new EfsError("Bad Request", HttpStatus.BAD_REQUEST.value(), details), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		return handleExceptionInternal(e, new EfsError(e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST.value(), null), headers,
				HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		return handleExceptionInternal(e, new EfsError(e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST.value(), null), headers,
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public EfsError handleIllegalState(IllegalStateException e, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		return new EfsError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
	}

	@ExceptionHandler({ResourceAccessException.class, ConnectException.class})
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public EfsError handleConnectException(Exception e, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		return new EfsError("A depending service is unavailable: " + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value(), null);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		return handleExceptionInternal(e, new EfsError(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null), headers,
				HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(CAUGHT, e.getClass().getSimpleName(), e.getMessage(), e);
		String message = String.format("%s. Allowed methods are: %s", e.getMessage(), e.getSupportedHttpMethods()); 
		return handleExceptionInternal(e, new EfsError(message, HttpStatus.METHOD_NOT_ALLOWED.value(), null), headers,
				HttpStatus.METHOD_NOT_ALLOWED, request);
	}
	
	
	
}