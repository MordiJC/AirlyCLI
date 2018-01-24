package io.github.mordijc.rest.containers.common;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Sensor measurement container.
 */
public class Measurement {
    /**
     * Air quality index.
     */
    public final Double airQualityIndex;

    /**
     * Air humidity.
     */
    public final Double humidity;

    /**
     * Time of measurement.
     */
    public final Date measurementTime;

    /**
     * PM 1 dust level.
     */
    public final Double pm1;

    /**
     * PM 2.5 dust level.
     */
    public final Double pm25;

    /**
     * PM 10 dust level.
     */
    public final Double pm10;

    /**
     * Air pollution level
     */
    public final Integer pollutionLevel;

    /**
     * Atmosphere pressure.
     */
    public final Double pressure;

    /**
     * Air temperature.
     */
    public final Double temperature;

    /**
     * Constructs object from given data.
     */
    public Measurement(Double airQualityIndex, Double humidity, Date measurementTime,
                       Double pm1, Double pm25, Double pm10, Integer pollutionLevel,
                       Double pressure, Double temperature) {
        this.airQualityIndex = airQualityIndex;
        this.humidity = humidity;
        this.measurementTime = measurementTime;
        this.pm1 = pm1;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.pollutionLevel = pollutionLevel;
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public Measurement() {
        this.airQualityIndex = null;
        this.humidity = null;
        this.measurementTime = null;
        this.pm1 = null;
        this.pm25 = null;
        this.pm10 = null;
        this.pollutionLevel = null;
        this.pressure = null;
        this.temperature = null;
    }

    /**
     * Check if data is null.
     *
     * @return true if all values are null otherwise false.
     */
    public boolean isNull() {
        return Stream.<Object>of(airQualityIndex, humidity,
                measurementTime, pm1, pm25, pm10, pollutionLevel,
                pressure, temperature)
                .allMatch(Objects::isNull);
    }
}
