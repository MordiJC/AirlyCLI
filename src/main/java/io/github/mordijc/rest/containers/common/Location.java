package io.github.mordijc.rest.containers.common;

/**
 *  Representation of location on earth globe.
 */
public class Location {

    public final double latitude;

    public final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
