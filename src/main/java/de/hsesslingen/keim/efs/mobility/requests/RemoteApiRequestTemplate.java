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

import de.hsesslingen.keim.efs.mobility.exception.MiddlewareError;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.client.ResponseErrorHandler;

/**
 *
 * @author ben
 */
public abstract class RemoteApiRequestTemplate extends MiddlewareRequestTemplate {

    private static final Logger logger = getLogger(RemoteApiRequestTemplate.class);

    public RemoteApiRequestTemplate() {
        // Set anonymous ResponseErrorHandler.
        super.setErrorHandler(new ResponseErrorHandler() {

            @Override
            public final boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
            }

            @Override
            public final void handleError(ClientHttpResponse response) throws IOException {
                HttpStatus httpStatus = response.getStatusCode();

                String responseBody = null;

                try ( var scanner = new Scanner(response.getBody(), Charset.forName("UTF-8").name())) {
                    responseBody = scanner.useDelimiter("\\A").next();
                } catch (Exception ex) {
                    // Simply catch and do nothing. responseBody will be null.
                }

                // parseError is used inside error handler within constructor. This might lead to a leaking this-reference.
                var error = parseError(responseBody, httpStatus, response);

                if (error == null) {
                    Map<String, Object> details = null;

                    if (responseBody != null) {
                        details = Map.of("Remote Error Message", responseBody);
                    }

                    // Fallback.
                    error = MiddlewareError.unknown(details, "An unknown error occured.");
                }

                logger.error("Error Response: {}", responseBody);

                throw error.toException();
            }
        });
    }

    protected abstract MiddlewareError parseError(@Nullable String responseBody, HttpStatus statusCode, ClientHttpResponse response);

}
