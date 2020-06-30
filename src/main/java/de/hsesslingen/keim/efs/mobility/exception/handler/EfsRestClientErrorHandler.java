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
package de.hsesslingen.keim.efs.mobility.exception.handler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.hsesslingen.keim.efs.mobility.exception.EfsError;
import de.hsesslingen.keim.efs.mobility.exception.HttpClientException;
import de.hsesslingen.keim.efs.mobility.exception.HttpServerException;

/**
 * Implementation of {@link ResponseErrorHandler} that is responsible 
 * for handling response errors during a rest call. To make use of EfsRestClientErrorHandler
 * set it as error-handler for your {@link RestTemplate}
 * @author k.sivarasah
 * 1 Oct 2019
 */

public class EfsRestClientErrorHandler implements ResponseErrorHandler {

	private static ObjectMapper mapper;
	private static final Logger log = LoggerFactory.getLogger(EfsRestClientErrorHandler.class);
	
	static {
		mapper = new ObjectMapper()
					.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().series() == Series.CLIENT_ERROR ||
				response.getStatusCode().series() == Series.SERVER_ERROR ;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpStatus httpStatus = response.getStatusCode();

		String responseBody = "";
		try (Scanner scanner = new Scanner(response.getBody(), Charset.forName("UTF-8").name())) {
			responseBody = scanner.useDelimiter("\\A").next();
		}
		
		EfsError error;
		try {
			error = mapper.readValue(responseBody, EfsError.class);
		} catch (IOException e1) {
			error = new EfsError().setMessage(responseBody)
						.setCode(httpStatus.value());
		}
		
		log.error("Error Response: {}", responseBody);
		
		if(httpStatus.is4xxClientError()) {
			throw new HttpClientException(error, httpStatus);
		}
		
		if(httpStatus.is5xxServerError()) {
			throw new HttpServerException(error, httpStatus);
		}
	}


}
