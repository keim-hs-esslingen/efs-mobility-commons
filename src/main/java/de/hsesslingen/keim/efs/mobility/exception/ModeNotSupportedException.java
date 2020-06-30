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
 * This exception is used to point out that a requested {@code Mode} is not
 * supported.
 *
 * @author boesch
 */
public class ModeNotSupportedException extends AbstractEfsException {

    /**
     * @see ModeNotSupportedException
     * @param message
     */
    public ModeNotSupportedException(String message) {
        super(message);
    }

    /**
     * @see ModeNotSupportedException
     * @param message
     * @param httpStatus
     */
    public ModeNotSupportedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    /**
     * @see ModeNotSupportedException
     * @param message
     * @param httpStatus
     * @param cause
     */
    public ModeNotSupportedException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }

    /**
     * @see ModeNotSupportedException
     * @param message
     * @param cause
     */
    public ModeNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see ModeNotSupportedException
     * @param message
     * @param error
     */
    public ModeNotSupportedException(String message, EfsError error) {
        super(message, error);
    }

    /**
     * @see ModeNotSupportedException
     * @param error
     * @param httpStatus
     */
    public ModeNotSupportedException(EfsError error, HttpStatus httpStatus) {
        super(error, httpStatus);
    }

    /**
     * @see ModeNotSupportedException
     * @param error
     */
    public ModeNotSupportedException(EfsError error) {
        super(error);
    }

}
