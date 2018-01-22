package io.github.mordijc.rest.containers;

import io.github.mordijc.rest.containers.common.Address;
import io.github.mordijc.rest.containers.common.Location;

import java.util.Date;

/**
 * Nearest sensor APi response container.
 */
public class NearestSensor {
    /**
     * Air quality index.
     */
    public final Double airQualityIndex;

    /**
     * PM 2.5 dust level.
     */
    public final Double pm25; // 2.5

    /**
     * PM 10 dust level.
     */
    public final Double pm10;

    /**
     * Sensor ID.
     */
    public final Integer id;

    /**
     * Sensor name.
     */
    public final String name;

    /**
     * Sensor vendor.
     */
    public final String vendor;

    /**
     * Sensor location.
     */
    public final Location location;

    /**
     * Sensor pollution level.
     */
    public final Integer pollutionLevel;

    /**
     * Sensor measurement time.
     */
    public final Date measurementTime;

    /**
     * Sensor address.
     */
    public final Address address;

    /**
     * Constructs object from given data.
     */
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
