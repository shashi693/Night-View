package com.avenueinfotech.location.utils;

public class TemperatureUtils {
    public static int KelvinToCesius(float kelvin) {
        return (int) Math.ceil(((double) kelvin) - 273.15d);
    }

    public static int KelvinToFahrenheit(float kelvin) {
        return (int) Math.ceil((double) ((1.8f * kelvin) - 459.67f));
    }
}
