package edu.itu.csc.quakenweather.utilities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.itu.csc.quakenweather.activities.MainActivity;
import edu.itu.csc.quakenweather.database.RegistrationProvider;

import edu.itu.csc.quakenweather.R;

/**
 *
 * Utility class to hold generic utilities.
 *
 * @author "Jigar Gosalia"
 */
public class Utility {

    public static SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy KK:mm a");

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
     * Update the last viewed time in database for About screen.
     *
     * @param caller
     * @param context
     */
    public static void updateLastViewed(String caller, Context context) {
        Map<String, String> map = Utility.getEntry(context);
        Log.d(MainActivity.APP_TAG, caller + " Time: " + (new Date()));
        if (map != null && map.size() > 0) {
            Utility.updateEntry(context);
        } else {
            Utility.addEntry(context);
        }
    }

    /**
     * Add an entry in registration table.
     *
     * @param context
     */
    public static void addEntry(Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationProvider.APP_NAME , context.getString(R.string.app_name));
        contentValues.put(RegistrationProvider.INSTALL_DATE , String.valueOf(new Date().getTime()));
        contentValues.put(RegistrationProvider.LAST_DATE , String.valueOf(new Date().getTime()));
        Uri uri = context.getContentResolver().insert(RegistrationProvider.CONTENT_URI, contentValues);
        Log.d(MainActivity.APP_TAG, "Added uri: " + uri.toString());
    }

    /**
     * Get entries from registration tables.
     *
     * @param context
     * @return
     */
    public static Map<String, String> getEntry(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cursor = context.getContentResolver().query(ContentUris.withAppendedId(RegistrationProvider.CONTENT_URI, Long.valueOf(1)), null, null, null, null);
        if (cursor != null
                && cursor.moveToNext()) {
            map.put(RegistrationProvider.INSTALL_DATE, cursor.getString(cursor.getColumnIndex(RegistrationProvider.INSTALL_DATE)));
            map.put(RegistrationProvider.LAST_DATE, cursor.getString(cursor.getColumnIndex(RegistrationProvider.LAST_DATE)));
        }
        Log.d(MainActivity.APP_TAG, "Get Entries: " + map);
        return map;
    }

    /**
     * update entry in registration table.
     *
     * @param context
     */
    public static void updateEntry(Context context) {
        long timestamp = new Date().getTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegistrationProvider.LAST_DATE , String.valueOf(timestamp));
        int result = context.getContentResolver().update(ContentUris.withAppendedId(RegistrationProvider.CONTENT_URI, Long.valueOf(1)), contentValues, null, null);
        Log.d(MainActivity.APP_TAG, "Updated: " + result + " time: " + Utility.getFormattedDate(Utility.formatter, String.valueOf(timestamp)));
    }

}
