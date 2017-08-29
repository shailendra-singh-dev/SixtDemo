package com.shail.sixtdemo.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.shail.sixtdemo.BuildConfig;
import com.shail.sixtdemo.R;
import com.shail.sixtdemo.activities.MainActivity;
import com.shail.sixtdemo.interfaces.AsyncCallBackInterface;
import com.shail.sixtdemo.model.Car;
import com.shail.sixtdemo.parsers.JasonDataParser;
import com.shail.sixtdemo.server_connection.Webservices;
import com.shail.sixtdemo.utils.CommonActions;

import java.util.ArrayList;

import static com.shail.sixtdemo.utils.AppUtils.SUCCESS;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public abstract class BaseFragment extends Fragment implements AsyncCallBackInterface {

    protected static ArrayList<Car> mCarArrayList;
    protected MainActivity mMainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(null == mCarArrayList || mCarArrayList.isEmpty()){
            Webservices.sendGetCall(this, BuildConfig.REQUEST_URL);
        }
    }

    @Override
    public void onTaskCompleted(String response, int taskStatus) {
        if (taskStatus == SUCCESS) {
            mCarArrayList = JasonDataParser.getCars(response);
            updateView();
        } else {
            CommonActions.showAlert(mMainActivity, getString(R.string.error_data_not_found), R.string.alert_dialog_positive_btn_text_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    protected abstract void updateView();
}
