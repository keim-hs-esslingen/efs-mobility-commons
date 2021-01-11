/*
 * MIT License
 * 
 * Copyright (c) 2021 Hochschule Esslingen
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

import static de.hsesslingen.keim.efs.mobility.exception.MiddlewareError.*;
import java.util.Map;

/**
 * This class is the throwable counterpart of {@link MiddlewareError} and is
 * used for transporting exception data through JVM exception handling system.
 *
 * @author ben
 */
public class MiddlewareException extends RuntimeException {

    private final String code;
    private final Map<String, Object> details;

    public MiddlewareException(String code, String message) {
        super(message);
        this.code = code;
        this.details = null;
    }

    public MiddlewareException(String code, Throwable cause, String message) {
        super(message, cause);
        this.code = code;
        this.details = null;
    }

    public MiddlewareException(String code, Map<String, Object> details, String message) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public MiddlewareException(String code, Map<String, Object> details, Throwable cause, String message) {
        super(message, cause);
        this.code = code;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public MiddlewareError toError() {
        return new MiddlewareError(code, details, getMessage());
    }

    public static MiddlewareException tokenInvalidException() {
        return tokenInvalidException(REMOTE_SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    }

    public static MiddlewareException tokenInvalidException(String format, Object... variables) {
        return tokenInvalidException(null, format, variables);
    }

    public static MiddlewareException tokenInvalidException(Map<String, Object> details, String format, Object... variables) {
        var message = variables.length <= 0 ? format : String.format(format, variables);
        return new MiddlewareException(TOKEN_INVALID_ERROR_CODE, details, message);
    }

    public static MiddlewareException remoteServiceUnavailableException() {
        return remoteServiceUnavailableException(TOKEN_INVALID_ERROR_MESSAGE);
    }

    public static MiddlewareException remoteServiceUnavailableException(String format, Object... variables) {
        return remoteServiceUnavailableException(null, format, variables);
    }

    public static MiddlewareException remoteServiceUnavailableException(Map<String, Object> details, String format, Object... variables) {
        var message = variables.length <= 0 ? format : String.format(format, variables);
        return new MiddlewareException(REMOTE_SERVICE_UNAVAILABLE_ERROR_CODE, details, message);
    }

}
