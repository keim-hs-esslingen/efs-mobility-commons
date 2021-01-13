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

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * This class serves as a container for error data. This class is mainly used to
 * serialize error data in a standardized fashion for transportation or storage.
 * The {@link MiddlewareException} is it's throwable counterpart, that is used
 * for transporting error data through the JVM exception handling system.
 *
 * @author k.sivarasah 11 Sep 2019
 * @author b.oesch
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"message", "code"})
public class MiddlewareError implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TOKEN_INVALID_ERROR_CODE = "TOKEN_INVALID";
    public static final String TOKEN_INVALID_ERROR_MESSAGE = "The token you have provided is invalid.";
    public static final String REMOTE_AUTHENTICATION_FAILED_ERROR_CODE = "REMOTE_AUTHENTICATION_FAILED";
    public static final String REMOTE_AUTHENTICATION_FAILED_ERROR_MESSAGE = "The authentication at a remote service failed.";
    public static final String UNKNOWN_ERROR_CODE = "UNKNOWN_ERROR";
    public static final String REMOTE_SERVICE_UNAVAILABLE_ERROR_CODE = "REMOTE_SERVICE_UNAVAILABLE";
    public static final String REMOTE_SERVICE_UNAVAILABLE_ERROR_MESSAGE = "A required remote service is unavailable.";

    /**
     * A human readable error message.
     */
    @ApiModelProperty(value = "A human readable error message", required = true, position = 0)
    private String message;

    /**
     * A TSP internal error code, used for reference. Could just be an
     * http-status.
     */
    @ApiModelProperty(value = "An error code, used for reference. Can be an HTTP-status.", required = true, position = 1)
    private String code;

    /**
     * Map containing futher error details.
     */
    @ApiModelProperty(value = "Map containing futher error details", required = false, position = 2)
    private Map<String, Object> details;

    public MiddlewareError(String message) {
        this.message = message;
    }

    public MiddlewareError(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public MiddlewareError(String code, Map<String, Object> details, String message) {
        this.message = message;
        this.code = code;
        this.details = details;
    }

    public MiddlewareError(int code, String message) {
        this.message = message;
        this.code = Integer.toString(code);
    }

    public MiddlewareError(int code, Map<String, Object> details, String message) {
        this.message = message;
        this.code = Integer.toString(code);
        this.details = details;
    }

    public MiddlewareException toException() {
        return new MiddlewareException(code, details, message);
    }

    private static String format(String format, Object... variables) {
        return variables.length <= 0 ? format : String.format(format, variables);
    }

    public static MiddlewareError unknown(String format, Object... variables) {
        return unknown(null, format, variables);
    }

    public static MiddlewareError unknown(Map<String, Object> details, String format, Object... variables) {
        return new MiddlewareError(UNKNOWN_ERROR_CODE, details, format(format, variables));
    }

    public static MiddlewareError tokenInvalid() {
        return tokenInvalid(TOKEN_INVALID_ERROR_MESSAGE);
    }

    public static MiddlewareError tokenInvalid(String format, Object... variables) {
        return tokenInvalid(null, format, variables);
    }

    public static MiddlewareError tokenInvalid(Map<String, Object> details, String format, Object... variables) {
        return new MiddlewareError(TOKEN_INVALID_ERROR_CODE, details, format(format, variables));
    }

    public static MiddlewareError remoteAuthenticationFailed() {
        return remoteAuthenticationFailed(REMOTE_AUTHENTICATION_FAILED_ERROR_MESSAGE);
    }

    public static MiddlewareError remoteAuthenticationFailed(String format, Object... variables) {
        return remoteAuthenticationFailed(null, format, variables);
    }

    public static MiddlewareError remoteAuthenticationFailed(Map<String, Object> details, String format, Object... variables) {
        return new MiddlewareError(REMOTE_AUTHENTICATION_FAILED_ERROR_CODE, details, format(format, variables));
    }

    public static MiddlewareError remoteServiceUnavailable() {
        return remoteServiceUnavailable(REMOTE_SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    }

    public static MiddlewareError remoteServiceUnavailable(String format, Object... variables) {
        return remoteServiceUnavailable(null, format, variables);
    }

    public static MiddlewareError remoteServiceUnavailable(Map<String, Object> details, String format, Object... variables) {
        return new MiddlewareError(REMOTE_SERVICE_UNAVAILABLE_ERROR_CODE, details, format(format, variables));
    }
}
