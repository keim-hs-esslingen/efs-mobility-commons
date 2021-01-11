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
 * Serves as an exception class that corresponds to an HTTP error response.
 * Therefore this class always contains a non-null HttpStatus value.
 *
 * @author keim
 */
public class HttpException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * The HTTP status associated with this exception.
     */
    protected final HttpStatus httpStatus;

    /**
     * @param format The message either as simple string or as a
     * String.format(...) format string. If no variables are given, the format
     * string is used as message as is.
     * @param variables
     * @see HttpClientException
     * @param httpStatus
     */
    public HttpException(HttpStatus httpStatus, String format, Object... variables) {
        super(variables.length > 0 ? String.format(format, variables) : format);
        this.httpStatus = httpStatus;
    }

    /**
     * @param format The message either as simple string or as a
     * String.format(...) format string. If no variables are given, the format
     * string is used as message as is.
     * @param variables
     * @see HttpClientException
     * @param httpStatus
     * @param cause
     */
    public HttpException(HttpStatus httpStatus, Throwable cause, String format, Object... variables) {
        super(variables.length > 0 ? String.format(format, variables) : format, cause);
        this.httpStatus = httpStatus;
    }

    public boolean isClientError() {
        return httpStatus.is4xxClientError();
    }

    public boolean isServerError() {
        return httpStatus.is5xxServerError();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
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
        return new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, format, variables);
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
        return new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, cause, format, variables);
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
        return new HttpException(HttpStatus.BAD_REQUEST, format, variables);
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
        return new HttpException(HttpStatus.BAD_REQUEST, cause, format, variables);
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
        return new HttpException(HttpStatus.FORBIDDEN, format, variables);
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
        return new HttpException(HttpStatus.FORBIDDEN, cause, format, variables);
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
        return new HttpException(HttpStatus.NOT_IMPLEMENTED, format, variables);
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
        return new HttpException(HttpStatus.NOT_IMPLEMENTED, cause, format, variables);
    }

    /**
     * Creates a new HttpException with status code UNAUTHORIZED and the given
     * message. If no variables are specified, the format string isn't formatted
     * but rather used as message directly.
     *
     * @param format
     * @param variables
     * @return
     */
    public static HttpException unauthorized(String format, Object... variables) {
        return new HttpException(HttpStatus.UNAUTHORIZED, format, variables);
    }

    /**
     * Creates a new HttpException with status code UNAUTHORIZED and the given
     * message and {@link Throwable}. If no variables are specified, the format
     * string isn't formatted but rather used as message directly.
     *
     * @param cause
     * @param format
     * @param variables
     * @return
     */
    public static HttpException unauthorized(Throwable cause, String format, Object... variables) {
        return new HttpException(HttpStatus.UNAUTHORIZED, cause, format, variables);
    }

    /**
     * Creates a new HttpException with status code NOT_FOUND and the given
     * message. If no variables are specified, the format string isn't formatted
     * but rather used as message directly.
     *
     * @param format
     * @param variables
     * @return
     */
    public static HttpException notFound(String format, Object... variables) {
        return new HttpException(HttpStatus.NOT_FOUND, format, variables);
    }

    /**
     * Creates a new HttpException with status code NOT_FOUND and the given
     * message and {@link Throwable}. If no variables are specified, the format
     * string isn't formatted but rather used as message directly.
     *
     * @param cause
     * @param format
     * @param variables
     * @return
     */
    public static HttpException notFound(Throwable cause, String format, Object... variables) {
        return new HttpException(HttpStatus.NOT_FOUND, cause, format, variables);
    }
}
