package com.shail.sixtdemo.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.shail.sixtdemo.R;
import com.shail.sixtdemo.activities.MainActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class CommonActions {

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

    public static void loadImagebyPicasso(final Context context, final String url, Target target, final int placeHolder, int errorPlaceHolder) {
        if (url != null) {
            Picasso.with(context).load(url)
                    .placeholder(placeHolder)
                    .error(errorPlaceHolder)
                    .into(target);
        }
    }

    public static void loadImagebyPicasso(final Context context, final String url, ImageView imageView, final int placeHolder, int errorPlaceHolder) {
        if (url != null) {
            Picasso.with(context).load(url)
                    .placeholder(placeHolder)
                    .error(errorPlaceHolder)
                    .into(imageView);
        }
    }

    public static boolean isActivityRunning(Context context) {
        if (context instanceof MainActivity) {
            MainActivity baseActivity = (MainActivity) context;
            return !baseActivity.isFinishing();
        }
        return false;
    }

}
