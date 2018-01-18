package io.github.mordijc.format;

import io.github.mordijc.rest.containers.SensorDetails;
import io.github.mordijc.rest.containers.SensorInfo;
import io.github.mordijc.rest.containers.common.Measurement;

import java.util.Collections;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class SensorFormatter {
    public static String format(SensorInfo sensorInfo, SensorDetails sensorDetails) {
        return format(sensorInfo, sensorDetails.currentMeasurements);
    }

    public static String format(SensorInfo sensorInfo, Measurement measurement) {
        // Data
        String airQualityIndex = Integer.toString((int) Math.round(measurement.airQualityIndex));
        String pm25 = Integer.toString((int) Math.round(measurement.pm25));
        String pm10 = Integer.toString((int) Math.round(measurement.pm10));
        String pressure = Integer.toString((int) Math.round(measurement.pressure) / 10);
        String temperature = Integer.toString((int) Math.round(measurement.temperature));
        String address = sensorInfo.address.route + " " +
                sensorInfo.address.streetNumber;
        String city = sensorInfo.address.locality;

        int pm25_value = (int) Math.round(measurement.pm25);
        int pm10_value = (int) Math.round(measurement.pm10);

        String pm25_name = "PM 2.5";
        String pm10_name = "PM 10";
        String pressure_name = "Pressure";
        String temperature_name = "Temp.";

        String pm25_unit = "μg/m\u00B3";
        String pm10_unit = "μg/m\u00B3";
        String pressure_unit = "hPa";
        String temperature_unit = "\u00B0C";


        // Top row
        int airQualityCellContentWidth = airQualityIndex.length();
        int airQualityCellWidth = airQualityCellContentWidth + 2;

        int addressCellContentWidth = max(max(address.length(), city.length()), 10);
        int addressCellWidth = addressCellContentWidth + 2;

        // Row
        int longestMeasurementNameWidth = Stream.of(pm25_name, pm10_name, pressure_name, temperature_name)
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(5);
        int longestMeasurementValueWidth = Stream.of(pm25, pm10, pressure, temperature)
                .map(String::length).max(Integer::compareTo).orElse(3);
        int longestMeasurementUnitWidth = Stream.of(pm25_unit, pm10_unit, pressure_unit, temperature_unit)
                .map(String::length).max(Integer::compareTo).orElse(0);
        int longestMeasurementPercentageValue =
                Stream.of(Integer.toString(pm25_value*4), Integer.toString(pm10_value*2))
                .map(s->s.length()+1).max(Integer::compareTo).orElse(0);

        int measurementsRowMinimumContentWidth = longestMeasurementNameWidth + 1
                + longestMeasurementValueWidth + 1
                + longestMeasurementUnitWidth + (longestMeasurementUnitWidth > 0 ? 1 : 0)
                + longestMeasurementPercentageValue;
        int measurementsRowMinimumWidth = measurementsRowMinimumContentWidth + 2;

        if(measurementsRowMinimumWidth - airQualityCellWidth - 1
                > addressCellWidth) {

            addressCellWidth = measurementsRowMinimumWidth - airQualityCellWidth - 1;
            addressCellContentWidth = addressCellWidth - 2;

        } else if(measurementsRowMinimumWidth - airQualityCellWidth - 1
                < addressCellWidth) {
            measurementsRowMinimumContentWidth = addressCellWidth + airQualityCellWidth + 1;
            measurementsRowMinimumWidth = measurementsRowMinimumContentWidth;
        }


        int measurementsLineSpacesNumber = measurementsRowMinimumContentWidth - longestMeasurementNameWidth
                - longestMeasurementValueWidth - longestMeasurementUnitWidth - longestMeasurementPercentageValue - 1 - 2;

        String measurementsLineBasicFormat =
                "%-" + Integer.toString(longestMeasurementNameWidth)
                        + "s" + String.join("", Collections.nCopies(measurementsLineSpacesNumber / 2, " "))
                        + "%" + Integer.toString(longestMeasurementValueWidth)
                        + "s %-" + Integer.toString(longestMeasurementUnitWidth)
                        + "s" + String.join("", Collections.nCopies(measurementsLineSpacesNumber / 2 + measurementsLineSpacesNumber % 2, " "))
                        + "%" + Integer.toString(longestMeasurementPercentageValue) + "s";

        String pm25_line = String.format(measurementsLineBasicFormat, pm25_name, pm25, pm25_unit, Integer.toString(pm25_value*4) + "%");
        String pm10_line = String.format(measurementsLineBasicFormat, pm10_name, pm10, pm10_unit, Integer.toString(pm10_value*2) + "%");
        String pressure_line = String.format(measurementsLineBasicFormat, pressure_name, pressure, pressure_unit, "");
        String temperature_line = String.format(measurementsLineBasicFormat, temperature_name, temperature, temperature_unit, "");


        StringBuilder builder = new StringBuilder()
                .append('╔')
                .append(String.join("", Collections.nCopies(airQualityCellWidth, "═")))
                .append('╦')
                .append(String.join("", Collections.nCopies(addressCellWidth, "═")))
                .append("╗\n")

                .append("║ ")
                .append(airQualityIndex)
                .append(" ║ ")

                .append(String.join("", Collections.nCopies((addressCellContentWidth - address.length()) / 2, " ")))
                .append(address)
                .append(String.join("", Collections.nCopies(addressCellContentWidth - address.length()- (addressCellContentWidth - address.length())/2, " ")))

                .append(" ║\n")
                .append('╠')
                .append(String.join("", Collections.nCopies(airQualityCellWidth, "═")))
                .append("╝ ")

                .append(String.join("", Collections.nCopies((addressCellContentWidth - city.length()) / 2, " ")))
                .append(city)
                .append(String.join("", Collections.nCopies(addressCellContentWidth - city.length() - (addressCellContentWidth - city.length()) / 2, " ")))

                .append(" ║\n║ ")

                .append(pm25_line)

                .append(" ║\n║ ")

                .append(pm10_line)

                .append(" ║\n║ ")

                .append(pressure_line)

                .append(" ║\n║ ")

                .append(temperature_line)

                .append(" ║\n")

                .append('╚')
                .append(String.join("", Collections.nCopies(measurementsRowMinimumContentWidth, "═")))
                .append("╝\n");


        return builder.toString();
    }
}
