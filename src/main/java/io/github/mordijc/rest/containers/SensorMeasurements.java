package io.github.mordijc.rest.containers;

import io.github.mordijc.rest.containers.common.Measurement;
import io.github.mordijc.rest.containers.common.MeasurementsRecord;

import java.util.List;

public class SensorMeasurements {

    public final Measurement currentMeasurements;

    public final List<MeasurementsRecord> history;

    public final List<MeasurementsRecord> forecast;

    public SensorMeasurements(Measurement currentMeasurements, List<MeasurementsRecord> history, List<MeasurementsRecord> forecast) {
        this.currentMeasurements = currentMeasurements;
        this.history = history;
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "SensorMeasurements{" +
                "currentMeasurements=" + currentMeasurements +
                ", history=" + history +
                ", forecast=" + forecast +
                '}';
    }

}
