package com.avenueinfotech.location.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.avenueinfotech.location.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Rashid on 08/10/2016.
 */

public class GeneralUtils {


    public static boolean isPlugged(Context context) {
        boolean isPlugged = false;
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        isPlugged = plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            isPlugged = isPlugged || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        }
        return isPlugged;
    }


    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMM_kkmmss");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    public static String getDisplayDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE dd MMM ");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }

    public static Bitmap getMarker(Context ctx, String mode) {


        Drawable output = ctx.getResources().getDrawable(R.drawable.marker_end);

        if (mode.equals("start")) {
            output = ctx.getResources().getDrawable(R.drawable.marker_start);
        }


        return Bitmap.createScaledBitmap(drawableToBitmap(output), 90, 90, false);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public static void writePath(String filename, ArrayList<Location> points) {

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><gpx xmlns=\"http://www.topografix.com/GPX/1/1\" creator=\"MapSource 6.15.5\" version=\"1.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\"><trk>\n";
        String name = "<name>" + filename + "</name><trkseg>\n";

        String segments = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        for (Location l : points) {
            segments += "<trkpt lat=\"" + l.getLatitude() + "\" lon=\"" + l.getLongitude() + "\"><time>" + df.format(new Date(l.getTime())) + "</time></trkpt>\n";
        }

        String footer = "</trkseg></trk></gpx>";

        File file = new File(Environment.getExternalStorageDirectory() + "/TrekPricer" + File.separator + filename + ".gpx");


        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file, false);
            writer.append(header);
            writer.append(name);
            writer.append(segments);
            writer.append(footer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "Error Writting Path", e);
        }

    }

    public static void createDirectory(){
        File folder = new File(Environment.getExternalStorageDirectory() + "/TrekPricer");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            // Do something on success
            Log.e("Success", "folder creation");
        } else {
            // Do something else on failure
            Log.e("Failure", "folder creation");
        }
    }

    public static float getAverageSpeed(float distance, String timeTaken) {

        String[] separated = timeTaken.split(":");

        int hours = Integer.parseInt(separated[0]);

        int minutes = Integer.parseInt(separated[1]);

        int seconds = Integer.parseInt(separated[2]);

        float totalTimeInSeconds = ( hours * 3600 ) + (minutes * 60) + seconds;

        float speed = 0;
        if(distance > 0) {
            float distancePerSecond = distance/totalTimeInSeconds;
            float distancePerMinute = distancePerSecond*60;
            float distancePerHour = distancePerMinute*60;

            speed = distancePerHour;
        }

        return speed;
    }
}



