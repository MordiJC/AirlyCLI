package io.github.mordijc.tasks;

import com.google.gson.GsonBuilder;
import io.github.mordijc.Application;
import io.github.mordijc.command.Command;
import io.github.mordijc.rest.services.AirlyService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
                System.out.println(service.getNearestSensorData(
                        command.latitude, command.longitude
                        ).execute().body()
                );
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
                        new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()
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
            System.err.println("Waiting for input.");
            return br.readLine();
        } catch (IOException e) {
            throw new Application.ApplicationExecutionBlockException(
                    "Airly API Token not provided."
            );
        }
    }
}
