package io.github.mordijc.rest.containers;

import io.github.mordijc.rest.containers.common.Address;
import io.github.mordijc.rest.containers.common.Location;

/**
 * Basic sensor information
 */
public class SensorInfo {
    /**
     * Sensor address.
     */
    public final Address address;

    /**
     * Sensor id number.
     */
    public final Integer id;

    /**
     * Sensor location (latitude and longitude).
     */
    public final Location location;

    /**
     * Sensor name.
     */
    public final String name;

    /**
     * Sensor pollution level.
     */
    public final Integer pollutionLevel;

    /**
     * Sensor vendor.
     */
    public final String vendor;

    /**
     * Constructs object from given data.
     */
    public SensorInfo(Address address, int id, Location location,
                      String name, int pollutionLevel, String vendor) {
        this.address = address;
        this.id = id;
        this.location = location;
        this.name = name;
        this.pollutionLevel = pollutionLevel;
        this.vendor = vendor;
    }
}
