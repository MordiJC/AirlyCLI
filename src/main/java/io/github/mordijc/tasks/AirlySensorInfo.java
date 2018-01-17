package io.github.mordijc.tasks;

import com.google.gson.GsonBuilder;
import io.github.mordijc.Application;
import io.github.mordijc.command.Command;
import io.github.mordijc.format.SensorFormatter;
import io.github.mordijc.rest.containers.ApiError;
import io.github.mordijc.rest.containers.NearestSensor;
import io.github.mordijc.rest.containers.SensorDetails;
import io.github.mordijc.rest.containers.SensorInfo;
import io.github.mordijc.rest.services.AirlyService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AirlySensorInfo implements Application.ApplicationExecutionBlock {
    @Override
    public void run(Application app) throws Application.ApplicationExecutionBlockException {

        String apikey = getApiKey(app);

        Retrofit retrofit = getRetrofit(apikey);

        AirlyService service = retrofit.create(AirlyService.class);

        Command command = app.getApplicationCommand();

        if (command.latitude != null && command.longitude != null) {
            try {

                Response<NearestSensor> nearestSensorResponse = service.getNearestSensorData(
                        command.latitude, command.longitude
                ).execute();

                if (handleErrors(app, nearestSensorResponse)) {
                    return;
                }

                NearestSensor nearestSensor = nearestSensorResponse.body();


                SensorInfo sensorInfo = service.getSensorInfo(nearestSensor.id)
                        .execute().body();

                SensorDetails sensorDetails = service.getSensorMeasurements(nearestSensor.id)
                        .execute().body();

                System.out.println(
                        SensorFormatter.formatSensor(sensorInfo, sensorDetails)
                );

            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                app.exit();
            }
        } else if (command.sensorId != null) {
            try {
                System.out.println(service.getSensorMeasurements(command.sensorId).execute().body());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                app.exit();
            }
        } else {
            throw new Application.ApplicationExecutionBlockException(
                    "Illegal command state. Make sure that you are validating it before running this task."
            );
        }

    }

    private <T> boolean handleErrors(Application app, Response<T> nearestSensorResponse) throws IOException {
        switch (nearestSensorResponse.code()) {
            case 400:
            case 500:
            case 401:
            case 403:
            case 404:
                if (nearestSensorResponse.errorBody() != null) {
                    ApiError apiError = new ApiError(nearestSensorResponse.errorBody().string());

                    System.err.println(
                            "APIError: " + apiError.message
                    );
                } else {
                    System.err.println("APIError: Code = " + nearestSensorResponse.code());
                }

                app.exit();
                return true;
        }
        return false;
    }

    private Retrofit getRetrofit(String apikey) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(
                chain -> {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest
                            .newBuilder()
                            .addHeader("apiKey", apikey);

                    Request newRequest = builder.build();

                    return chain.proceed(newRequest);
                }

        ).build();

        return new Retrofit.Builder()
                .baseUrl("https://airapi.airly.eu/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                .create()
                ))
                .client(okHttpClient)
                .build();
    }

    private String getApiKey(Application app) {
        String apikey = System.getenv("API_KEY");

        if (apikey == null) {
            Command command = app.getApplicationCommand();

            apikey = command.apiKey;

            if (apikey == null || apikey.isEmpty()) {
                apikey = askForApiKeyAndThrowIfNotGiven();
            }
        }
        return apikey;
    }

    private String askForApiKeyAndThrowIfNotGiven() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter Airly API Token: ");

        try {
            return br.readLine();
        } catch (IOException e) {
            throw new Application.ApplicationExecutionBlockException(
                    "Airly API Token not provided."
            );
        }
    }
}
