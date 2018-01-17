package io.github.mordijc.rest.containers.common;

import java.util.Date;

public class MeasurementsRecord {
    final Measurement measurements;
    final Date fromDateTime;
    final Date tillDateTime;

    public MeasurementsRecord(Measurement measurements, Date fromDateTime, Date tillDateTime) {
        this.measurements = measurements;
        this.fromDateTime = fromDateTime;
        this.tillDateTime = tillDateTime;
    }

    @Override
    public String toString() {
        return "MeasurementsRecord{" +
                "measurements=" + measurements +
                ", fromDateTime=" + fromDateTime +
                ", tillDateTime=" + tillDateTime +
                '}';
    }
}
