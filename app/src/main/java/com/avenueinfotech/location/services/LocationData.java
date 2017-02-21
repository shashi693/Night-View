package com.avenueinfotech.location.services;

import android.location.Location;

import com.avenueinfotech.location.utils.LocationsUtils;
import com.avenueinfotech.location.utils.SpeedUtils;


public class LocationData {
    private static LocationData _instance;
    private float avgSpeed = 0;
    private Location currLocation = null;
    private float currSpeed = 0;
    private float distance = 0;
    private float maxSpeed = 0;
    private Location prevLocation = null;
    private long time = 0;

    private LocationData() {
    }

    public static LocationData getInstance() {
        if (_instance == null) {
            _instance = new LocationData();
        }
        return _instance;
    }

    private int convertSpeed(float sp) {
//        if (AppConfigs.getInstance().IS_METRIC_SYSTEM_UNIT) {
//            return SpeedUtils.toKmph(sp);
//        }
//        return SpeedUtils.toMph(sp);

        return SpeedUtils.toKmph(sp);
    }

    public long getTime() {
        return this.time;
    }

    public String getSpeedUnit() {
        return AppConfigs.getInstance().IS_METRIC_SYSTEM_UNIT ? "kmh" : "mph";
    }

    public String getDistanceUnit() {
        return AppConfigs.getInstance().IS_METRIC_SYSTEM_UNIT ? "km" : "mi";
    }

    public int getCurrentSpeed() {
        return convertSpeed(this.currSpeed);
    }

    public float getDistance() {
        if (AppConfigs.getInstance().IS_METRIC_SYSTEM_UNIT) {
            return SpeedUtils.toKM(this.distance);
        }
        return SpeedUtils.toMiles(this.distance);
    }

    public int getMaxSpeed() {
        return convertSpeed(this.maxSpeed);
    }

    public int getAvgSpeed() {
        return convertSpeed(this.avgSpeed);
    }

    public Location getCurrentLocation() {
        return this.currLocation;
    }

    public void setCurrLocation(Location location) {
        this.prevLocation = this.currLocation;
        this.currLocation = location;
        if (this.prevLocation != null) {
            this.currSpeed = LocationsUtils.getSpeed(this.prevLocation, this.currLocation);
            if (this.maxSpeed < this.currSpeed) {
                this.maxSpeed = this.currSpeed;
            }
            this.distance += this.prevLocation.distanceTo(this.currLocation);//LocationsUtils.calculateDistance(this.prevLocation, this.currLocation);
            this.time += LocationsUtils.timeSpan(this.prevLocation, this.currLocation);
            this.avgSpeed = this.distance / ((float) ((int) (((float) this.time) / 1000.0f)));
            this.avgSpeed = this.distance / (((float) this.time) / 1000.0f);
        }
    }

    public Location getPreviousLocation() {
        return this.prevLocation;
    }

    public void resetLocationData(){
        avgSpeed = 0;
        currLocation = null;
        currSpeed = 0;
        distance = 0;
        maxSpeed = 0;
        prevLocation = null;
        time = 0;

    }
}
