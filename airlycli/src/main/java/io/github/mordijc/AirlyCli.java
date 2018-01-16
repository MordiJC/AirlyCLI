package io.github.mordijc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.mordijc.rest.services.AirlyService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class AirlyCli {

    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(
                chain -> {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest
                            .newBuilder()
                            .addHeader("apikey", "4e9a14482ae1428185454617bde61f46");

                    Request newRequest = builder.build();

                    return chain.proceed(newRequest);
                }

        ).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://airapi.airly.eu/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()
                ))
                .client(okHttpClient)
                .build();


        AirlyService service = retrofit.create(AirlyService.class);

        System.out.println(service.getNearestSensorData(50.06, 20).execute().body());
    }
}
