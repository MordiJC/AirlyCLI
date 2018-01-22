package io.github.mordijc.rest.containers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * API error message container for GSon purposes.
 */
public class ApiError {
    /**
     * API error message content.
     */
    public final String message;

    /**
     * Constructs object from given error message.
     *
     * @param errorMessage error message.
     */
    public ApiError(String errorMessage) {
        JsonElement jsonElement = new JsonParser().parse(errorMessage);

        this.message = jsonElement
                .getAsJsonObject()
                .get("message")
                .getAsString();
    }
}
