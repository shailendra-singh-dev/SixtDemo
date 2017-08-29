package com.shail.sixtdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class AppUtils {

    public static final float GOOGLE_MAP_ZOOM_LEVEL_DEFAULT = 16;
    public static final double LOCATION_LATITUDE_DEFAULT = 0L;
    public static final double LOCATION_LONGITUDE_DEFAULT = 0L;
    public static final long MESSAGE_REFRESH_TIME = 30 * 1000;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000;
    public static final int REQUEST_CHECK_SETTINGS = PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION + 1;
    public static final int REQUEST_PLAY_SERVICES_RESOLVE_ERROR = REQUEST_CHECK_SETTINGS + 1;
    public static final String BROADCAST_GOOGLE_SERVICES_CONNECTION_FAILED = "google_services_connection_failed";
    public static final String GOOGLE_SERVICES_CONNECTION_FAILED_RESULT = "connection_failed_result";
    public static final String BROADCAST_GOOGLE_SERVICES_CONNECTION_SUCCESS = "google_services_connection_success";
    public static final int HTTP_CODE_SUCCESS = 200;
    public static final int SUCCESS = 1;

    public static boolean isInternetConnectionAvailable(Context con) {
        ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return null != activeNetworkInfo && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // We ask for the bounds if they have been set as they would be most
        // correct, then we check we are  > 0
        final int width = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().height() : drawable.getIntrinsicHeight();

        // Now we check we are > 0
        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width, height <= 0 ? 1 : height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
            return false;
        }
        return true;
    }
}
