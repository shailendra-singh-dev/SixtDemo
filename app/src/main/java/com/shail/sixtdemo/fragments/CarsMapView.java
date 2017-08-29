package com.shail.sixtdemo.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shail.sixtdemo.R;
import com.shail.sixtdemo.activities.MainActivity;
import com.shail.sixtdemo.model.Car;
import com.shail.sixtdemo.utils.CommonActions;
import com.shail.sixtdemo.utils.PicassoMarker;
import com.shail.sixtdemo.utils.Print;

import java.util.ArrayList;

import static com.shail.sixtdemo.utils.AppUtils.GOOGLE_MAP_ZOOM_LEVEL_DEFAULT;
import static com.shail.sixtdemo.utils.AppUtils.LOCATION_LATITUDE_DEFAULT;
import static com.shail.sixtdemo.utils.AppUtils.LOCATION_LONGITUDE_DEFAULT;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class CarsMapView extends BaseFragment implements GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mGoogleMap;
    private float mGoogleMapCurrentZoomLevel;
    private double mLastLocationLatitude;
    private double mLastLocationLongitude;

    public static CarsMapView newInstance() {

        Bundle args = new Bundle();

        CarsMapView fragment = new CarsMapView();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cars_mapview, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainActivity = null;
    }

    private void setUpMap() {
        SupportMapFragment customMapFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
        if (customMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            customMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, customMapFragment).commit();
        }
        customMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                updateGoogleMap();
            }
        });

    }

    public void updateGoogleMap() {
        Print.i("updateGoogleMap(),mGoogleMap:" + mGoogleMap);
        setupGoogleMap();
        updateUserLocation();
    }

    protected void setupGoogleMap() {
        UiSettings uiSettings = mGoogleMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnCameraMoveStartedListener(this);
        mGoogleMap.setOnCameraMoveListener(this);
        mGoogleMap.setOnCameraMoveCanceledListener(this);
        mGoogleMap.setOnMyLocationButtonClickListener(this);
    }

    public void updateUserLocation() {
        if (null == mMainActivity || null == CARS || CARS.isEmpty()) {
            return;
        }

        Print.i("updateUserLocation(),mLastLocationLatitude:" + mLastLocationLatitude + ",mLastLocationLongitude:" + mLastLocationLongitude);
        if (null != mGoogleMap) {
            mGoogleMap.clear();

            ArrayList<Car> cars = CARS;
            Car firstCar = cars.get(0);
            LatLng firstCarLatLng = new LatLng(firstCar.getLatitude(), firstCar.getLongitude());

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(firstCarLatLng, getGoogleMapCurrentZoomLevel());
            mGoogleMap.animateCamera(update);

            for (Car car : cars) {
                LatLng latLng = new LatLng(car.getLatitude(), car.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng);
                Marker locationMarker = mGoogleMap.addMarker(markerOptions);
                PicassoMarker picassoMarker = new PicassoMarker(locationMarker);
                String carCarImageUrl = car.getCarImageUrl();
                CommonActions.loadImagebyPicasso(mMainActivity, carCarImageUrl, picassoMarker, android.R.drawable.ic_menu_mylocation, android.R.drawable.stat_notify_error);
            }

        }
    }

    public float getGoogleMapCurrentZoomLevel() {
        return mGoogleMapCurrentZoomLevel > 0 ? mGoogleMapCurrentZoomLevel : GOOGLE_MAP_ZOOM_LEVEL_DEFAULT;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onCameraIdle() {
        Print.i("onCameraIdle()");
        CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
        mGoogleMapCurrentZoomLevel = cameraPosition.zoom;
    }

    @Override
    public void onCameraMoveStarted(int i) {
        Print.i("onCameraMoveStarted()");
    }

    @Override
    public void onCameraMove() {
        Print.i("onCameraMove()");
    }

    @Override
    public void onCameraMoveCanceled() {
        Print.i("onCameraMoveCanceled()");
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    protected void updateView() {
        updateUserLocation();
    }


}
