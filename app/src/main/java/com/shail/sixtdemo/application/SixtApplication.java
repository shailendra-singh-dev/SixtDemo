package com.shail.sixtdemo.application;

import android.app.Application;
import android.net.Uri;

import com.shail.sixtdemo.messages.MessagePumpEngine;
import com.shail.sixtdemo.utils.AppUtils;
import com.shail.sixtdemo.utils.Print;
import com.squareup.picasso.Picasso;


/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class SixtApplication extends Application {
    private static final MessagePumpEngine MESSAGE_PUMP_ENGINE = new MessagePumpEngine();

    private static SixtApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initPicasso();
    }

    public static synchronized SixtApplication getInstance() {
        return mInstance;
    }

    public static MessagePumpEngine getPumpEngine() {
        return MESSAGE_PUMP_ENGINE;
    }

    public boolean isInternetConnectionAvailable() {
        return AppUtils.isInternetConnectionAvailable(this);
    }
    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Print.e("onImageLoadFailed(),uri:" + uri + " ,exception:" + exception);
            }
        });
        Picasso picasso = builder.build();
        picasso.setLoggingEnabled(true);
        Picasso.setSingletonInstance(picasso);
    }
}
