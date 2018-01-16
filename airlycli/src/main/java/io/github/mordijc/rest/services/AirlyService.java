package io.github.mordijc.rest.services;

import io.github.mordijc.rest.containers.Sensor;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AirlyService {

    @GET("v1/nearestSensor/measurements")
    Call<Sensor> getNearestSensorData(@Query("latitude") double latitude, @Query("longitude") double longitude);


}
