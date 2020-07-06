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

/**
 *
 * @author keim
 */
public class HttpException extends AbstractEfsException {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpClientException
     * @param error
     */
    public HttpException(EfsError error) {
        super(error);
    }

    /**
     * @see HttpClientException
     * @param error
     * @param httpStatus
     */
    public HttpException(HttpStatus httpStatus, EfsError error) {
        super(error, httpStatus);
    }

    /**
     * @see HttpClientException
     * @param message
     * @param status
     */
    public HttpException(HttpStatus status, String message) {
        super(message, status);
    }

    /**
     * @see HttpClientException
     * @param message
     * @param status
     * @param cause
     */
    public HttpException(HttpStatus status, Throwable cause, String message) {
        super(message, status, cause);
    }

    /**
     * Creates a new HttpException with the given properties. If no variables
     * are specified, the format string isn't formatted but rather used as
     * message directly.
     *
     * @param statusCode
     * @param format
     * @param variables
     * @return
     */
    public static HttpException status(HttpStatus statusCode, String format, Object... variables) {
        String msg = format;

        if (variables.length > 0) {
            msg = String.format(format, variables);
        }

        return new HttpException(statusCode, msg);
    }

    /**
     * Creates a new HttpException with the given properties. If no variables
     * are specified, the format string isn't formatted but rather used as
     * message directly.
     *
     * @param status
     * @param cause
     * @param format
     * @param variables
     * @return
     */
    public static HttpException status(HttpStatus status, Throwable cause, String format, Object... variables) {
        String msg = format;

        if (variables.length > 0) {
            msg = String.format(format, variables);
        }

        return new HttpException(status, cause, msg);
    }

    /**
     * Creates a new HttpException with the given properties.
     *
     * @param status
     * @param error
     * @return
     */
    public static HttpException status(HttpStatus status, EfsError error) {
        return new HttpException(status, error);
    }

    /**
     * Creates a new HttpException with status code INTERNAL_SERVER_ERROR and
     * the given message. If no variables are specified, the format string isn't
     * formatted but rather used as message directly.
     *
     * @param format
     * @param variables
     * @return
     */
    public static HttpException internalServerError(String format, Object... variables) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR, format, variables);
    }

    /**
     * Creates a new HttpException with status code INTERNAL_SERVER_ERROR and
     * the given {@link Throwable}. If no variables are specified, the format
     * string isn't formatted but rather used as message directly.
     *
     * @param cause
     * @param format
     * @param variables
     * @return
     */
    public static HttpException internalServerError(Throwable cause, String format, Object... variables) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR, cause, format, variables);
    }

    /**
     * Creates a new HttpException with status code INTERNAL_SERVER_ERROR and
     * the given {@link EfsError}.
     *
     * @param error
     * @return
     */
    public static HttpException internalServerError(EfsError error) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }

    /**
     * Creates a new HttpException with status code BAD_REQUEST and the given
     * message. If no variables are specified, the format string isn't formatted
     * but rather used as message directly.
     *
     * @param format
     * @param variables
     * @return
     */
    public static HttpException badRequest(String format, Object... variables) {
        return status(HttpStatus.BAD_REQUEST, format, variables);
    }

    /**
     * Creates a new HttpException with status code BAD_REQUEST and the given
     * message and {@link Throwable}. If no variables are specified, the format
     * string isn't formatted but rather used as message directly.
     *
     * @param cause
     * @param format
     * @param variables
     * @return
     */
    public static HttpException badRequest(Throwable cause, String format, Object... variables) {
        return status(HttpStatus.BAD_REQUEST, cause, format, variables);
    }

    /**
     * Creates a new HttpException with status code FORBIDDEN and the given
     * message. If no variables are specified, the format string isn't formatted
     * but rather used as message directly.
     *
     * @param format
     * @param variables
     * @return
     */
    public static HttpException forbidden(String format, Object... variables) {
        return status(HttpStatus.FORBIDDEN, format, variables);
    }

    /**
     * Creates a new HttpException with status code FORBIDDEN and the given
     * message and {@link Throwable}. If no variables are specified, the format
     * string isn't formatted but rather used as message directly.
     *
     * @param cause
     * @param format
     * @param variables
     * @return
     */
    public static HttpException forbidden(Throwable cause, String format, Object... variables) {
        return status(HttpStatus.FORBIDDEN, cause, format, variables);
    }

    /**
     * Creates a new HttpException with status code NOT_IMPLEMENTED and the
     * given message. If no variables are specified, the format string isn't
     * formatted but rather used as message directly.
     *
     * @param format
     * @param variables
     * @return
     */
    public static HttpException notImplemented(String format, Object... variables) {
        return status(HttpStatus.NOT_IMPLEMENTED, format, variables);
    }

    /**
     * Creates a new HttpException with status code NOT_IMPLEMENTED and the
     * given message and {@link Throwable}. If no variables are specified, the
     * format string isn't formatted but rather used as message directly.
     *
     * @param cause
     * @param format
     * @param variables
     * @return
     */
    public static HttpException notImplemented(Throwable cause, String format, Object... variables) {
        return status(HttpStatus.NOT_IMPLEMENTED, cause, format, variables);
    }

}
