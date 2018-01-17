package io.github.mordijc.rest.containers.common;

import java.util.Date;

public class Measurement {
    final double airQualityIndex;

    final double humidity;

    final Date measurementTime;

    final double pm1;

    final double pm25; // 2.5

    final double pm10;

    final int pollutionLevel;

    final double pressure;

    final double temperature;

    public Measurement(double airQualityIndex, double humidity, Date measurementTime, double pm1, double pm25, double pm10, int pollutionLevel, double pressure, double temperature) {
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

    @Override
    public String toString() {
        return "Measurement{" +
                "airQualityIndex=" + airQualityIndex +
                ", humidity=" + humidity +
                ", measurementTime=" + measurementTime +
                ", pm1=" + pm1 +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", pollutionLevel=" + pollutionLevel +
                ", pressure=" + pressure +
                ", temperature=" + temperature +
                '}';
    }
}
