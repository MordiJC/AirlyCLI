package io.github.mordijc.rest.containers;

import io.github.mordijc.Util;
import io.github.mordijc.rest.containers.common.Measurement;
import io.github.mordijc.rest.containers.common.MeasurementsRecord;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Airly sensor measurement data structure.
 */
public class SensorMeasurements {

    /**
     * Latest sensor measurement.
     */
    public final Measurement currentMeasurements;

    /**
     * Historic measurement.
     */
    public final List<MeasurementsRecord> history;

    /**
     * Measurements forecast.
     */
    public final List<MeasurementsRecord> forecast;

    /**
     * Constructs object from given data.
     */
    public SensorMeasurements(Measurement currentMeasurements, List<MeasurementsRecord> history, List<MeasurementsRecord> forecast) {
        this.currentMeasurements = currentMeasurements;
        this.history = history;
        this.forecast = forecast;
    }
}
