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
package de.hsesslingen.keim.efs.mobility.service;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A class describing a mobility service provided by a mobility provider. The
 * list of available services is retrievable from the service directory.
 *
 * @author k.sivarasah 11 Sep 2019
 * @author b.oesch
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "Model describing the details of a MobilityService provided by an TSP")
public class MobilityService implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the service (given by the service-directory).
     */
    @NotEmpty
    @ApiModelProperty(value = "Unique identifier for the service (given by the service-directory)", required = true)
    private String id;

    /**
     * Unique name of the TSP.
     */
    @NotEmpty
    @ApiModelProperty(value = "Unique name of the TSP", required = true)
    private String providerName;

    /**
     * An URL pointing to an image resource that shows the providers logo.
     */
    private String serviceLogoUrl;

    /**
     * Unique name of the service provided by the TSP.
     */
    @NotEmpty
    @ApiModelProperty(value = "Unique name of the service provided by the TSP", required = true)
    private String serviceName;

    /**
     * Base URL of the servic.
     */
    @NotEmpty
    @ApiModelProperty(value = "Base URL of the service", required = true)
    private String serviceUrl;

    /**
     * A free form string describing the area in which this service is
     * available.
     */
    private String serviceArea;

    /**
     * Collection of supported modes.
     */
    @NotEmpty
    @ApiModelProperty(value = "Collection of supported modes", required = true)
    private Set<Mode> modes;

    @ApiModelProperty(value = "The apis provided by this mobility service.", required = true)
    private Set<API> apis;

    public boolean supportsApi(API api) {
        return apis.contains(api);
    }

    /**
     * Contains information about the Users-API, if that API is supported by
     * this service.
     */
    @ApiModelProperty(value = "Contains information about the Users-API, in case this API is supported by this service.")
    private UsersApiProperties usersApiProperties;

    public enum API {
        PLACES_API,
        ASSETS_API,
        OPTIONS_API,
        BOOKING_API,
        CREDENTIALS_API,
        USERS_API
    }

}
