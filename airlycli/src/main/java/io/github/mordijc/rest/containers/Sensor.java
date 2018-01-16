package io.github.mordijc.rest.containers;

import java.util.Date;

public class Sensor {
    public final double airQualityIndex;

    public final double pm25; // 2.5

    public final double pm10;

    public final int id;

    public final String name;

    public final String vendor;

    public final Location location;

    public final int pollutionLevel;

    public final Date measurementTime;

    public final Address address;

    @Override
    public String toString() {
        return "Sensor{" +
                "airQualityIndex=" + airQualityIndex +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", vendor='" + vendor + '\'' +
                ", location=" + location +
                ", pollutionLevel=" + pollutionLevel +
                ", measurementTime=" + measurementTime +
                ", address=" + address +
                '}';
    }

    public Sensor(double airQualityIndex, double pm25, double pm10,
                  int id, String name, String vendor, Location location, int pollutionLevel,
                  Date measurementTime, Address address) {
        this.airQualityIndex = airQualityIndex;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.location = location;
        this.pollutionLevel = pollutionLevel;
        this.measurementTime = measurementTime;
        this.address = address;
    }

    public class Location {

        public final double latitude;

        public final double longitude;

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }

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
}
