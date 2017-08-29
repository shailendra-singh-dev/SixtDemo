package com.shail.sixtdemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shail.sixtdemo.R;
import com.shail.sixtdemo.adapter.RecyclerViewAdapter;
import com.shail.sixtdemo.model.Car;
import com.shail.sixtdemo.utils.CommonActions;
import com.shail.sixtdemo.utils.Print;


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

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter() {

            @Override
            protected View createView(Context context, ViewGroup viewGroup, int viewType) {
                return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
            }

            @Override
            protected void bindView(final int position, ViewHolder viewHolder) {
                if (null == mCarArrayList || mCarArrayList.isEmpty()) {
                    return;
                }

                final Car car = mCarArrayList.get(position);

                TextView carInfoView = (TextView) viewHolder.getView(R.id.car_info);
                TextView carLicensePlatView = (TextView) viewHolder.getView(R.id.car_license_plat);
                ImageView carLogo = (ImageView) viewHolder.getView(R.id.car_logo);
                String carName = car.getName();
                String carLicensePlate = car.getLicensePlate();
                String carMake = car.getMake();
                String carSeries = car.getSeries();
                String carCarImageUrl = car.getCarImageUrl();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(carName);
                stringBuilder.append(" ");
                stringBuilder.append(carSeries);
                stringBuilder.append(" ");
                stringBuilder.append(carMake);
                String carInfo = stringBuilder.toString();

                if (!carInfo.isEmpty()) {
                    carInfoView.setText(carInfo);
                }
                if (null != carLicensePlate && !carLicensePlate.isEmpty()) {
                    carLicensePlatView.setText(carLicensePlate);
                }
                Print.i("SHAIL bindView(),carCarImageUrl:" + carCarImageUrl);
                if (null != carCarImageUrl && !carCarImageUrl.isEmpty()) {
                    CommonActions.loadImagebyPicasso(mMainActivity, carCarImageUrl, carLogo, android.R.drawable.ic_menu_mylocation, android.R.drawable.stat_notify_error);
                }
            }

            @Override
            public int getItemCount() {
                if (null == mCarArrayList || mCarArrayList.isEmpty()) {
                    return 0;
                }
                return mCarArrayList.size();
            }

            @Override
            public long getItemId(int position) {
                if (null == mCarArrayList || mCarArrayList.isEmpty()) {
                    return 0;
                }

                final Car car = mCarArrayList.get(position);
                return car.hashCode();
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
