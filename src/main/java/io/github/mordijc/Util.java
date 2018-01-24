package io.github.mordijc;

public class Util {
    public static <T> T valueOrDefault(T value, T def) {
        if(value == null) {
            return def;
        }
        return value;
    }
}
