package io.github.mordijc.rest.containers;

import io.github.mordijc.rest.containers.common.Address;
import io.github.mordijc.rest.containers.common.Location;

public class SensorInfo {
    public final Address address;

    public final int id;

    public final Location location;

    public final String name;

    public final int pollutionLevel;

    public final String vendor;

    public SensorInfo(Address address, int id, Location location,
                      String name, int pollutionLevel, String vendor) {
        this.address = address;
        this.id = id;
        this.location = location;
        this.name = name;
        this.pollutionLevel = pollutionLevel;
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "SensorInfo{" +
                "address=" + address +
                ", id=" + id +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", pollutionLevel=" + pollutionLevel +
                ", vendor='" + vendor + '\'' +
                '}';
    }
}
