package io.github.mordijc.rest.containers;

import java.util.Date;
import java.util.List;

public class SensorDetails {

    public final Measurement currentMeasurements;

    public final List<MeasurementsRecord> history;

    public final List<MeasurementsRecord> forecast;

    public SensorDetails(Measurement currentMeasurements, List<MeasurementsRecord> history, List<MeasurementsRecord> forecast) {
        this.currentMeasurements = currentMeasurements;
        this.history = history;
        this.forecast = forecast;
    }

    public class Measurement {
        public final double airQualityIndex;

        public final double humidity;

        public final Date measurementTime;

        public final double pm1;

        public final double pm25; // 2.5

        public final double pm10;

        public final int pollutionLevel;

        public final double pressure;

        public final double temperature;

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
    }

    public class MeasurementsRecord {
        public final List<Measurement> measurements;

        public final Date fromDateTime;

        public final Date tillDateTime;

        public MeasurementsRecord(List<Measurement> measurements, Date fromDateTime, Date tillDateTime) {
            this.measurements = measurements;
            this.fromDateTime = fromDateTime;
            this.tillDateTime = tillDateTime;
        }
    }
}
