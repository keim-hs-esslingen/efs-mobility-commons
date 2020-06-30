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
package de.hsesslingen.keim.efs.mobility.exception;

import org.springframework.http.HttpStatus;

import de.hsesslingen.keim.efs.mobility.exception.handler.EfsExceptionHandler;

/**
 * Abstract exception class that should be used as base class for any unchecked
 * exceptions inside EFS. Exceptions inherited from AbstractEfsException can be
 * handled properly by {@link EfsExceptionHandler} with all necessary details#
 *
 * @author k.sivarasah 24 Sep 2019
 * @author b.oesch
 */
public abstract class AbstractEfsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * The HTTP status associated with this exception.
     */
    protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private EfsError error;

    /**
     * @see AbstractEfsException
     * @param message
     */
    public AbstractEfsException(String message) {
        super(message);
    }

    /**
     * @see AbstractEfsException
     * @param message
     * @param httpStatus
     */
    public AbstractEfsException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * @see AbstractEfsException
     * @param message
     * @param httpStatus
     * @param cause
     */
    public AbstractEfsException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    /**
     * @see AbstractEfsException
     * @param message
     * @param cause
     */
    public AbstractEfsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see AbstractEfsException
     * @param message
     * @param error
     */
    public AbstractEfsException(String message, EfsError error) {
        super(message);
        this.error = error;
        this.httpStatus = HttpStatus.resolve(error.getCode());
    }

    /**
     * @see AbstractEfsException
     * @param error
     * @param httpStatus
     */
    public AbstractEfsException(EfsError error, HttpStatus httpStatus) {
        super(error.getMessage());
        this.error = error;
        this.httpStatus = httpStatus;
    }

    /**
     * @see AbstractEfsException
     * @param error
     */
    public AbstractEfsException(EfsError error) {
        super(error.getMessage());
        this.error = error;
    }

    /**
     * Get the HTTP status associated with this exception.
     *
     * @return
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Get the contained EfsError if present.
     *
     * @return
     */
    public EfsError getError() {
        return error;
    }

}
