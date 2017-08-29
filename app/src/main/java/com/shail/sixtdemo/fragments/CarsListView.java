package com.shail.sixtdemo.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shail.sixtdemo.R;
import com.shail.sixtdemo.activities.MainActivity;
import com.shail.sixtdemo.adapter.RecyclerViewAdapter;
import com.shail.sixtdemo.model.Car;
import com.shail.sixtdemo.utils.CommonActions;
import com.shail.sixtdemo.views.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.BitSet;


/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class CarsListView extends BaseFragment {
    private RecyclerView mRecyclerView;

    public static CarsListView newInstance() {

        Bundle args = new Bundle();

        CarsListView fragment = new CarsListView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars_lisview, container, false);
        setUpRecyclerView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMainActivity = null;
    }


    private void setUpRecyclerView(View mParentView) {
        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setHasFixedSize(true);

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mRecyclerView) {

            @Override
            protected View createView(Context context, ViewGroup viewGroup, int viewType) {
                return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
            }

            @Override
            protected void bindView(final int position, ViewHolder viewHolder) {
                if (null == CARS || CARS.isEmpty()) {
                    return;
                }

                final Car car = CARS.get(position);

                TextView carNameView = (TextView) viewHolder.getView(R.id.car_name);
                TextView carLicensePlatView = (TextView) viewHolder.getView(R.id.car_license_plat);
                AspectRatioImageView carLogo = (AspectRatioImageView) viewHolder.getView(R.id.car_logo);

                String carName = car.getName();
                String carLicensePlate = car.getLicensePlate();
                String carCarImageUrl = car.getCarImageUrl();

                if (null != carName && !carName.isEmpty()) {
                    carNameView.setText(carName);
                }
                if (null != carLicensePlate && !carLicensePlate.isEmpty()) {
                    carLicensePlatView.setText(carLicensePlate);
                }
                if (null != carCarImageUrl && !carCarImageUrl.isEmpty()) {
                    CommonActions.loadImagebyPicasso(mMainActivity, carCarImageUrl, carLogo, android.R.drawable.ic_menu_mylocation, android.R.drawable.stat_notify_error);
                }
            }

            @Override
            public int getItemCount() {
                if (null == CARS || CARS.isEmpty()) {
                    return 0;
                }
                return CARS.size();
            }

            @Override
            public long getItemId(int position) {
                if (null == CARS || CARS.isEmpty()) {
                    return 0;
                }

                final Car car = CARS.get(position);
                return car.hashCode();
            }

            public void setSelected(int position) {
            }
        };

        mRecyclerView.setAdapter(recyclerViewAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void updateView() {
        final RecyclerViewAdapter recyclerViewAdapter = (RecyclerViewAdapter) mRecyclerView.getAdapter();
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
