package io.github.mordijc.rest.containers;

import io.github.mordijc.rest.containers.common.Address;
import io.github.mordijc.rest.containers.common.Location;

import java.util.Date;

public class NearestSensor {
    public final Double airQualityIndex;

    public final Double pm25; // 2.5

    public final Double pm10;

    public final Integer id;

    public final String name;

    public final String vendor;

    public final Location location;

    public final Integer pollutionLevel;

    public final Date measurementTime;

    public final Address address;

    @Override
    public String toString() {
        return "NearestSensor{" +
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

    public NearestSensor(double airQualityIndex, Double pm25, Double pm10,
                         Integer id, String name, String vendor, Location location,
                         Integer pollutionLevel, Date measurementTime, Address address) {
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

}
