package io.github.mordijc.rest.containers.common;

/**
 * Address representation.
 */
public class Address {
    public final String streetNumber;

    public final String route;

    public final String locality;

    public final String country;

    public Address(String streetNumber, String route, String locality, String country) {
        this.streetNumber = streetNumber;
        this.route = route;
        this.locality = locality;
        this.country = country;
    }
}
