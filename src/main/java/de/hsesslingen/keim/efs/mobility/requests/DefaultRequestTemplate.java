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
package de.hsesslingen.keim.efs.mobility.requests;

import de.hsesslingen.keim.efs.mobility.exception.handler.MiddlewareErrorResponseHandler;
import org.springframework.web.client.RestTemplate;

/**
 * This request template sets an instance of
 * {@link MiddlewareErrorResponseHandler} as error handler and is used by all
 * internal beans that request stuff from services within the middleware.
 *
 * @author ben
 */
public final class DefaultRequestTemplate extends MiddlewareRequestTemplate {

    private final MiddlewareErrorResponseHandler errorHandler = new MiddlewareErrorResponseHandler();

    public DefaultRequestTemplate(RestTemplate value) {
        super.setRestTemplate(value);
        this.resetErrorHandler();
    }

    public void resetErrorHandler() {
        this.setErrorHandler(errorHandler);
    }

    @Override
    public DefaultRequestTemplate setRestTemplate(RestTemplate value) {
        super.setRestTemplate(value);
        this.resetErrorHandler();
        return this;
    }
}
