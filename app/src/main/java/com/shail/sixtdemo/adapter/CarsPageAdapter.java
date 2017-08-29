package com.shail.sixtdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shail.sixtdemo.BuildConfig;
import com.shail.sixtdemo.R;
import com.shail.sixtdemo.application.SixtApplication;
import com.shail.sixtdemo.fragments.CarsListView;
import com.shail.sixtdemo.fragments.CarsMapView;


/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class CarsPageAdapter extends FragmentStatePagerAdapter {

    public CarsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CarsListView.newInstance();

            case 1:
                return CarsMapView.newInstance();

            default:
                break;

        }

        return null;
    }


    @Override
    public int getCount() {
        return BuildConfig.TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = SixtApplication.getInstance().getString(R.string.cars_listview);
                break;

            case 1:
                title = SixtApplication.getInstance().getString(R.string.cars_mapview);
                break;

            default:
                break;

        }
        return title;
    }


}
