package edu.itu.csc.quakenweather.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * ErrorProvider for error details.
 *
 * @author "Jigar Gosalia"
 */
public class ErrorProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "edu.itu.csc.quakenweather.error";
    private static final String URL = "content://" + PROVIDER_NAME + "/error";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String ERROR_DETAILS = "error_details";
    public static final String LAST_DATE = "last_date";

    private ErrorDataHelper errorDataHelper = null;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        errorDataHelper = new ErrorDataHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor = errorDataHelper.getEntry(null, null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri result = null;
        long _id = errorDataHelper.addEntry(contentValues);
        if ( _id > 0 )
            result = ContentUris.withAppendedId(CONTENT_URI, _id);
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String id = uri.getPathSegments().get(1);
        int result = errorDataHelper.deleteEntry(id);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String id = id = uri.getPathSegments().get(1);
        int result = errorDataHelper.updateEntry(id, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }
}
