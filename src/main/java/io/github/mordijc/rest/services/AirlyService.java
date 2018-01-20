package io.github.mordijc.rest.services;

import io.github.mordijc.rest.containers.NearestSensor;
import io.github.mordijc.rest.containers.SensorMeasurements;
import io.github.mordijc.rest.containers.SensorInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AirlyService {

    @GET("v1/nearestSensor/measurements")
    Call<NearestSensor> getNearestSensorData(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("v1/sensor/measurements")
    Call<SensorMeasurements> getSensorMeasurements(@Query("sensorId") int sensorId);

    @GET("v1/sensors/{sensorId}")
    Call<SensorInfo> getSensorInfo(@Path("sensorId") int sensorId);
}
