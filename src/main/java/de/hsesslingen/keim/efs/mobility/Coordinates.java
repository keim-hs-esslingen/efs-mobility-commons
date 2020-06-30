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
package de.hsesslingen.keim.efs.mobility;

import java.io.Serializable;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A class representing a 2 dimensional pair of geo coordinates in decimal
 * latitude and longitude format.
 * <p>
 * It also has some static methods to calculate the distance in kilometers
 * between two coordinates and to convert between degrees and radians.
 *
 * @author boesch
 * @author K.Sivarasah
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates implements ICoordinates, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * minimum: -90
     * <p>
     * maximum: 90
     * <p>
     * example: 52.376883 The field value must be a valid WGS84 latitude in
     * decimal degrees format.
     */
    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @JsonProperty(required = true)
    private Double lat;

    /**
     * minimum: -180
     * <p>
     * maximum: 180
     * <p>
     * example: 4.90017 The field value must be a valid WGS84 longitude in
     * decimal degrees format.
     */
    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    @JsonProperty(required = true)
    private Double lon;

    /**
     * Updates this instance with the latitude and longitude values of the given
     * ICoordinates. If null is passed, nothing happens.
     *
     * @param coordinates
     */
    public void setCoordinates(ICoordinates coordinates) {
        if (coordinates == null) {
            return;
        }

        this.lat = coordinates.getLat();
        this.lon = coordinates.getLon();
    }

    /**
     * Updates this instance with the latitude and longitude values of the given
     * ICoordinates.If null is passed, nothing happens.This is essentiallythe
     * same as calling {@link setCoordinates(ICoordinates)}.
     *
     * @param other
     */
    public void updateSelfFrom(Object other) {
        if (other instanceof ICoordinates) {
            this.setCoordinates((ICoordinates) other);
        }
    }

}
