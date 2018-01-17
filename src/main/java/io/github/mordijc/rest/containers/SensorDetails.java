package io.github.mordijc.rest.containers;

import io.github.mordijc.rest.containers.common.Measurement;
import io.github.mordijc.rest.containers.common.MeasurementsRecord;

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

    @Override
    public String toString() {
        return "SensorDetails{" +
                "currentMeasurements=" + currentMeasurements +
                ", history=" + history +
                ", forecast=" + forecast +
                '}';
    }

}
