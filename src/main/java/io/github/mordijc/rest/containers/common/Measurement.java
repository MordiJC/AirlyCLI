package io.github.mordijc.rest.containers.common;

import java.util.Date;

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
    public final double humidity;

    /**
     * Time of measurement.
     */
    public final Date measurementTime;

    /**
     * PM 1 dust level.
     */
    public final double pm1;

    /**
     * PM 2.5 dust level.
     */
    public final double pm25;

    /**
     * PM 10 dust level.
     */
    public final double pm10;

    /**
     * Air pollution level
     */
    public final int pollutionLevel;

    /**
     * Atmosphere pressure.
     */
    public final double pressure;

    /**
     * Air temperature.
     */
    public final double temperature;

    /**
     * Constructs object from given data.
     */
    public Measurement(double airQualityIndex, double humidity, Date measurementTime,
                       double pm1, double pm25, double pm10, int pollutionLevel,
                       double pressure, double temperature) {
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
}
