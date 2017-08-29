package com.shail.sixtdemo.application;

import android.app.Application;
import android.location.Location;

import com.shail.sixtdemo.messages.MessagePumpEngine;
import com.shail.sixtdemo.utils.AppUtils;
import com.shail.sixtdemo.utils.Print;


/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class SixtApplication extends Application {
    private static final MessagePumpEngine MESSAGE_PUMP_ENGINE = new MessagePumpEngine();

    private static SixtApplication mInstance;
    private Location mCurrentLocation;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized SixtApplication getInstance() {
        return mInstance;
    }

    public void setCurrentLocation(Location location) {
        Print.i("setCurrentLocation()," + location);
        mCurrentLocation = location;
    }

    public Location getCurrentLocation() {
        return mCurrentLocation;
    }

    public static MessagePumpEngine getPumpEngine() {
        return MESSAGE_PUMP_ENGINE;
    }

    public boolean isInternetConnectionAvailable() {
        return AppUtils.isInternetConnectionAvailable(this);
    }
}
