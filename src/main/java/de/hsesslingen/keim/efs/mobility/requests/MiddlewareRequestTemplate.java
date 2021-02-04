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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpMethod;
import static org.springframework.http.HttpMethod.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ben
 */
public class MiddlewareRequestTemplate {

    private RestTemplate restTemplate;
    private List<MiddlewareRequestAdapter> requestAdapters;

    /**
     * This adds the given adapter to the list of request adapters.These
     * adapters are called before a request is sent off to be able to add or
     * change information.<p>
     * An adapter can even throw an exception to intercept the sending of the
     * request.
     *
     * @param adapter
     * @return
     */
    public MiddlewareRequestTemplate addRequestAdapter(MiddlewareRequestAdapter adapter) {
        if (this.requestAdapters == null) {
            this.requestAdapters = new ArrayList<>();
        }

        requestAdapters.add(adapter);

        return this;
    }

    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        return restTemplate;
    }

    public MiddlewareRequestTemplate setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    public ResponseErrorHandler getErrorHandler() {
        return getRestTemplate().getErrorHandler();
    }

    public MiddlewareRequestTemplate setErrorHandler(ResponseErrorHandler errorHandler) {
        getRestTemplate().setErrorHandler(errorHandler);
        return this;
    }

    public MiddlewareRequest<?> get(String uri) {
        return new MiddlewareRequest<>(GET, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> get(URI uri) {
        return new MiddlewareRequest<>(GET, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> post(String uri) {
        return new MiddlewareRequest<>(POST, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> post(URI uri) {
        return new MiddlewareRequest<>(POST, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> put(String uri) {
        return new MiddlewareRequest<>(PUT, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> put(URI uri) {
        return new MiddlewareRequest<>(PUT, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> delete(String uri) {
        return new MiddlewareRequest<>(DELETE, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> delete(URI uri) {
        return new MiddlewareRequest<>(DELETE, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> custom(HttpMethod method, String uri) {
        return new MiddlewareRequest<>(method, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> custom(HttpMethod method, URI uri) {
        return new MiddlewareRequest<>(method, uri, getRestTemplate()).requestAdapters(requestAdapters);
    }

    public MiddlewareRequest<?> method(HttpMethod method) {
        return new MiddlewareRequest(getRestTemplate()).method(method);
    }

    public MiddlewareRequest<?> createRequest() {
        return new MiddlewareRequest(getRestTemplate());
    }
}
