package io.github.mordijc.rest.services;

import io.github.mordijc.rest.containers.NearestSensor;
import io.github.mordijc.rest.containers.SensorMeasurements;
import io.github.mordijc.rest.containers.SensorInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Airly REST API service.
 */
public interface AirlyService {

    /**
     * Returns information about nearest sensor.
     *
     * @param latitude latitude of searching area point.
     * @param longitude longitude of searching area point.
     * @return {@code NearestSensor} instance.
     */
    @GET("v1/nearestSensor/measurement")
    Call<NearestSensor> getNearestSensorData(@Query("latitude") double latitude, @Query("longitude") double longitude);

    /**
     * Returns measurement of specific sensor.
     *
     * @param sensorId id of sensor.
     * @return measurement.
     */
    @GET("v1/sensor/measurement")
    Call<SensorMeasurements> getSensorMeasurements(@Query("sensorId") int sensorId);

    /**
     * Returns basic sensor information.
     *
     * @param sensorId id of sensor.
     * @return sensor info.
     */
    @GET("v1/sensors/{sensorId}")
    Call<SensorInfo> getSensorInfo(@Path("sensorId") int sensorId);
}
