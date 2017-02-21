package com.avenueinfotech.location.services;

import android.app.IntentService;
import android.content.Intent;

import com.avenueinfotech.location.MainActivity;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;


public class DetectedActivitiesIntentService extends IntentService {

    protected static final String TAG = "DetectedActivitiesIS";
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_CONFIDENCE = "myConfidence";
    public static String responseString = "";

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public DetectedActivitiesIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for (DetectedActivity activity : probableActivities) {
            if(activity.getConfidence()>75) {
                switch (activity.getType()) {
                    case DetectedActivity.IN_VEHICLE: {
                        responseString = "IN_VEHICLE";
                        break;
                    }

                    case DetectedActivity.ON_BICYCLE: {
                        responseString = "ON_BICYCLE";
                        break;
                    }
                    case DetectedActivity.ON_FOOT: {
                        responseString = "ON_FOOT";
                        break;
                    }
                    case DetectedActivity.RUNNING: {
                        responseString = "RUNNING";
                        break;
                    }
                    case DetectedActivity.STILL: {
                        responseString = "STILL";
                        break;
                    }
                    case DetectedActivity.TILTING: {
                        responseString = "TILTING";
                        break;
                    }
                    case DetectedActivity.WALKING: {
                        responseString = "WALKING";
                        break;
                    }
                    case DetectedActivity.UNKNOWN: {
                        responseString = "UNKNOWN";
                        break;
                    }
                }
            }

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.MyActivityRequestReceiver.PROCESS_RESPONSE);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(RESPONSE_STRING, responseString);
            broadcastIntent.putExtra(RESPONSE_CONFIDENCE, String.valueOf(activity.getConfidence()) + "%");
            sendBroadcast(broadcastIntent);



        }
    }

}
