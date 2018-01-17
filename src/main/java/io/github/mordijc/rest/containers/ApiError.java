package io.github.mordijc.rest.containers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ApiError {
    public final String message;

    public ApiError(String jsonMessage) {
        JsonElement jsonElement = new JsonParser().parse(jsonMessage);

        this.message = jsonElement
                .getAsJsonObject()
                .get("message")
                .getAsString();
    }
}
