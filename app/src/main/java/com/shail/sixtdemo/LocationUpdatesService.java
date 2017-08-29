package com.shail.sixtdemo;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.shail.sixtdemo.application.SixtApplication;
import com.shail.sixtdemo.utils.Print;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;
import static com.shail.sixtdemo.utils.AppUtils.BROADCAST_GOOGLE_SERVICES_CONNECTION_FAILED;
import static com.shail.sixtdemo.utils.AppUtils.GOOGLE_SERVICES_CONNECTION_FAILED_RESULT;
import static com.shail.sixtdemo.utils.AppUtils.MESSAGE_REFRESH_TIME;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class LocationUpdatesService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = LocationUpdatesService.class.getSimpleName();
    private static final float SMALLEST_DISPLACEMENT = 50;

    private static LocationUpdatesService mLocationUpdateService;

    private final IBinder mBinder = new LocalBinder();

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MESSAGE_REFRESH_TIME;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private boolean mChangingConfiguration = false;

    /**
     * The entry point to Google Play Services.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Contains parameters used by {@link com.google.android.gms.location.FusedLocationProviderApi}.
     */
    private LocationRequest mLocationRequest;

    private Handler mServiceHandler;

    /**
     * The current location.
     */
    private Location mLocation;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected boolean mRequestingLocationUpdates; //If GoogleClient is connected.then it will be TRUE else false.

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;

    public LocationUpdatesService() {
    }

    @Override
    public void onCreate() {
        Print.i("onCreate()");
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Print.i("Service started");
        if (null == mLocation) {
            init();
        }
        return START_STICKY;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Print.i("onConfigurationChanged()");
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Called when a client (Main in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        Print.i("in onBind()");
//        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        // Called when a client (Main in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        Print.i("in onRebind()");
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Print.i("Last client unbound from service");

        // Called when the last client (Main in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in Main, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration && mRequestingLocationUpdates) {
            Print.i("Starting foreground service");
        }
        return true; // Ensures onRebind() is called when a client re-binds.
    }

    @Override
    public void onDestroy() {
        Print.i("onDestroy()");
        mServiceHandler.removeCallbacksAndMessages(null);
        mGoogleApiClient.disconnect();
        removeLocationUpdates();
        setInstance(null);
    }


    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Print.i("Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    protected void buildLocationSettingsRequest() {
        Print.i("buildLocationSettingsRequest()");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        builder.setNeedBle(true);
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();
    }


    public LocationSettingsRequest getLocationSettingsRequest() {
        return mLocationSettingsRequest;
    }

    private void updateUI(final Location location) {
        // Notify anyone listening for broadcasts about the new location.
        Print.i("updateUI()");
        SixtApplication.getInstance().setCurrentLocation(location);
    }


    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     * {@link SecurityException}.
     */
    public void requestLocationUpdates() {
        Print.i("requestLocationUpdates mGoogleApiClient:" + mGoogleApiClient + ",mGoogleApiClient.isConnected():" + mGoogleApiClient.isConnected() + "" +
                ",mRequestingLocationUpdates:" + mRequestingLocationUpdates);
        if (null == mGoogleApiClient || !mGoogleApiClient.isConnected() || !mRequestingLocationUpdates) {
            return;
        }

        try {
            FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, LocationUpdatesService.this);
        } catch (SecurityException unlikely) {
            mRequestingLocationUpdates = false;
            Print.e("Lost location permission. Could not request updates. " + unlikely);
        }
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     * {@link SecurityException}.
     */
    public void removeLocationUpdates() {
        Print.i("Removing location updates");
        if (null == mGoogleApiClient || !mGoogleApiClient.isConnected() || !mRequestingLocationUpdates) {
            return;
        }
        try {
            FusedLocationApi.removeLocationUpdates(mGoogleApiClient, LocationUpdatesService.this);
            mRequestingLocationUpdates = false;
            stopSelf();
        } catch (SecurityException unlikely) {
            mRequestingLocationUpdates = true;
            Print.e("Lost location permission. Could not remove updates. " + unlikely);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Print.i("GoogleApiClient connected");
        try {
            mLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
            Print.i("GoogleApiClient connected(),mLocation:" + mLocation);
            if (null == mLocation) {
                mLocation = SixtApplication.getInstance().getCurrentLocation();
            }
            if (null != mLocation) {
                // Notify anyone listening for broadcasts about the new location.
                updateUI(mLocation);
                mRequestingLocationUpdates = true;
                requestLocationUpdates();
            } else {
                mRequestingLocationUpdates = true;
                requestLocationUpdates();
            }
        } catch (SecurityException unlikely) {
            mRequestingLocationUpdates = false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // In this example, we merely log the suspension.
        Print.e("GoogleApiClient connection suspended.");
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Print.i("Connection suspended==");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // In this example, we merely log the failure.
        Print.e("GoogleApiClient connection failed.");
        // Notify anyone listening for broadcasts about the new location.
        Intent intent = new Intent(BROADCAST_GOOGLE_SERVICES_CONNECTION_FAILED);
        intent.putExtra(GOOGLE_SERVICES_CONNECTION_FAILED_RESULT, connectionResult);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        Print.i("onLocationChanged() :location: " + location + ",mLocation:" + mLocation);
        if (mLocation == null) {
            mLocation = location;
            updateUI(location);
        }
        if (mLocation.getLatitude() != location.getLatitude() && mLocation.getLongitude() != location.getLongitude()) {
            updateUI(location);
        }
        // Update notification content if running as a foreground service.
        if (serviceIsRunningInForeground(this)) {
            Print.i("onLocationChanged(),serviceIsRunningInForeground");
        }
    }

    /**
     * Sets the location request parameters.
     */
    private void createLocationRequest() {
        Print.i("createLocationRequest()");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocationUpdatesService getService() {
            return LocationUpdatesService.this;
        }
    }

    /**
     * Returns true if this is a foreground service.
     *
     * @param context The {@link Context}.
     */
    public boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (getClass().getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void setInstance(final LocationUpdatesService locationUpdateService) {
        mLocationUpdateService = locationUpdateService;
    }

    public static LocationUpdatesService getInstance() {
        return mLocationUpdateService;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public Location getLocation() {
        return mLocation;
    }


    private void init() {
        setInstance(this);
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
    }
    public void setRequestingLocationUpdates(boolean requestingLocationUpdates) {
        mRequestingLocationUpdates = requestingLocationUpdates;
    }

}
