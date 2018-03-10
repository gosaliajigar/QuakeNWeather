package edu.itu.csc.quakenweather.utilities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.itu.csc.quakenweather.activities.MainActivity;
import edu.itu.csc.quakenweather.database.ErrorProvider;
import edu.itu.csc.quakenweather.database.RegistrationProvider;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.models.Quake;

/**
 * Utility class to hold generic utilities.
 *
 * @author "Jigar Gosalia"
 */
public class Utility {

    public static final String DEFAULT_MAGNITUDE = "3.0";

    public static final String DEFAULT_DURATION = "last24hr";

    public static final String DEFAULT_DISTANCE = "miles";

    public static final SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy KK:mm a");

    public static final Map<String, String> urlType = new HashMap<String, String>();

    public static final Map<String, String> durationMap = new HashMap<String, String>();

    static {
        urlType.put("lasthour", "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
        urlType.put("today", "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson");
        urlType.put("last24hr", "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson");
        urlType.put("thisweek", "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson");
        urlType.put("thismonth", "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson");

        durationMap.put("lasthour", "Last Hour");
        durationMap.put("today", "Today");
        durationMap.put("last24hr", "Last 24 Hour");
        durationMap.put("thisweek", "This Week");
        durationMap.put("thismonth", "This Month");
    }

    /**
     * Get formatted Date for displaying in About screen.
     *
     * @param formatter
     * @param dateInLong
     * @return
     */
    public static String getFormattedDate(SimpleDateFormat formatter, String dateInLong) {
        Date date = new Date(Long.valueOf(dateInLong));
        return formatter.format(date);
    }

    /**
     * Get formatted Depth i.e. with mi or km suffix depending on the metric system in settings.
     *
     * @param depth
     * @param distance
     * @return
     */
    public static String getFormattedDepth(String depth, String distance) {
        return (isMiles(distance) ? (depth + " Mi") : (depth + " Km"));
    }

    /**
     * Get depth in miles or kilometers depending on distance unit in settings.
     *
     * @param depth
     * @param distance
     * @return
     */
    public static String getConvertedDepth(double depth, String distance) {
        return (isMiles(distance) ? String.format("%.2f", depth) : String.format("%.2f", 1.60934 * depth));
    }

    /**
     * Get Earth Quake location from USGS data.
     *
     * @param place
     * @return
     */
    public static String getPlace(String place) {
        if (place != null
                && place.contains(" of ")) {
            return place.substring(place.lastIndexOf(" of ") + " of ".length());
        }
        return place;
    }

    /**
     * Check if the settings i.e. preferences contain miles as preferred distance unit.
     *
     * @param preference
     * @return
     */
    public static boolean isMiles(String preference) {
        return ((preference.equals("kilometers")) ? false : true);
    }

    /**
     * Get date time for listview on the latest data page.
     *
     * @param unixTime
     * @return
     * @throws Exception
     */
    public static String getDateTime(long unixTime) throws Exception {
        StringBuilder builder = new StringBuilder();
        Date quakeTime = new Date((long) unixTime);
        Date current = new Date();
        SimpleDateFormat displayFormatter = new SimpleDateFormat("MM/dd hh:mm a");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("K:mm a");
        String quakeTimeS = dateFormatter.format(quakeTime);
        String currentS = dateFormatter.format(current);
        if (quakeTimeS != null
                && currentS != null) {
            if (quakeTimeS.equals(currentS)) {
                builder.append("Today " + timeFormatter.format(quakeTime));
            } else {
                builder.append(displayFormatter.format(quakeTime));
            }
        } else {
            builder.append("N/A");
        }
        return builder.toString();
    }


    /**
     * Get date time for feedback email.
     *
     * @param unixTime
     * @return
     * @throws Exception
     */
    public static String getDateTimeForFeedback(long unixTime) throws Exception {
        StringBuilder builder = new StringBuilder();
        Date quakeTime = new Date((long) unixTime);
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (quakeTime != null) {
            String quakeTimeS = dateTimeFormatter.format(quakeTime);
            if (quakeTimeS != null) {
                builder.append(quakeTimeS);
            }
        }
        return builder.toString();
    }

    /**
     * Update the last viewed time in database for About screen.
     *
     * @param caller
     * @param context
     */
    public static void updateLastViewed(String caller, Context context) {
        Map<String, String> map = Utility.getRegistrationEntry(context);
        Log.d(MainActivity.APP_TAG, caller + " Time: " + (new Date()));
        if (map != null && map.size() > 0) {
            Utility.updateRegistrationEntry(context);
        } else {
            Utility.addRegistrationEntry(context);
        }
    }

    /**
     * Set the text color on the listview depending on the magnitude of the quake.
     *
     * 0.0 to 3.5  - HUE_GREEN  - #00FF00
     * 3.5 to 5.5  - HUE_ORANGE - #FF6347
     * 5.5 & above - HUE_RED    - #FF0000
     *
     * @param input
     * @return
     */
    public static int getTextColorFromMagnitude(String input) {
        double magnitude = Double.parseDouble(input);
        if (magnitude >=0 && magnitude <= 3.5) {
            return Color.parseColor("#00FF00");
        } else if (magnitude > 3.5 && magnitude <= 5.5) {
            return Color.parseColor("#FF6347");
        } else {
            return Color.parseColor("#FF0000");
        }
    }

    /**
     * Set the text color on the google marker depending on the magnitude of the earthquake.
     *
     * 0.0 to 3.5  - HUE_GREEN  - #00FF00
     * 3.5 to 5.5  - HUE_ORANGE - #FF6347
     * 5.5 & above - HUE_RED    - #FF0000
     *
     * @param input
     * @return
     */
    public static float getMarkerColorFromMagnitude(String input) {
        double magnitude = Double.parseDouble(input);
        if (magnitude >=0 && magnitude <= 3.5) {
            return BitmapDescriptorFactory.HUE_GREEN;
        } else if (magnitude > 3.5 && magnitude <= 5.5) {
            return BitmapDescriptorFactory.HUE_ORANGE;
        } else {
            return BitmapDescriptorFactory.HUE_RED;
        }
    }

    /**
     * Add an entry in registration table.
     *
     * @param context
     */
    public static void addRegistrationEntry(Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationProvider.APP_NAME , context.getString(R.string.app_name));
        contentValues.put(RegistrationProvider.INSTALL_DATE , String.valueOf(new Date().getTime()));
        contentValues.put(RegistrationProvider.LAST_DATE , String.valueOf(new Date().getTime()));
        Uri uri = context.getContentResolver().insert(RegistrationProvider.CONTENT_URI, contentValues);
        Log.d(MainActivity.APP_TAG, "Utility: Added registration uri: " + uri.toString());
    }

    /**
     * Get entries from registration tables.
     *
     * @param context
     * @return
     */
    public static Map<String, String> getRegistrationEntry(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = context.getContentResolver().query(ContentUris.withAppendedId(RegistrationProvider.CONTENT_URI, Long.valueOf(1)), null, null, null, null);
        if (cursor != null
                && cursor.moveToNext()) {
            map.put(RegistrationProvider.INSTALL_DATE, cursor.getString(cursor.getColumnIndex(RegistrationProvider.INSTALL_DATE)));
            map.put(RegistrationProvider.LAST_DATE, cursor.getString(cursor.getColumnIndex(RegistrationProvider.LAST_DATE)));
        }
        Log.d(MainActivity.APP_TAG, "Utility: Get Registration Entries: " + map);
        return map;
    }

    /**
     * update entry in registration table.
     *
     * @param context
     */
    public static void updateRegistrationEntry(Context context) {
        long timestamp = new Date().getTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationProvider.LAST_DATE , String.valueOf(timestamp));
        int result = context.getContentResolver().update(ContentUris.withAppendedId(RegistrationProvider.CONTENT_URI, Long.valueOf(1)), contentValues, null, null);
        Log.d(MainActivity.APP_TAG, "Utility: Updated registration: " + result + " time: " + Utility.getFormattedDate(Utility.formatter, String.valueOf(timestamp)));
    }

    /**
     * Add an entry in error table.
     *
     * @param context
     */
    public static void addErrorEntry(Context context, Exception exception) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ErrorProvider.ERROR_DETAILS , Log.getStackTraceString(exception));
        contentValues.put(ErrorProvider.LAST_DATE , String.valueOf(new Date().getTime()));
        Uri uri = context.getContentResolver().insert(ErrorProvider.CONTENT_URI, contentValues);
        Log.d(MainActivity.APP_TAG, "Utility: Added error uri: " + uri.toString());
    }

    /**
     * Get entries from error tables.
     *
     * @param context
     * @return
     */
    public static Map<String, String> getErrorEntry(Context context) {
        Map<String, String> map = new TreeMap<>(Collections.reverseOrder());
        Cursor cursor = context.getContentResolver().query(ErrorProvider.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                map.put(cursor.getString(cursor.getColumnIndex(ErrorProvider.LAST_DATE)), cursor.getString(cursor.getColumnIndex(ErrorProvider.ERROR_DETAILS)));
            }
        }
        Log.d(MainActivity.APP_TAG, "Utility: Get Error Entries: " + map.size());
        return map;
    }

    /**
     * Call USGS API to get quake data and parse the data.
     *
     * @param caller
     * @param urlPath
     * @param magnitude
     * @param duration
     * @param distance
     * @return
     */
    public static List<Quake> getQuakeData(String caller, String urlPath, String magnitude, String duration, String distance, Context context) {
        List<Quake> quakeList = new ArrayList<Quake>();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            Log.d(MainActivity.APP_TAG, "Utility: Connecting " + url);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();
            Log.d(MainActivity.APP_TAG, "Utility: Received data from " + url);

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Log.d(MainActivity.APP_TAG, "Utility: JSON String: " + stringBuilder.toString());
            if (stringBuilder.length() > 0) {
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray featuresArray = jsonObject.getJSONArray("features");

                if (featuresArray != null && featuresArray.length() > 0) {
                    Log.d(MainActivity.APP_TAG, caller + " : " + stringBuilder.toString());
                    for (int i = 0; i < featuresArray.length(); i++) {
                        JSONObject propertiesObject = featuresArray.getJSONObject(i).getJSONObject("properties");
                        if (propertiesObject != null) {
                            float mag = (propertiesObject.getString("mag") != null && !propertiesObject.getString("mag").equals("null")) ? Float.valueOf(propertiesObject.getString("mag").toString()) : 0;
                            if (mag < Float.valueOf(magnitude)) {
                                continue;
                            }
                            long timeStamp = (propertiesObject.getString("time") != null) ? Long.valueOf(propertiesObject.getString("time").toString()) : 0;
                            if (timeStamp > 0 && duration != null && duration.equals("today") && url.toString().equals(Utility.urlType.get("today"))) {
                                if (!inRange(timeStamp)) {
                                    continue;
                                }
                            }
                            String place = propertiesObject.getString("place").toString();
                            String urlLink = propertiesObject.getString("url").toString();
                            String significance = propertiesObject.getString("sig").toString();
                            String status = propertiesObject.getString("status").toString();
                            String title = propertiesObject.getString("title").toString();

                            JSONObject geometryObject = featuresArray.getJSONObject(i).getJSONObject("geometry");
                            String eventId = featuresArray.getJSONObject(i).getString("id").toString();
                            if (geometryObject != null) {
                                double longitude = (geometryObject.getJSONArray("coordinates") != null && geometryObject.getJSONArray("coordinates").length() > 0) ? Double.parseDouble(geometryObject.getJSONArray("coordinates").get(0).toString()) : 0;
                                double latitude = (geometryObject.getJSONArray("coordinates") != null && geometryObject.getJSONArray("coordinates").length() > 1) ? Double.parseDouble(geometryObject.getJSONArray("coordinates").get(1).toString()) : 0;
                                double depth = (geometryObject.getJSONArray("coordinates") != null && geometryObject.getJSONArray("coordinates").length() > 2) ? Double.parseDouble(geometryObject.getJSONArray("coordinates").get(2).toString()) : 0;
                                if (longitude != 0 && longitude != 0 && depth != 0) {
                                    quakeList.add(new Quake(String.valueOf(mag), longitude, latitude, place, depth, String.valueOf(timeStamp), urlLink, status, title, significance, eventId));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            quakeList = null;
            Log.e(MainActivity.APP_TAG, "Utility: getQuakeData Exception: " + exception.toString());
            addErrorEntry(context, exception);
            exception.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        Log.d(MainActivity.APP_TAG, caller + " : Quake Count=" + ((quakeList != null) ? quakeList.size() : null));
        return quakeList;
    }


    /**
     * Check if the given time is today or different day to display in listview of latest quake page.
     *
     * @param quakeTime
     * @return
     */
    public static boolean inRange(long quakeTime) throws Exception {
        SimpleDateFormat dayFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String quakeDay = dayFormatter.format(new Date(quakeTime));
        String currentDay = dayFormatter.format(new Date());
        return (quakeDay.equals(currentDay));
    }

    /**
     * Retrieve Device Information for debugging on feedback email.
     *
     * @return
     */
    public static String getDebuggingInformation(Context context, boolean deviceInformation, boolean errorInformation) {
        StringBuilder debuggingDetails = new StringBuilder();
        if (deviceInformation || errorInformation) {
            debuggingDetails.append("\n\n--------\n\n");
            debuggingDetails.append("FOR DEBUGGING PURPOSE ONLY\n\n");
            if (deviceInformation) {
                debuggingDetails.append(getDeviceInformation(context));
            }
            if (errorInformation) {
                debuggingDetails.append(getErrorInformation(context));
            }
        }
        Log.d(MainActivity.APP_TAG, "Debugging Information: " + debuggingDetails.toString());
        return debuggingDetails.toString();
    }

    /**
     * Retrieve Device Information for debugging on feedback email.
     *
     * @return
     */
    public static String getDeviceInformation(Context context) {
        String details =  "SDK\t: " + Build.VERSION.SDK_INT
                +"\nBRD\t: " + Build.BRAND
                +"\nH/W\t: " + Build.HARDWARE
                +"\nHST\t: " + Build.HOST
                +"\nID \t: " + Build.ID
                +"\nMFR\t: " + Build.MANUFACTURER
                +"\nMDL\t: " + Build.MODEL
                +"\nPRD\t: " + Build.PRODUCT
                +"\nDEV\t: " + Build.DEVICE
                +"\nAPP\t: " + context.getResources().getString(R.string.software_version)
                +"\n\n\n";
        Log.d(MainActivity.APP_TAG, "Device Details: " + details);
        return details;
    }

    /**
     * Retrieve Error Information for debugging on feedback email.
     *
     * @return
     */
    public static String getErrorInformation(Context context) {
        StringBuilder errorDetails = new StringBuilder();
        Map<String, String> errors = getErrorEntry(context);
        if (errors != null && !errors.isEmpty()) {
            int count = 0;
            for (Map.Entry<String, String> error : errors.entrySet()) {
                if (count < 2) {
                    String dateTime = null;
                    try {
                        dateTime = getDateTimeForFeedback(Long.parseLong(error.getKey()));
                    } catch (Exception exception) {
                        // do nothing
                    }
                    if (dateTime == null) {
                        errorDetails.append(error.getKey() + " || " + error.getValue() + "\n");
                    } else {
                        errorDetails.append(dateTime + " || " + error.getValue() + "\n");
                    }
                }
                count++;
            }
        }
        Log.d(MainActivity.APP_TAG, "Error Details: " + errorDetails.toString());
        return errorDetails.toString();
    }

}
