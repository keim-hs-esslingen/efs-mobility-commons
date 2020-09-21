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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsesslingen.keim.restutils.AbstractRequest;
import de.hsesslingen.keim.restutils.UrlUtils;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * nothing else but setting all the visibility levels of EfsRequest to public
 * and providing some static starter methods for building requests.
 *
 * @author boesch
 * @param <T>
 */
public class EfsRequest<T> extends AbstractRequest<T> {

    //<editor-fold defaultstate="collapsed" desc="Configuration code">
    private static final Logger log = LoggerFactory.getLogger(EfsRequest.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String LOCALHOST = "localhost";
    public static final String CREDENTIALS_HEADER_NAME = "x-credentials";
    public static final String CREDENTIALS_HEADER_DESC = "Credential data as json content string";

    // These values must be configured once statically using EfsRequest.configureRestTemplates(...).
    private static RestTemplate restTemplate;
    private static RestTemplate restTemplateLoadBalanced;
    private static final List<Consumer<EfsRequest>> outgoingRequestAdapters = new ArrayList<>();

    public static void configureRestTemplates(RestTemplate loadBalanced, RestTemplate normal) {
        EfsRequest.restTemplate = normal;
        EfsRequest.restTemplateLoadBalanced = loadBalanced;
    }

    /**
     * This adds an adapter to the list of outgoing adapters. These adapters are
     * called before a request is send off to be able to add or change
     * information.
     * <p>
     * An adapter can even throw an exception to intercept the sending of the
     * request.
     *
     * @param adapter
     */
    public static void addOutgoingRequestAdapter(Consumer<EfsRequest> adapter) {
        outgoingRequestAdapters.add(adapter);
    }
    //</editor-fold>

    private Object credentials;

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

    public EfsRequest(HttpMethod method, String uri) {
        super(method, uri);
    }

    public EfsRequest(HttpMethod method, URI uri) {
        super(method, uri);
    }

    public EfsRequest(HttpMethod method) {
        super(method);
    }

    public EfsRequest(String uri) {
        super(uri);
    }

    public EfsRequest(URI uri) {
        super(uri);
    }

    //<editor-fold defaultstate="collapsed" desc="Static factory methods for easy creation.">
    public static <X> EfsRequest<X> get(String uri) {
        return new EfsRequest<>(HttpMethod.GET, uri);
    }

    public static <X> EfsRequest<X> get(URI uri) {
        return new EfsRequest<>(HttpMethod.GET, uri);
    }

    public static <X> EfsRequest<X> post(String uri) {
        return new EfsRequest<>(HttpMethod.POST, uri);
    }

    public static <X> EfsRequest<X> post(URI uri) {
        return new EfsRequest<>(HttpMethod.POST, uri);
    }

    public static <X> EfsRequest<X> put(String uri) {
        return new EfsRequest<>(HttpMethod.PUT, uri);
    }

    public static <X> EfsRequest<X> put(URI uri) {
        return new EfsRequest<>(HttpMethod.PUT, uri);
    }

    public static <X> EfsRequest<X> delete(String uri) {
        return new EfsRequest<>(HttpMethod.DELETE, uri);
    }

    public static <X> EfsRequest<X> delete(URI uri) {
        return new EfsRequest<>(HttpMethod.DELETE, uri);
    }

    public static <X> EfsRequest<X> custom(HttpMethod method, String uri) {
        return new EfsRequest<>(method, uri);
    }

    public static <X> EfsRequest<X> custom(HttpMethod method, URI uri) {
        return new EfsRequest<>(method, uri);
    }
    //</editor-fold>

    @Override
    public RestTemplate getRestTemplate() {
        // This method is overridden from super class, because we need to provide a different rest template.
        String host = uriBuilder().build().getHost();

        if (LOCALHOST.equals(host) || UrlUtils.isValidDomainName(host) || UrlUtils.isValidIpAddress(host)) {
            return restTemplate;
        } else {
            return restTemplateLoadBalanced;
        }
    }

    /**
     * This method will set the credentials that shall be used in this request.
     *
     * @param credentials
     * @return
     */
    public EfsRequest<T> credentials(Object credentials) {
        this.credentials = credentials;
        return this;
    }

    private boolean outgoingRequestAdaptersCalled = false;
    public EfsRequest<T> callOutgoingRequestAdapters() {
        // Check if the outgoing adapters were already called once. If so, don't do it again.
        if(this.outgoingRequestAdaptersCalled){
            return this;
        }
        
        outgoingRequestAdaptersCalled = true;
        
        for (var adapter : outgoingRequestAdapters) {
            adapter.accept(this);
        }
        
        return this;
    }

    private void addCredentialsToHeader() {
        if (credentials == null) {
            return;
        }

        try {
            if (credentials instanceof Map) {
                super.headers((Map) credentials);
            } else if (credentials instanceof String) {
                super.header(CREDENTIALS_HEADER_NAME, (String) credentials);
            } else {
                super.header(CREDENTIALS_HEADER_NAME, mapper.writeValueAsString(credentials));
            }
        } catch (JsonProcessingException e) {
            log.error("Credential information could not be added to HttpHeader", e);
        }
    }

    @Override
    public <R> EfsRequest<R> expect(ParameterizedTypeReference<R> responseTypeReference) {
        return (EfsRequest<R>) super.expect(responseTypeReference); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <R> EfsRequest<R> expect(Class<R> responseTypeClass) {
        return (EfsRequest<R>) super.expect(responseTypeClass); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> uriVariables(Map<String, ?> uriVariables) {
        return (EfsRequest<T>) super.uriVariables(uriVariables); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> uriVariables(Object... uriVariables) {
        return (EfsRequest<T>) super.uriVariables(uriVariables); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> query(MultiValueMap<String, String> params) {
        return (EfsRequest<T>) super.query(params); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> query(String key, Object... values) {
        return (EfsRequest<T>) super.query(key, values); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> query(String key, Object value) {
        return (EfsRequest<T>) super.query(key, value); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> query(String query) {
        return (EfsRequest<T>) super.query(query); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> contentType(String contentType) {
        return (EfsRequest<T>) super.contentType(contentType); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> header(String key, String value) {
        return (EfsRequest<T>) super.header(key, value); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> headers(String key, List<? extends String> values) {
        return (EfsRequest<T>) super.headers(key, values); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> headers(Map<String, String> headersToAdd) {
        return (EfsRequest<T>) super.headers(headersToAdd); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> body(Object body) {
        return (EfsRequest<T>) super.body(body); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> entity(HttpEntity entity) {
        return (EfsRequest<T>) super.entity(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> uri(URI uri) {
        return (EfsRequest<T>) super.uri(uri); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EfsRequest<T> uri(String uri) {
        return (EfsRequest<T>) super.uri(uri); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity<T> go() {
        // Before we send the request, lets add our credentials...
        addCredentialsToHeader();
        callOutgoingRequestAdapters();
        return super.go();
    }

    public EfsRequest<T> toInternal() {
        this.isInternal = true;
        return this;
    }

    public boolean isInternal() {
        return this.isInternal;
    }

    @Override
    public ParameterizedTypeReference<T> responseTypeReference() {
        return super.responseTypeReference(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<T> responseTypeClass() {
        return super.responseTypeClass(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, ?> uriVariablesMap() {
        return super.uriVariablesMap(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] uriVariables() {
        return super.uriVariables(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HttpHeaders headers() {
        return super.headers(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String contentType() {
        return super.contentType(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object body() {
        return super.body(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HttpEntity entity() {
        return super.entity(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HttpMethod method() {
        return super.method(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UriComponentsBuilder uriBuilder() {
        return super.uriBuilder(); //To change body of generated methods, choose Tools | Templates.
    }

}
