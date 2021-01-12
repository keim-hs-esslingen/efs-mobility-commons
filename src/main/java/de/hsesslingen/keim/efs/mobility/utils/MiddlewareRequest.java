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
package de.hsesslingen.keim.efs.mobility.utils;

import de.hsesslingen.keim.restutils.AbstractRequest;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The default class for building and sending requests in the EFS-project. Does
 * nothing else but setting all the visibility levels of {@link AbstractRequest}
 * to public, adding some extra methods for EFS-specific fetaures and providing
 * some static methods for building requests easily.
 * <p>
 * The {@link MiddlewareRequest} class is a wrapper of Springs
 * {@link RestTemplate} clas for building requests using a builder pattern wich
 * chainable methods. See {@link AbstractRequest} for more details on this.
 *
 * @author boesch
 * @param <T>
 * @see AbstractRequest
 */
public class MiddlewareRequest<T> extends AbstractRequest<T> {

    public static final String USER_ID_HEADER = "x-user-id";
    public static final String SECRET_HEADER = "x-secret";
    public static final String TOKEN_HEADER = "x-token";

    private RestTemplate template;
    private List<Consumer<MiddlewareRequest>> outgoingRequestAdapters;

    private String token;
    private String userId;
    private String secret;

    /**
     * Tells whether this request is send in interest of an internal
     * source/motivation and was not triggered upon input from outside.
     * <p>
     * Frameworks that use the middleware-core library can use this flag
     * together with outgoing request adapters to add credentials to outgoing
     * requests. Either their own, or those delivered by Authentication from the
     * original caller.
     * <p>
     * Set to true using {@link toInternal()}.
     */
    private boolean isInternal = false;

    public MiddlewareRequest(HttpMethod method, String uri, RestTemplate template) {
        super(method, uri);
        this.template = template;
    }

    public MiddlewareRequest(HttpMethod method, URI uri, RestTemplate template) {
        super(method, uri);
        this.template = template;
    }

    public MiddlewareRequest(HttpMethod method, RestTemplate template) {
        super(method);
        this.template = template;
    }

    public MiddlewareRequest(String uri, RestTemplate template) {
        super(uri);
        this.template = template;
    }

    public MiddlewareRequest(URI uri, RestTemplate template) {
        super(uri);
        this.template = template;
    }

    //<editor-fold defaultstate="collapsed" desc="Static factory methods for easy creation.">
    public static <X> MiddlewareRequest<X> get(String uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.GET, uri, template);
    }

    public static <X> MiddlewareRequest<X> get(URI uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.GET, uri, template);
    }

    public static <X> MiddlewareRequest<X> post(String uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.POST, uri, template);
    }

    public static <X> MiddlewareRequest<X> post(URI uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.POST, uri, template);
    }

    public static <X> MiddlewareRequest<X> put(String uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.PUT, uri, template);
    }

    public static <X> MiddlewareRequest<X> put(URI uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.PUT, uri, template);
    }

    public static <X> MiddlewareRequest<X> delete(String uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.DELETE, uri, template);
    }

    public static <X> MiddlewareRequest<X> delete(URI uri, RestTemplate template) {
        return new MiddlewareRequest<>(HttpMethod.DELETE, uri, template);
    }

    public static <X> MiddlewareRequest<X> custom(HttpMethod method, String uri, RestTemplate template) {
        return new MiddlewareRequest<>(method, uri, template);
    }

    public static <X> MiddlewareRequest<X> custom(HttpMethod method, URI uri, RestTemplate template) {
        return new MiddlewareRequest<>(method, uri, template);
    }
    //</editor-fold>

    public MiddlewareRequest<T> template(RestTemplate template) {
        this.template = template;
        return this;
    }

    public MiddlewareRequest<T> requestAdapters(List<Consumer<MiddlewareRequest>> adapters) {
        this.outgoingRequestAdapters = adapters;
        return this;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return template;
    }

    /**
     * This method will set the token that shall be used in this request.
     *
     * @param token
     * @return
     */
    public MiddlewareRequest<T> token(String token) {
        this.token = token;
        return this;
    }

    /**
     * This method will set the userId and secret that shall be used in this
     * request.
     *
     * @param userId
     * @param secret
     * @return
     */
    public MiddlewareRequest<T> userIdAndSecret(String userId, String secret) {
        this.userId = userId;
        this.secret = secret;
        return this;
    }

    /**
     * This method will set the user-ID that shall be used in this request.
     *
     * @param userId
     * @return
     */
    public MiddlewareRequest<T> userId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * This method will set the secret that shall be used in this request.
     *
     * @param secret
     * @return
     */
    public MiddlewareRequest<T> secret(String secret) {
        this.secret = secret;
        return this;
    }

    private boolean outgoingRequestAdaptersCalled = false;

    /**
     * This function calls the registered request adapters with this request.
     * Calling this function multiple times has no effect. The adapters are
     * called only once.
     * <p>
     * If this function is not called manually if will be called automatically
     * before the request is sent.
     * <p>
     * Request adapters can be registered using
     * {@link EfsRequest.addOutgoingRequestAdapter}.
     *
     * @return
     */
    public MiddlewareRequest<T> callOutgoingRequestAdapters() {
        // Check if the outgoing adapters were already called once. If so, don't do it again.
        if (outgoingRequestAdaptersCalled) {
            return this;
        }

        outgoingRequestAdaptersCalled = true;

        if (outgoingRequestAdapters != null) {
            for (var adapter : outgoingRequestAdapters) {
                adapter.accept(this);
            }
        }

        return this;
    }

    private void addCredentialsToHeader() {
        if (token != null) {
            super.header(TOKEN_HEADER, token);
        }

        if (userId != null) {
            super.header(USER_ID_HEADER, userId);
        }

        if (secret != null) {
            super.header(SECRET_HEADER, secret);
        }
    }

    @Override
    public <R> MiddlewareRequest<R> expect(ParameterizedTypeReference<R> responseTypeReference) {
        return (MiddlewareRequest<R>) super.expect(responseTypeReference);
    }

    @Override
    public <R> MiddlewareRequest<R> expect(Class<R> responseTypeClass) {
        return (MiddlewareRequest<R>) super.expect(responseTypeClass);
    }

    @Override
    public MiddlewareRequest<T> uriVariables(Map<String, ?> uriVariables) {
        return (MiddlewareRequest<T>) super.uriVariables(uriVariables);
    }

    @Override
    public MiddlewareRequest<T> uriVariables(Object... uriVariables) {
        return (MiddlewareRequest<T>) super.uriVariables(uriVariables);
    }

    @Override
    public MiddlewareRequest<T> query(MultiValueMap<String, String> params) {
        return (MiddlewareRequest<T>) super.query(params);
    }

    @Override
    public MiddlewareRequest<T> query(String key, Object... values) {
        return (MiddlewareRequest<T>) super.query(key, values);
    }

    @Override
    public MiddlewareRequest<T> query(String key, Object value) {
        return (MiddlewareRequest<T>) super.query(key, value);
    }

    @Override
    public MiddlewareRequest<T> query(String query) {
        return (MiddlewareRequest<T>) super.query(query);
    }

    @Override
    public MiddlewareRequest<T> contentType(String contentType) {
        return (MiddlewareRequest<T>) super.contentType(contentType);
    }

    @Override
    public MiddlewareRequest<T> header(String key, String value) {
        return (MiddlewareRequest<T>) super.header(key, value);
    }

    @Override
    public MiddlewareRequest<T> headers(String key, List<? extends String> values) {
        return (MiddlewareRequest<T>) super.headers(key, values);
    }

    @Override
    public MiddlewareRequest<T> headers(Map<String, String> headersToAdd) {
        return (MiddlewareRequest<T>) super.headers(headersToAdd);
    }

    @Override
    public MiddlewareRequest<T> body(Object body) {
        return (MiddlewareRequest<T>) super.body(body);
    }

    @Override
    public MiddlewareRequest<T> entity(HttpEntity entity) {
        return (MiddlewareRequest<T>) super.entity(entity);
    }

    @Override
    public MiddlewareRequest<T> uri(URI uri) {
        return (MiddlewareRequest<T>) super.uri(uri);
    }

    @Override
    public MiddlewareRequest<T> uri(String uri) {
        return (MiddlewareRequest<T>) super.uri(uri);
    }

    @Override
    public ResponseEntity<T> go() {
        // Before we send the request, lets add our credentials...
        addCredentialsToHeader();
        callOutgoingRequestAdapters();
        return super.go();
    }

    public MiddlewareRequest<T> toInternal() {
        this.isInternal = true;
        return this;
    }

    public boolean isInternal() {
        return this.isInternal;
    }

    @Override
    public ParameterizedTypeReference<T> responseTypeReference() {
        return super.responseTypeReference();
    }

    @Override
    public Class<T> responseTypeClass() {
        return super.responseTypeClass();
    }

    @Override
    public Map<String, ?> uriVariablesMap() {
        return super.uriVariablesMap();
    }

    @Override
    public Object[] uriVariables() {
        return super.uriVariables();
    }

    @Override
    public HttpHeaders headers() {
        return super.headers();
    }

    @Override
    public String contentType() {
        return super.contentType();
    }

    @Override
    public Object body() {
        return super.body();
    }

    @Override
    public HttpEntity entity() {
        return super.entity();
    }

    @Override
    public HttpMethod method() {
        return super.method();
    }

    @Override
    public UriComponentsBuilder uriBuilder() {
        return super.uriBuilder();
    }

}
