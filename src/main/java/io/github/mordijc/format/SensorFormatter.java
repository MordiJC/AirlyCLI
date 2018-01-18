package io.github.mordijc.format;

import io.github.mordijc.rest.containers.SensorDetails;
import io.github.mordijc.rest.containers.SensorInfo;
import io.github.mordijc.rest.containers.common.Measurement;

import java.util.Collections;
import java.util.stream.Stream;

import static java.lang.Integer.max;

public class SensorFormatter {
    public static String format(SensorInfo sensorInfo, SensorDetails sensorDetails) {
        return format(sensorInfo, sensorDetails.currentMeasurements);
    }

    public static String format(SensorInfo sensorInfo, Measurement measurement) {
        String airQualityIndex = Integer.toString((int) Math.round(measurement.airQualityIndex));
        String pm25 = Integer.toString((int) Math.round(measurement.pm25));
        String pm10 = Integer.toString((int) Math.round(measurement.pm10));
        String pressure = Integer.toString((int) Math.round(measurement.pressure) / 10);
        String temperature = Integer.toString((int) Math.round(measurement.temperature));

        int pm25_int = (int) Math.round(measurement.pm25);
        int pm10_int = (int) Math.round(measurement.pm10);

        String pm25_name = "PM 2.5";
        String pm10_name = "PM 10";
        String pressure_name = "Pressure";
        String temperature_name = "Temp.";

        String pm25_unit = "μg/m\u00B3";
        String pm10_unit = "μg/m\u00B3";
        String pressure_unit = "hPa";
        String temperature_unit = "\u00B0C";

        String address = sensorInfo.address.route + " " +
                sensorInfo.address.streetNumber;
        String city = sensorInfo.address.locality;


        int airQualityColumnSize = airQualityIndex.length() + 2;
        int rightColumnSize = max(address.length(), city.length()) + 2;
        int fullContentWidth = airQualityColumnSize + rightColumnSize + 1;

        int measurementValueSize = Stream.of(pm25, pm10, pressure, temperature)
                .map(s -> s.length())
                .max(Integer::compareTo).orElse(3);

        int valueNameMaxSize = Stream.of(pm25_name, pm10_name, pressure_name, temperature_name)
                .map(s -> s.length())
                .max(Integer::compareTo).orElse(5);

        int measurementsValueSize = Stream.of(pm25, pm10, pressure, temperature)
                .map(s -> s.length()).max(Integer::compareTo).orElse(0);

        int measurementsUnitSize = Stream.of(pm25_unit, pm10_unit, pressure_unit, temperature_unit)
                .map(s -> s.length()).max(Integer::compareTo).orElse(0);

        int measurementsLineSizeMin = valueNameMaxSize + measurementsValueSize + measurementsUnitSize;
        int measurementsTextOnlyLineSizeMin = measurementsLineSizeMin + 7;

        String measurementsLineBasicFormat =
                "%-" + Integer.toString(valueNameMaxSize)
                        + "s %" + Integer.toString(measurementValueSize+1)
                        + "s %-" + Integer.toString(measurementsUnitSize)
                        + "s %5s";

        String pm25_line = String.format(measurementsLineBasicFormat, pm25_name, pm25, pm25_unit, Integer.toString(pm25_int*4));
        String pm10_line = String.format(measurementsLineBasicFormat, pm10_name, pm10, pm10_unit, Integer.toString(pm10_int*2));
        String pressure_line = String.format(measurementsLineBasicFormat, pressure_name, pressure, pressure_unit, "");
        String temperature_line = String.format(measurementsLineBasicFormat, temperature_name, temperature, temperature_unit, "");


        StringBuilder builder = new StringBuilder()
                .append('╔')
                .append(String.join("", Collections.nCopies(airQualityColumnSize, "═")))
                .append('╦')
                .append(String.join("", Collections.nCopies(rightColumnSize, "═")))
                .append("╗\n")

                .append("║ ")
                .append(airQualityIndex)
                .append(" ║ ")

                .append(String.join("", Collections.nCopies(rightColumnSize - address.length() - 2, " ")))
                .append(address)

                .append(" ║\n")
                .append('╠')
                .append(String.join("", Collections.nCopies(airQualityColumnSize, "═")))
                .append("╝ ")

                .append(String.join("", Collections.nCopies(rightColumnSize - city.length() - 2, " ")))
                .append(city)

                .append(" ║\n║ ")

                .append(pm25_line)

                .append(" ║\n║ ")

                .append(pm10_line)

                .append(" ║\n║ ")

                .append(pressure_line)

                .append(" ║\n║ ")

                .append(temperature_line);

        return builder.toString();
    }
}
