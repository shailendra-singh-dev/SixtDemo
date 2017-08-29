package com.shail.sixtdemo.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.shail.sixtdemo.LocationUpdatesService;
import com.shail.sixtdemo.R;
import com.shail.sixtdemo.application.SixtApplication;
import com.shail.sixtdemo.messages.MessagePumpEngine;
import com.shail.sixtdemo.utils.CommonActions;
import com.shail.sixtdemo.utils.Print;

import static com.shail.sixtdemo.utils.AppUtils.BROADCAST_GOOGLE_SERVICES_CONNECTION_FAILED;
import static com.shail.sixtdemo.utils.AppUtils.BROADCAST_GOOGLE_SERVICES_CONNECTION_SUCCESS;
import static com.shail.sixtdemo.utils.AppUtils.GOOGLE_SERVICES_CONNECTION_FAILED_RESULT;
import static com.shail.sixtdemo.utils.AppUtils.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.shail.sixtdemo.utils.AppUtils.REQUEST_CHECK_SETTINGS;
import static com.shail.sixtdemo.utils.AppUtils.REQUEST_PLAY_SERVICES_RESOLVE_ERROR;

public class BaseActivity extends AppCompatActivity implements Handler.Callback {
    private static final MessagePumpEngine.MessageID[] MESSAGES = {
            MessagePumpEngine.MessageID.USER_LOCATION_CHANGED,
            MessagePumpEngine.MessageID.USER_LOCATION_SETTINGS_CHANGED
    };
    private final Handler mMessageHandler = new Handler(this);
    private BroadcastReceiver mGoogleServicesConnectionFailedReceiver;
    private BroadcastReceiver mGoogleServicesConnectionSuccessReceiver;
    private boolean mResolvingError;
    private Location mCurrentLocation;
    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;
    // Tracks the bound state of the service.
    private boolean mBound = false;
    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkPlayServices()) {
            checkForLocationPermission();
        }
        MessagePumpEngine.addAppMessageHandler(mMessageHandler, MESSAGES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mGoogleServicesConnectionSuccessReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mGoogleServicesConnectionFailedReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationService();
        MessagePumpEngine.removeAppMessageHandler(mMessageHandler, MESSAGES);
    }

    public Location getUserLocation() {
        if (null == mCurrentLocation) {
            mCurrentLocation = SixtApplication.getInstance().getCurrentLocation();
        }
        return mCurrentLocation;
    }

    public void startLocationService() {
        handleGoogleServiceConnectionSuccess();
        handleGoogleServiceConnectionFailed();
        if (null != mService) {
            mService.requestLocationUpdates();
        } else {
            bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
            startService(new Intent(getApplicationContext(), LocationUpdatesService.class));
        }
    }

    public void stopLocationService() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            Print.i("stopLocationService(),mBound:" + mBound);
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    public void checkLocationUpdateSettings() {
        if (null == mService) {
            return;
        }
        Print.i("checkLocationUpdateSettings()");
        LocationSettingsRequest locationSettingsRequest = mService.getLocationSettingsRequest();
        GoogleApiClient googleApiClient = mService.getGoogleApiClient();
        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationSettingsRequest).setResultCallback(
                new ResultCallback<LocationSettingsResult>() {

                    @Override
                    public void onResult(LocationSettingsResult locationSettingsResult) {
                        final Status status = locationSettingsResult.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                Print.i("All location settings are satisfied.");
                                try {
                                    mService.setRequestingLocationUpdates(true);
                                    mService.requestLocationUpdates();
                                } catch (SecurityException e) {
                                    Print.e("PendingIntent unable to execute request.");
                                    askForLocationAccessPermission(PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                                }
                                break;

                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Print.i("Location settings are not satisfied. Attempting to upgrade location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    status.startResolutionForResult(BaseActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    Print.e("PendingIntent unable to execute request.");
                                }
                                break;

                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in Settings.";
                                Print.e(errorMessage);
                                askForLocationAccessPermission(PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                                break;

                            default:
                                break;
                        }
                    }
                });
    }

    public void askForLocationAccessPermission(int permissionCode) {
        Print.i("askForLocationAccessPermission()");
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, permissionCode);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, permissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationService();
                } else {
                    askForLocationAccessPermission(PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
                return;
            }
            default:
                break;
        }
    }

    private void checkForLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                askForLocationAccessPermission(PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                startLocationService();
            }
        } else {
            startLocationService();
        }
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Print.i("Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (connectionResult.hasResolution()) {
            try {
                mResolvingError = true;
                connectionResult.startResolutionForResult(this, REQUEST_PLAY_SERVICES_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                startLocationService();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(connectionResult.getErrorCode());
            mResolvingError = true;
        }
    }

    // The rest of this code is all about building the error dialog

    /* Creates a dialog for an error message */
    private void showErrorDialog(final int errorCode) {
        final Dialog errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode, REQUEST_PLAY_SERVICES_RESOLVE_ERROR);
        errorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                errorDialog.dismiss();
                mResolvingError = false;
            }
        });
        errorDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Print.i("User agreed to make required location settings changes.");
                        // Nothing to do. checkLocationUpdateSettings() gets called in onResume again.
                        startLocationService();
                        break;

                    case Activity.RESULT_CANCELED:
                        Print.e("User chose not to make required location settings changes.");
                        startLocationService();
                        break;
                }
                break;

            case REQUEST_PLAY_SERVICES_RESOLVE_ERROR:
                mResolvingError = false;
                switch (resultCode) {
                    case RESULT_OK:
                        // Make sure the app is not already connected or attempting to connect
                        Print.i("User agreed to make Google Play services update.");
                        startLocationService();
                        checkLocationUpdateSettings();
                        break;

                    case RESULT_CANCELED:
                        Print.e("User chose not to make Google Play services update.");
                        finish();
                        break;

                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    protected boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, REQUEST_PLAY_SERVICES_RESOLVE_ERROR).show();
            } else {
                Print.i("This device is not supported.");
                CommonActions.showAlert(this, getString(R.string.alert_error_google_services), R.string.alert_dialog_positive_btn_text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        finish();
                    }
                });
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what >= MessagePumpEngine.MessageID.CUSTOM_EVENT_START) {
            //Handle local Messages..
        } else {
            //Pumped Messages..
            final MessagePumpEngine.MessageID id = MessagePumpEngine.MessageID.getID(message.what);
            Print.i("handleMessage(),id:" + id);
            switch (id) {
                case USER_LOCATION_SETTINGS_CHANGED:
                    onLocationSettingsUpdated();
                    return true;

            }
        }
        return false;
    }

    private void handleGoogleServiceConnectionSuccess() {
        mGoogleServicesConnectionSuccessReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                onConnectionSuccess();
            }
        };
        IntentFilter googleServicesConnectionSuccess = new IntentFilter(BROADCAST_GOOGLE_SERVICES_CONNECTION_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(mGoogleServicesConnectionSuccessReceiver, googleServicesConnectionSuccess);
    }

    private void handleGoogleServiceConnectionFailed() {
        mGoogleServicesConnectionFailedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                ConnectionResult connectionResult = intent.getParcelableExtra(GOOGLE_SERVICES_CONNECTION_FAILED_RESULT);
                onConnectionFailed(connectionResult);
            }
        };
        IntentFilter googleServicesConnectionFailed = new IntentFilter(BROADCAST_GOOGLE_SERVICES_CONNECTION_FAILED);
        LocalBroadcastManager.getInstance(this).registerReceiver(mGoogleServicesConnectionFailedReceiver, googleServicesConnectionFailed);
    }


    private void onConnectionSuccess() {
        if (null != mService) {
            mService.requestLocationUpdates();
            checkLocationUpdateSettings();
        }
    }

    private void onLocationSettingsUpdated() {
        if (null != mService && null != mService.getGoogleApiClient()) {
            GoogleApiClient googleApiClient = mService.getGoogleApiClient();
            if (googleApiClient.isConnected()) {
                checkLocationUpdateSettings();
            } else {
                startLocationService();
            }
        } else {
            startLocationService();
        }
    }

}
