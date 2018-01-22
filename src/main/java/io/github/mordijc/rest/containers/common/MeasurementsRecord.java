package io.github.mordijc.rest.containers.common;

import java.util.Date;

/**
 * Sensor measurement record.
 */
public class MeasurementsRecord {

    /**
     * Sensor measurement data.
     */
    public final Measurement measurement;

    /**
     * Measurement start time.
     */
    public final Date fromDateTime;

    /**
     * Measurement end time.
     */
    public final Date tillDateTime;

    /**
     * Constructs object from given data.
     */
    public MeasurementsRecord(Measurement measurement, Date fromDateTime, Date tillDateTime) {
        this.measurement = measurement;
        this.fromDateTime = fromDateTime;
        this.tillDateTime = tillDateTime;
    }
}
