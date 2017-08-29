package com.shail.sixtdemo.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shail.sixtdemo.R;
import com.shail.sixtdemo.adapter.CarsPageAdapter;
import com.shail.sixtdemo.messages.MessagePumpEngine;
import com.shail.sixtdemo.utils.CommonActions;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private static final MessagePumpEngine.MessageID[] MESSAGES = {MessagePumpEngine.MessageID.INTERNET_NOT_AVAILABLE};

    private final Handler mMessageHandler = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MessagePumpEngine.addAppMessageHandler(mMessageHandler, MESSAGES);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MessagePumpEngine.removeAppMessageHandler(mMessageHandler, MESSAGES);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        CarsPageAdapter pageAdapter = new CarsPageAdapter(getSupportFragmentManager());
        for (int i = 0; i < pageAdapter.getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageAdapter.getPageTitle(i)));
        }

        viewPager.setAdapter(pageAdapter);
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


    @Override
    public boolean handleMessage(Message message) {
        final MessagePumpEngine.MessageID id = MessagePumpEngine.MessageID.getID(message.what);
        switch (id) {
            case INTERNET_NOT_AVAILABLE:
                CommonActions.showAlert(this, getString(R.string.error_internet_not_found), R.string.alert_dialog_positive_btn_text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
        }
        return false;
    }
}
