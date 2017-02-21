package com.avenueinfotech.location.services;

import android.graphics.Typeface;

public class AppConfigs {
    private static AppConfigs _instance;
    public boolean IS_METRIC_SYSTEM_UNIT = true;
    public String OPEN_WHEATHER_MAP_API_KEY;
    public Typeface ROBOTO_CONDENSED_REGULAR;
    public Typeface ROBOTO_THIN;

    private AppConfigs() {
    }

    public static AppConfigs getInstance() {
        if (_instance == null) {
            _instance = new AppConfigs();
        }
        return _instance;
    }
}
