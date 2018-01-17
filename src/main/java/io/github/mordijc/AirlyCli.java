package io.github.mordijc;

import com.google.gson.GsonBuilder;
import io.github.mordijc.cli.Command;
import io.github.mordijc.execution.InfoPrinter;
import io.github.mordijc.rest.services.AirlyService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Scanner;

public class AirlyCli {

    public static void main(String[] args) throws IOException {
        Application application = new Application(args);

        application.addChainBlock(new InfoPrinter())
                .addChainBlock();

        Command programCommand = CommandLine.populateCommand(new Command(), args);


        String apikey = System.getenv("API_KEY");

        if (apikey == null) {
            System.out.println("Enter Airly API Token: ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNext()) {
                apikey = scanner.next();
            } else {
                throw new RuntimeException("Airly API Token not provided.");
            }
        }

        String finalApikey = apikey;

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(
                chain -> {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest
                            .newBuilder()
                            .addHeader("apikey", finalApikey);

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
