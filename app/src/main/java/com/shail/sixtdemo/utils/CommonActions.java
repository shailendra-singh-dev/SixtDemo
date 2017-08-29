package com.shail.sixtdemo.utils;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shail.sixtdemo.R;
import com.shail.sixtdemo.activities.MainActivity;
import com.shail.sixtdemo.views.AspectRatioImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by joyal on 10/5/2015.
 */
public class CommonActions {
    private static ProgressDialog mProgressDialog;

    /**
     * Method for showing toast
     *
     * @param Message
     */
    public static void showToast(Context con, String Message) {
        Toast.makeText(con, Message, Toast.LENGTH_LONG).show();
    }

    /**
     * Returns the extracted text from the edit text given
     *
     * @param editText
     * @return
     */
    public static String getTextFrom(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * Method for checking valid email id
     *
     * @param target
     * @return
     */
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    public static void hideKeyboard(AppCompatActivity appCompatActivity) {
        if (null == appCompatActivity) {
            return;
        }
        // Check if no view has focus:
        View view = appCompatActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        appCompatActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static boolean isLollipop() {
        if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.LOLLIPOP) {
            return true;
        } else {
            return false;
        }
    }

    public static String timeZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        String timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
        return timeZone.substring(0, 3) + ":" + timeZone.substring(3, 5);
    }

    public static int timeZoneOffsetinSeconds() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        return tz.getOffset(System.currentTimeMillis()) / 1000;
    }

    public static void showAlert(Context con, String message, int positiveButtonID, DialogInterface.OnClickListener positiveButtonClickListener) {
        if (con instanceof MainActivity) {
            if (!isActivityRunning(con)) {
                return;
            }
        }

        new AlertDialog.Builder(con)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(con.getString(positiveButtonID), positiveButtonClickListener)
                .show();
    }

    public static void showAlert(Context con, String message, int positiveButtonID, DialogInterface.OnClickListener positiveButtonClickListener, int negativeButtonID,
                                 DialogInterface.OnClickListener negativeButtonClickListener) {
        if (con instanceof MainActivity) {
            if (!isActivityRunning(con)) {
                return;
            }
        }
        new AlertDialog.Builder(con)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(con.getString(positiveButtonID), positiveButtonClickListener)
                .setNegativeButton(con.getString(negativeButtonID), negativeButtonClickListener)
                .show();
    }

    public static void showAlert(Context con, String message, int positiveButtonID, DialogInterface.OnClickListener positiveButtonClickListener, int negativeButtonID,
                                 DialogInterface.OnClickListener negativeButtonClickListener, int neutralButtonID,
                                 DialogInterface.OnClickListener newtralButtonClickListener) {
        if (con instanceof MainActivity) {
            if (!isActivityRunning(con)) {
                return;
            }
        }
        new AlertDialog.Builder(con)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(con.getString(positiveButtonID), positiveButtonClickListener)
                .setNegativeButton(con.getString(negativeButtonID), negativeButtonClickListener)
                .setNeutralButton(neutralButtonID, newtralButtonClickListener)
                .show();
    }


    /**
     * GPS Settings alert
     */
    public static void showGPSSettingsAlert(final MainActivity baseActivity, int permission) {
        if (!isActivityRunning(baseActivity)) {
            return;
        }

        boolean isCoarseLocationPermissionNotGranted = ContextCompat.checkSelfPermission(baseActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean isFineLocationPermissionNotGranted = ContextCompat.checkSelfPermission(baseActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (isCoarseLocationPermissionNotGranted || isFineLocationPermissionNotGranted) {
            ActivityCompat.requestPermissions(baseActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, permission);
        }
    }

    public static void loadImagebyPicasso(Context con, File file, ImageView iv, int placeHolder, int errorPlaceHolder, int resizedWidth, int resizedHeight) {
        Picasso.with(con)
                .load(file)
                .centerCrop()
                .resize(resizedWidth, resizedHeight)
                .placeholder(placeHolder) // optional
                .error(errorPlaceHolder)         // optional
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(iv);
    }


    public static void loadImagebyPicasso(final Context context, String url, ImageView iv, int placeHolder, int errorPlaceHolder, int resizedWidth, int resizedHeight) {
        if (!url.equalsIgnoreCase("")) {
            Picasso.with(context)
                    .load(url)
                    .resize(resizedWidth, resizedHeight)
                    .placeholder(placeHolder) // optional
                    .error(errorPlaceHolder)         // optional
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(iv);
        }
    }

    public static void loadImagebyPicasso(final Context context, Uri uri, ImageView iv, int placeHolder, int errorPlaceHolder, int resizedWidth, int resizedHeight) {
        if (uri != null) {
            Picasso.with(context)
                    .load(uri)
                    .resize(resizedWidth, resizedHeight)
                    .centerCrop()
                    .placeholder(placeHolder) // optional
                    .error(errorPlaceHolder)         // optional
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(iv);
        }
    }


    public static void loadImagebyPicasso(final Context context, final String url, Target target, final int placeHolder, int errorPlaceHolder) {
        if (url != null) {
            Picasso.with(context).load(url)
                    .placeholder(placeHolder)
                    .error(errorPlaceHolder)
                    .into(target);
        }
    }

    public static void loadImagebyPicasso(final Context context, final String url, final AspectRatioImageView aspectRatioImageView, final int placeHolder, int errorPlaceHolder) {
        if (url != null) {
            Picasso.with(context).load(url)
                    .placeholder(placeHolder)
                    .error(errorPlaceHolder)
                    .into(new Target() {
                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            Print.i("onPrepareLoad(),url:" + url);
                            Bitmap icon = AppUtils.getBitmapFromDrawable(placeHolderDrawable);
                            // Calculate the image ratio of the loaded bitmap
                            float ratio = (float) icon.getWidth() / (float) icon.getHeight();
                            aspectRatioImageView.setAspectRatio(ratio);
                            aspectRatioImageView.setImageBitmap(icon);
                        }

                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Print.i("onBitmapLoaded(), " + url);
                            // Calculate the image ratio of the loaded bitmap
                            float ratio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
                            aspectRatioImageView.setAspectRatio(ratio);
                            aspectRatioImageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Print.i("onBitmapFailed " + url);
                            Bitmap icon = AppUtils.getBitmapFromDrawable(errorDrawable);
                            // Calculate the image ratio of the loaded bitmap
                            float ratio = (float) icon.getWidth() / (float) icon.getHeight();
                            aspectRatioImageView.setAspectRatio(ratio);
                            aspectRatioImageView.setImageBitmap(icon);
                        }
                    });
        }
    }


    public static boolean isActivityRunning(Context context) {
        if (context instanceof MainActivity) {
            MainActivity baseActivity = (MainActivity) context;
            return !baseActivity.isFinishing();
        }
        return false;
    }

    public static void progressShow(Context context) {
        Print.i("progressShow()");
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(context, "Please wait", "Loading");
        mProgressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }

    public static void progressHide() {
        Print.i("progressHide()");
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }


}
