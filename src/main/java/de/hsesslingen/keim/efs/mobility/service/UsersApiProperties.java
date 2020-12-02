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

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Contains information about the Users-API of mobility service providers.
 *
 * @author ben
 */
@Data
public class UsersApiProperties {

    /**
     * Whether this service supports creation of new users using the Users-API.
     * This value is only applicable if the Users-API is supported by this
     * service.
     */
    @NotNull
    @ApiModelProperty(value = "Whether this service supports creation of new users using the Users-API.")
    private boolean supportsUserRegistration;

    /**
     * A list of those property names, that are required for regitrating new
     * users and therefore must be not-null in the {@link Customer} object of
     * that endpoint.
     */
    private List<String> requiredCustomerPropertiesForRegistration;

    /**
     * Whether users can be registered under a super-user-account, when using
     * the registerUser endpoint.
     */
    @ApiModelProperty(value = "Whether users can be registered under a super-user-account, when using the registerUser endpoint.")
    private boolean superUserSupportedForRegistration;

    /**
     * Whether user are required to be registered under a super-user-account,
     * when using the registerUser endpoint. In this case, root-super-users
     * cannot be created using this API but must instead be requested directly
     * at the service provider.
     */
    @ApiModelProperty(value = "Whether user are required to be registered under a super-user-account, when using the registerUser endpoint. In this case, root-super-users cannot be created using this API but must instead be requested directly at the service provider.")
    private boolean superUserRequiredForRegistration;

}
