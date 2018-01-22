package io.github.mordijc.tasks;

import com.google.gson.GsonBuilder;
import io.github.mordijc.Application;
import io.github.mordijc.command.Command;
import io.github.mordijc.format.SensorFormatter;
import io.github.mordijc.rest.containers.ApiError;
import io.github.mordijc.rest.containers.NearestSensor;
import io.github.mordijc.rest.containers.SensorMeasurements;
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
import java.net.UnknownHostException;

public class AirlySensorInfoTask implements Application.ApplicationExecutionBlock {
    @Override
    public void run(Application app) throws Application.ApplicationExecutionBlockException {

        String apiKey = getApiKey(app);

        Retrofit retrofit = getRetrofit(apiKey);

        AirlyService service = retrofit.create(AirlyService.class);

        Command command = app.getApplicationCommand();

        if (command.latitude != null && command.longitude != null) {
            showNearestSensorInfo(app, service, command);

        } else if (command.sensorId != null) {
            showSpecificSensorInfo(app, service, command);

        } else {
            throw new Application.ApplicationExecutionBlockException(
                    "Illegal command state. Make sure that you are validating it before running this task."
            );
        }
    }

    private void showSpecificSensorInfo(Application app, AirlyService service, Command command) {
        try {
            Response<SensorMeasurements> sensorMeasurementsResponse =
                    service.getSensorMeasurements(command.sensorId).execute();

            if (handleConnectionErrorsAndTellIfOccured(app, sensorMeasurementsResponse)) {
                return;
            }

            SensorInfo sensorInfo = service.getSensorInfo(command.sensorId)
                    .execute().body();

            System.out.println(
                    new SensorFormatter()
                    .format(sensorInfo, sensorMeasurementsResponse.body())
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            app.exit();
        }
    }

    private void showNearestSensorInfo(Application app, AirlyService service, Command command) {
        try {
            Response<NearestSensor> nearestSensorResponse = service.getNearestSensorData(
                    command.latitude, command.longitude
            ).execute();

            if (handleConnectionErrorsAndTellIfOccured(app, nearestSensorResponse)) {
                return;
            }

            NearestSensor nearestSensor = nearestSensorResponse.body();

            Response<SensorInfo> sensorInfoResponse = service.getSensorInfo(nearestSensor.id)
                    .execute();

            if (handleConnectionErrorsAndTellIfOccured(app, sensorInfoResponse)) {
                return;
            }

            SensorInfo sensorInfo = sensorInfoResponse.body();

            Response<SensorMeasurements> sensorMeasurementsResponse = service.getSensorMeasurements(nearestSensor.id)
                    .execute();

            if (handleConnectionErrorsAndTellIfOccured(app, sensorMeasurementsResponse)) {
                return;
            }

            SensorMeasurements sensorMeasurements = sensorMeasurementsResponse.body();

            System.out.println(
                    new SensorFormatter().format(sensorInfo, sensorMeasurements)
            );

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            app.exit();
        }
    }

    private <T> boolean handleConnectionErrorsAndTellIfOccured(Application app, Response<T> nearestSensorResponse) throws IOException {
        if (nearestSensorResponse.code() >= 400) {
                if (nearestSensorResponse.errorBody() != null) {
                    ApiError apiError = new ApiError(nearestSensorResponse.errorBody().string());

                    System.err.println(
                            "API error: " + apiError.message
                    );
                } else {
                    System.err.println("Unknown API error. Code: " + nearestSensorResponse.code());
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

                    try {
                        return chain.proceed(newRequest);
                    } catch (UnknownHostException e) {
                        throw new Application.ApplicationExecutionBlockException(
                                "Unknown host or no connection: " + e.getMessage());
                    } catch (IOException e) {
                        throw new Application.ApplicationExecutionBlockException(
                                "Unknown error: " + e.getMessage()
                        );
                    }
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
        String apiKey = System.getenv("API_KEY");

        if (apiKey == null) {
            Command command = app.getApplicationCommand();

            apiKey = command.apiKey;

            if (apiKey == null || apiKey.isEmpty()) {
                apiKey = askForApiKeyAndThrowIfNotGiven();
            }
        }
        return apiKey;
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
