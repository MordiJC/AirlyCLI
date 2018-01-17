package io.github.mordijc.rest.containers.common;

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

    @Override
    public String toString() {
        return "Address{" +
                "streetNumber='" + streetNumber + '\'' +
                ", route='" + route + '\'' +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
