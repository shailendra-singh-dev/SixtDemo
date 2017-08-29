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
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.shail.sixtdemo.BuildConfig;
import com.shail.sixtdemo.LocationUpdatesService;
import com.shail.sixtdemo.R;
import com.shail.sixtdemo.adapter.CarsPageAdapter;
import com.shail.sixtdemo.application.SixtApplication;
import com.shail.sixtdemo.interfaces.AsyncCallBackInterface;
import com.shail.sixtdemo.messages.MessagePumpEngine;
import com.shail.sixtdemo.server_connection.Webservices;
import com.shail.sixtdemo.utils.CommonActions;
import com.shail.sixtdemo.utils.Print;

import static com.shail.sixtdemo.utils.AppUtils.BROADCAST_GOOGLE_SERVICES_CONNECTION_FAILED;
import static com.shail.sixtdemo.utils.AppUtils.BROADCAST_GOOGLE_SERVICES_CONNECTION_SUCCESS;
import static com.shail.sixtdemo.utils.AppUtils.GOOGLE_SERVICES_CONNECTION_FAILED_RESULT;
import static com.shail.sixtdemo.utils.AppUtils.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.shail.sixtdemo.utils.AppUtils.REQUEST_CHECK_SETTINGS;
import static com.shail.sixtdemo.utils.AppUtils.REQUEST_PLAY_SERVICES_RESOLVE_ERROR;
import static com.shail.sixtdemo.utils.AppUtils.SUCCESS;

public class MainActivity extends AppCompatActivity {

    private CarsPageAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new CarsPageAdapter(getSupportFragmentManager());
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mPagerAdapter.getPageTitle(i)));
        }

        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
