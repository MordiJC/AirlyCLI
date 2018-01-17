package io.github.mordijc.format;

import io.github.mordijc.rest.containers.SensorDetails;
import io.github.mordijc.rest.containers.SensorInfo;
import io.github.mordijc.rest.containers.common.Measurement;

public class SensorFormatter {
    public static String formatSensor(SensorInfo sensorInfo, SensorDetails sensorDetails) {
        return formatSensor(sensorInfo, sensorDetails.currentMeasurements);
    }

    public static String formatSensor(SensorInfo sensorInfo, Measurement measurement) {
        return "";
    }
}
