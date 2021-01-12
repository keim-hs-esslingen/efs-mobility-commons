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

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
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

import de.hsesslingen.keim.efs.mobility.exception.HttpException;
import de.hsesslingen.keim.efs.mobility.exception.MiddlewareError;
import static de.hsesslingen.keim.efs.mobility.exception.MiddlewareError.*;
import de.hsesslingen.keim.efs.mobility.exception.MiddlewareException;
import static java.util.stream.Collectors.toMap;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;

/**
 * ExceptionHandler class for centralized exception handling with an unified
 * error structure using @ExceptionHandler for a various number of Exceptions.
 * This class extends {@link ResponseEntityExceptionHandler}
 *
 * @author k.sivarasah 11 Sep 2019
 */
@ControllerAdvice
@ResponseBody
public class MiddlewareServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private static final Logger logger = getLogger(MiddlewareServiceExceptionHandler.class);

    private static final String CAUGHT_MSG = "{} caught with message {}";

    @ExceptionHandler(MiddlewareException.class)
    public ResponseEntity<MiddlewareError> handleMiddlewareException(MiddlewareException e) {
        logger.error(CAUGHT_MSG, e.getClass().getSimpleName(), e.getMessage(), e);

        HttpStatus httpStatus;

        switch (e.getCode()) {
            case TOKEN_INVALID_ERROR_CODE:
                httpStatus = UNAUTHORIZED;
                break;
            case REMOTE_SERVICE_UNAVAILABLE_ERROR_CODE:
                httpStatus = BAD_GATEWAY;
                break;
            default:
                httpStatus = INTERNAL_SERVER_ERROR;
                break;
        }

        return new ResponseEntity<>(e.toError(), httpStatus);
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<MiddlewareError> handleHttpException(HttpException e) {
        logger.error(CAUGHT_MSG, e.getClass().getSimpleName(), e.getMessage(), e);
        var error = new MiddlewareError(e.getHttpStatus().value(), e.getMessage());
        return new ResponseEntity<>(error, e.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public MiddlewareError handleMethodArgumentNotValid(ConstraintViolationException e, WebRequest request) {
        logger.error(CAUGHT_MSG, e.getClass().getSimpleName(), e.getMessage(), e);

        var details = new HashMap<String, Object>();

        for (var violation : e.getConstraintViolations()) {
            details.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return new MiddlewareError(BAD_REQUEST.value(), details, "Validation failed");
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public MiddlewareError handleIllegalState(IllegalStateException e, WebRequest request) {
        logger.error(CAUGHT_MSG, e.getClass().getSimpleName(), e.getMessage(), e);
        return new MiddlewareError(INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler({ResourceAccessException.class, ConnectException.class})
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public MiddlewareError handleConnectException(Exception e, WebRequest request) {
        logger.error(CAUGHT_MSG, e.getClass().getSimpleName(), e.getMessage(), e);
        return new MiddlewareError(SERVICE_UNAVAILABLE.value(), "A depending service is unavailable: " + e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.error(CAUGHT_MSG, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        // Collect error details from field errors to put them as details into the retunred MiddlewareError.
        Map<String, Object> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(toMap(FieldError::getField, FieldError::getDefaultMessage));

        var error = new MiddlewareError(BAD_REQUEST.value(), details, "Bad Request");

        return handleExceptionInternal(ex, error, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.error(CAUGHT_MSG, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        // Collect error details from field errors to put them as details into the retunred MiddlewareError.
        Map<String, Object> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(toMap(FieldError::getField, FieldError::getDefaultMessage));

        var error = new MiddlewareError(BAD_REQUEST.value(), details, "Bad Request");

        return handleExceptionInternal(ex, error, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.error(CAUGHT_MSG, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        var error = new MiddlewareError(BAD_REQUEST.value(), ex.getMostSpecificCause().getMessage());

        return handleExceptionInternal(ex, error, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.error(CAUGHT_MSG, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        var error = new MiddlewareError(BAD_REQUEST.value(), ex.getMostSpecificCause().getMessage());

        return handleExceptionInternal(ex, error, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.error(CAUGHT_MSG, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        var error = new MiddlewareError(BAD_REQUEST.value(), ex.getMessage());

        return handleExceptionInternal(ex, error, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        logger.error(CAUGHT_MSG, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        String message = String.format("%s. Allowed methods are: %s", ex.getMessage(), ex.getSupportedHttpMethods());

        var error = new MiddlewareError(METHOD_NOT_ALLOWED.value(), message);

        return handleExceptionInternal(ex, error, headers, METHOD_NOT_ALLOWED, request);
    }

}
