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

/**
 * A collection of modes used in the EFS API. It is inspired by the MaaS-API.
 *
 * @author boesch
 */
public enum Mode {
    WALK,
    //

    /**
     * <img width="360" src="https://upload.wikimedia.org/wikipedia/commons/9/97/Electric_scooters%2C_Warszawska_Street_in_Tomasz%C3%B3w_Mazowiecki%2C_%C5%81%C3%B3d%C5%BA_Voivodeship%2C_Poland.jpg">
     * <br><small><b>Image-Source:</b>
     * <i>https://en.wikipedia.org/wiki/Motorized_scooter#/media/File:Electric_scooters,_Warszawska_Street_in_Tomasz%C3%B3w_Mazowiecki,_%C5%81%C3%B3d%C5%BA_Voivodeship,_Poland.jpg</i>
     * </small>
     * <h2>English:</h2>
     * Kick-Scooter, e-Scooter -
     * <a href="https://en.wikipedia.org/wiki/Motorized_scooter#Electric">Wikipedia</a>
     * <h2>German:</h2>
     * E-Scooter -
     * <a href="https://de.wikipedia.org/wiki/E-Scooter">Wikipedia</a>
     */
    KICK_SCOOTER,
    //

    /**
     * Includes all kinds of bicycles (even with more than three wheels), also
     * pedelecs.
     */
    BICYCLE,
    //

    /**
     * Simply means a car as is. As long as there is no extra Mode for
     * motorcycles or motorized non-kick-scooters, these kinds of modes should
     * also be defined using {@link CAR}.
     */
    CAR,
    //

    /**
     * Taxi includes not only the classical Taxi, but also other ride hailing
     * services like Uber or Lyft.
     */
    TAXI,
    //

    /**
     * Classical bus that is usually self powered and runs on typical roads.
     */
    BUS,
    //

    /**
     * <img width="360" src="https://upload.wikimedia.org/wikipedia/commons/5/56/R160_E_enters_42nd_Street.jpg">
     * <br><small>
     * A train made up of R160's on the E line heads to Jamaica Center in
     * Queens.
     * <br><b>Image-Source:</b>
     * <i>https://en.wikipedia.org/wiki/Rapid_transit#/media/File:R160_E_enters_42nd_Street.jpg</i>
     * <br><b>Uploaded by:</b> <i>MTAEnthusiast10</i>
     * <br><b>License:</b>
     * <i><a href="https://creativecommons.org/licenses/by-sa/4.0">CC BY-SA
     * 4.0</a></i>
     * <br><b>Changes:</b> <i>none</i>
     * </small>
     * <h2>English:</h2>
     * Rapid Transit, Metro, Subway, U-Bahn, MRT -
     * <a href="https://en.wikipedia.org/wiki/Rapid_transit">Wikipedia</a>
     * <h2>German:</h2>
     * U-Bahn, Metro -
     * <a href="https://de.m.wikipedia.org/wiki/U-Bahn">Wikipedia</a>
     */
    METRO,
    //

    /**
     * <img width="360" src="https://upload.wikimedia.org/wikipedia/commons/0/0e/NF2-Frohnhausen.jpg">
     * <br><small>
     * Eine Straßenbahn des Typs NF2 in Frohnhausen, Breilsort
     * <br><b>Image-Source:</b>
     * <i>https://de.wikipedia.org/wiki/Stra%C3%9Fenbahn#/media/Datei:NF2-Frohnhausen.jpg</i>
     * <br><b>Uploaded by:</b> <i>U-Bahnfreund</i>
     * <br><b>License:</b>
     * <i><a href="https://creativecommons.org/licenses/by-sa/4.0">CC BY-SA
     * 4.0</a></i>
     * <br><b>Changes:</b> <i>none</i>
     * </small>
     * <h2>English:</h2>
     * Tram, Streetcar, Trolley, Tramway -
     * <a href="https://en.wikipedia.org/wiki/Tram">Wikipedia</a>
     * <h2>German:</h2>
     * Tram, Straßenbahn -
     * <a href="https://de.wikipedia.org/wiki/Stra%C3%9Fenbahn">Wikipedia</a>
     */
    TRAM,
    //

    /**
     * <h2>English:</h2>
     * S-Train, regional trains, inter-city trains, ...
     * <br>Means all kinds of trains that run on the common rail system, usually
     * used to travel medium to larger distances.
     * <h2> German:</h2>
     * S-Bahn, Regionalbahnen, Interregio-Bahnen, InterCity-Bahnen, ...
     * <br>
     * Alles was auf dem gängigen Schienensystem fährt. Wird in der Regel für
     * mittlere bis größere Distanzen genutzt.
     */
    RAIL,
    //

    /**
     * <img width="360" src="https://upload.wikimedia.org/wikipedia/commons/1/1d/ACableCarWithNoOneOnItOnMasonAndBroadwayBrokenDownWithAnNABIInTheBack.jpg">
     * <br><small><b>Image-Source:</b>
     * <i>https://de.wikipedia.org/wiki/Kabelstra%C3%9Fenbahn#/media/Datei:ACableCarWithNoOneOnItOnMasonAndBroadwayBrokenDownWithAnNABIInTheBack.jpg</i>
     * </small>
     * <h2>English:</h2>
     * Cable-Car (US), Cable-Tram (outside US) -
     * <a href="https://en.wikipedia.org/wiki/Cable_car_(railway)">Wikipedia</a>
     * <h2>German:</h2>
     * Kabelstraßenbahn -
     * <a href="https://de.wikipedia.org/wiki/Kabelstra%C3%9Fenbahn">Wikipedia</a>
     */
    CABLE_TRAM,
    //

    /**
     * <img width="360" src="https://upload.wikimedia.org/wikipedia/commons/0/06/Cablecar.zellamsee.500pix.jpg">
     * <br><small><b>Image-Source:</b>
     * <i>https://de.wikipedia.org/wiki/Datei:Cablecar.zellamsee.500pix.jpg</i>
     * </small>
     * <h2>English:</h2>
     * Aerial-Lift (US), Cable-Car (UK) -
     * <a href="https://en.wikipedia.org/wiki/Aerial_lift">Wikipedia</a>
     * <h2>German:</h2>
     * Luftseilbahn -
     * <a href="https://de.wikipedia.org/wiki/Luftseilbahn">Wikipedia</a>
     */
    AERIAL_LIFT,
    //

    /**
     * <img width="360" src="https://upload.wikimedia.org/wikipedia/commons/8/82/HarderBahn.jpg">
     * <br><small><b>Image-Source:</b>
     * <i>https://de.wikipedia.org/wiki/Standseilbahn#/media/Datei:HarderBahn.jpg</i>
     * </small>
     * <h2>English:</h2>
     * Funicular -
     * <a href="https://en.wikipedia.org/wiki/Funicular">Wikipedia</a>
     * <h2>German:</h2>
     * Standseilbahn -
     * <a href="https://de.wikipedia.org/wiki/Standseilbahn">Wikipedia</a>
     */
    FUNICULAR,
    //

    /**
     * A transportation ship for people, cars and other vehicles.
     */
    FERRY,
    //

    /**
     * <img width="360" src="https://upload.wikimedia.org/wikipedia/commons/c/c8/Gondola_Ride.jpg">
     * <br><small>
     * Gondola Ride A photo by Shaun Bowden kindly released by his widow
     * <br><b>Image-Source:</b>
     * <i>https://en.wikipedia.org/wiki/Gondola#/media/File:Gondola_Ride.jpg</i>
     * <br><b>Uploaded by:</b> <i>Njsmith3</i>
     * <br><b>License:</b>
     * <i><a href="https://creativecommons.org/licenses/by-sa/4.0">CC BY-SA
     * 4.0</a></i>
     * <br><b>Changes:</b> <i>none</i>
     * </small>
     * <h2>English:</h2>
     * Gondola (boat) -
     * <a href="https://en.wikipedia.org/wiki/Gondola">Wikipedia</a>
     * <h2>German:</h2>
     * Gondel (Bootstyp) -
     * <a href="https://de.wikipedia.org/wiki/Gondel_%28Bootstyp%29">Wikipedia</a>
     */
    GONDOLA,
    //

    /**
     * Means travelling by flight using a normal air plane.
     */
    FLIGHT,
    //

    /**
     * Special mode describing a kind of mobility that doesn't fit into any
     * other mode.
     */
    OTHER,
    //

    /**
     * Special mode describing a that a leg is travelled using multiple modes.
     * The particular modes should be extracted from the sub-legs.
     */
    MULTIPLE,
    //

    /**
     * Special mode that denotes a leg that virtually means the way between two
     * other legs. This leg usually is travelled by walking. If vehicles are
     * used for this mode, that mode can be described in the asset belonging to
     * this leg.
     */
    LEG_SWITCH,
    //
}
