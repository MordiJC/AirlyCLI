package io.github.mordijc.format;

import io.github.mordijc.rest.containers.SensorMeasurements;
import io.github.mordijc.rest.containers.SensorInfo;
import io.github.mordijc.rest.containers.common.Measurement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.max;

public class SensorFormatter {
    public String format(SensorInfo sensorInfo, SensorMeasurements sensorMeasurements) {
        return format(sensorInfo, sensorMeasurements.currentMeasurements);
    }

    public String format(SensorInfo sensorInfo, Measurement measurement) {
        // Data
        String airQualityIndex = Integer.toString((int) Math.round(measurement.airQualityIndex));
        String pm25 = Integer.toString((int) Math.round(measurement.pm25));
        String pm10 = Integer.toString((int) Math.round(measurement.pm10));
        String pressure = Integer.toString((int) Math.round(measurement.pressure/100));
        String temperature = Integer.toString((int) Math.round(measurement.temperature));
        String humidity = Integer.toString((int) Math.round(measurement.humidity));
        String address = sensorInfo.address.route + " " +
                sensorInfo.address.streetNumber;
        String city = sensorInfo.address.locality;

        int pm25_value = (int) Math.round(measurement.pm25);
        int pm10_value = (int) Math.round(measurement.pm10);

        Supplier<Stream<String>> measurement_values_supplier = () -> Stream.of(
                pm25, pm10, pressure, temperature, humidity
        );

        Supplier<Stream<String>> measurement_names_supplier = () -> Stream.of(
                "PM 2.5", "PM 10", "Pressure", "Temp.", "Humidity"
        );

        Supplier<Stream<String>> measurement_units_supplier = () -> Stream.of(
                "\u03BCg/m\u00B3", "\u03BCg/m\u00B3", "hPa", "\u00B0C", "%"
        );

        // Top row
        int airQualityCellContentWidth = airQualityIndex.length();
        int airQualityCellWidth = airQualityCellContentWidth + 2;

        int addressCellContentWidth = max(max(address.length(), city.length()), 10);
        int addressCellWidth = addressCellContentWidth + 2;

        // Measurements
        int longestMeasurementNameWidth = measurement_names_supplier.get()
                .map(String::length).max(Integer::compareTo).orElse(5);

        int longestMeasurementValueWidth = Stream.of(pm25, pm10, pressure, temperature)
                .map(String::length).max(Integer::compareTo).orElse(3);

        int longestMeasurementUnitWidth = measurement_units_supplier.get()
                .map(String::length).max(Integer::compareTo).orElse(0);

        int longestMeasurementPercentageValue =
                Stream.of(Integer.toString(pm25_value * 4), Integer.toString(pm10_value * 2))
                        .map(s -> s.length() + 1).max(Integer::compareTo).orElse(0);

        int measurementsRowMinimumContentWidth = longestMeasurementNameWidth + 1
                + longestMeasurementValueWidth + 1
                + longestMeasurementUnitWidth + (longestMeasurementUnitWidth > 0 ? 1 : 0)
                + longestMeasurementPercentageValue;
        int measurementsRowMinimumWidth = measurementsRowMinimumContentWidth + 2;

        if (measurementsRowMinimumWidth - airQualityCellWidth - 1
                > addressCellWidth) {

            addressCellWidth = measurementsRowMinimumWidth - airQualityCellWidth - 1;
            addressCellContentWidth = addressCellWidth - 2;

        } else if (measurementsRowMinimumWidth - airQualityCellWidth - 1
                < addressCellWidth) {
            measurementsRowMinimumContentWidth = addressCellWidth + airQualityCellWidth + 1;
            measurementsRowMinimumWidth = measurementsRowMinimumContentWidth;
        }


        int measurementsLineSpacesNumber = measurementsRowMinimumContentWidth - longestMeasurementNameWidth
                - longestMeasurementValueWidth - longestMeasurementUnitWidth - longestMeasurementPercentageValue - 1 - 2;

        String measurementsLineBasicFormat = getMeasurementsLineFormat(longestMeasurementNameWidth, longestMeasurementValueWidth, longestMeasurementUnitWidth, longestMeasurementPercentageValue, measurementsLineSpacesNumber);

        List<String> measurementLines = prepare(measurementsLineBasicFormat,
                measurement_names_supplier.get(),
                measurement_values_supplier.get(),
                measurement_units_supplier.get(),
                Stream.of(Integer.toString(pm25_value * 4) + "%", Integer.toString(pm10_value * 2) + "%")
        );

        return getTopTableLine(airQualityCellWidth, addressCellWidth) +
                "\u2551 " +
                airQualityIndex +
                " \u2551 " +
                createAddressCellContent(address, addressCellContentWidth) +
                " \u2551\n" +
                '\u2560' +
                String.join("", Collections.nCopies(airQualityCellWidth, "\u2550")) +
                "\u255D " +
                createAddressCellContent(city, addressCellContentWidth) +
                " \u2551\n\u2551 " +
                createMeasurementLines(measurementLines) +
                getBottomTableLine(airQualityCellWidth, addressCellWidth);
    }

    private String getMeasurementsLineFormat(int longestMeasurementNameWidth, int longestMeasurementValueWidth, int longestMeasurementUnitWidth, int longestMeasurementPercentageValue, int measurementsLineSpacesNumber) {
        return "%-" + Integer.toString(longestMeasurementNameWidth)
                + "s" + String.join("", Collections.nCopies(measurementsLineSpacesNumber / 2, " "))
                + "%" + Integer.toString(longestMeasurementValueWidth)
                + "s %-" + Integer.toString(longestMeasurementUnitWidth)
                + "s" + String.join("", Collections.nCopies(measurementsLineSpacesNumber / 2 + measurementsLineSpacesNumber % 2, " "))
                + "%" + Integer.toString(longestMeasurementPercentageValue) + "s";
    }

    private String createAddressCellContent(String content, int cellContentSize) {
        return String.join("", Collections.nCopies((cellContentSize - content.length()) / 2, " ")) +
                content +
                String.join("", Collections.nCopies(cellContentSize - content.length() - (cellContentSize - content.length()) / 2, " "));
    }

    private String getTopTableLine(int leftCellWidth, int rightCellWidth) {
        return "\u2554" +
                String.join("", Collections.nCopies(leftCellWidth, "\u2550")) +
                '\u2566' +
                String.join("", Collections.nCopies(rightCellWidth, "\u2550")) +
                "\u2557\n";
    }

    private String getBottomTableLine(int leftCellWidth, int rightCellWidth) {
        return "\u255A" +
                String.join("", Collections.nCopies(leftCellWidth + 1 + rightCellWidth, "\u2550")) +
                "\u255D\n";
    }

    private String createMeasurementLines(List<String> measurementLines) {
        StringBuilder builder = new StringBuilder();
        measurementLines.stream().limit(measurementLines.size() - 1).forEach(l -> {
            builder.append(l);
            builder.append(" \u2551\n\u2551 ");
        });

        measurementLines.stream().skip(measurementLines.size() - 1).forEach(l -> {
            builder.append(l);
            builder.append(" \u2551\n");
        });

        return builder.toString();
    }

    private List<String> prepare(String lineFormat, Stream<String> names, Stream<String> values,
                                 Stream<String> units, Stream<String> percentages) {
        List<String> output = new LinkedList<>();

        List<String> namesList = names.collect(Collectors.toList());
        List<String> valuesList = values.collect(Collectors.toList());
        List<String> unitsList = units.collect(Collectors.toList());
        List<String> percentagesList = percentages.collect(Collectors.toList());

        int maxLength = Stream.of(namesList, valuesList, unitsList, percentagesList)
                .map(List::size).max(Integer::compareTo).orElse(0);

        for (int i = 0; i < maxLength; ++i) {
            output.add(
                    String.format(lineFormat,
                            defaultIfNull(getFromListOrNull(namesList, i), ""),
                            defaultIfNull(getFromListOrNull(valuesList, i), ""),
                            defaultIfNull(getFromListOrNull(unitsList, i), ""),
                            defaultIfNull(getFromListOrNull(percentagesList, i), "")
                    )
            );
        }

        return output;
    }

    private <T> T getFromListOrNull(List<T> list, int index) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private <T> T defaultIfNull(T o, T def) {
        if (o == null) {
            return def;
        } else {
            return o;
        }
    }

}
