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
 * Exception for http status 404. By default the ResourceNotFoundException has
 * the {@link HttpStatus} NOT_FOUND, that cannot be changed.
 *
 * @author k.sivarasah 12 Sep 2019
 * @author b.oesch
 */
public class ResourceNotFoundException extends AbstractEfsException {

    private static final long serialVersionUID = 1L;
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    /**
     * @see ResourceNotFoundException
     */
    public ResourceNotFoundException() {
        super("Resource not found", NOT_FOUND);
    }

    /**
     * Constructs a new ResourceNotFoundException with HttpStatus
     * {@code NOT_FOUND} and the provided error message.
     *
     * @param message The detailed error message
     */
    public ResourceNotFoundException(String message) {
        super(message, NOT_FOUND);
    }

    /**
     * Constructs a new ResourceNotFoundException with HttpStatus
     * {@code NOT_FOUND} and the provided cause and error message.
     *
     * @param message The detailed error message
     * @param cause Throwable
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, NOT_FOUND, cause);
    }

}
