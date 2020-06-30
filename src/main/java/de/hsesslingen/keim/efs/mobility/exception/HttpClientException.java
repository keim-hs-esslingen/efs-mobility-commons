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

import de.hsesslingen.keim.efs.mobility.exception.handler.EfsRestClientErrorHandler;

/**
 * Exception that is used by {@link EfsRestClientErrorHandler} for converting
 * response errors with http status 4xx.
 *
 * @author k.sivarasah 12 Sep 2019
 * @author b.oesch
 */
public class HttpClientException extends AbstractEfsException {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpClientException
     * @param error
     */
    public HttpClientException(EfsError error) {
        super(error);
    }

    /**
     * @see HttpClientException
     * @param error
     * @param httpStatus
     */
    public HttpClientException(EfsError error, HttpStatus httpStatus) {
        super(error, httpStatus);
    }

    /**
     * @see HttpClientException
     * @param message
     * @param status
     */
    public HttpClientException(String message, HttpStatus status) {
        super(message, status);
    }

    /**
     * @see HttpClientException
     * @param message
     * @param status
     * @param cause
     */
    public HttpClientException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }

}
