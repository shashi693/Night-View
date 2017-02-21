package com.avenueinfotech.location.utils;

import android.location.Location;

public class LocationsUtils {
    private static final int earthRadius = 6371;

    public static float calculateDistance(Location start, Location end) {
        float e = (float) start.getLatitude();
        float g = (float) end.getLatitude();
        float h = (float) end.getLongitude();
        float dLat = (float) Math.toRadians((double) (g - e));
        float dLon = (float) Math.toRadians((double) (h - ((float) start.getLongitude())));
        float a = (float) ((Math.sin((double) (dLat / 2.0f)) * Math.sin((double) (dLat / 2.0f))) + (((Math.cos(Math.toRadians((double) e)) * Math.cos(Math.toRadians((double) g))) * Math.sin((double) (dLon / 2.0f))) * Math.sin((double) (dLon / 2.0f))));
        return 1000.0f * (6371.0f * ((float) (2.0d * Math.atan2(Math.sqrt((double) a), Math.sqrt((double) (1.0f - a))))));
    }

    public static long timeSpan(Location start, Location end) {
        return end.getTime() - start.getTime();
    }

    public static float getSpeed(Location start, Location end) {
        return calculateDistance(start, end) / (((float) (end.getTime() - start.getTime())) / 1000.0f);
    }
}
