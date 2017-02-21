package com.avenueinfotech.location.utils;

public class SpeedUtils {
    public static int toKmph(float mps) {
        return Math.round((3600.0f * mps) / 1000.0f);
    }

    public static int toMph(float mps) {
        return Math.round((3600.0f * mps) * 6.21371E-4f);
    }

    public static float toKM(float m) {
        return m / 1000.0f;
    }

    public static float toMiles(float meters) {
        return 6.21371E-4f * meters;
    }
}
