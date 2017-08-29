package com.shail.sixtdemo.server_connection;


import android.os.AsyncTask;

import com.shail.sixtdemo.application.SixtApplication;
import com.shail.sixtdemo.interfaces.AsyncCallBackInterface;
import com.shail.sixtdemo.messages.MessagePumpEngine;
import com.shail.sixtdemo.utils.AppUtils;
import com.shail.sixtdemo.utils.Print;

import java.io.IOException;
/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class ServerTask {
    public static final int MAX_RETRY_COUNT = 5;
    private final String mURL;

    private AsyncCallBackInterface mAsyncCallBackInterface;

    public ServerTask(AsyncCallBackInterface callbackInterface, String url) {
        mAsyncCallBackInterface = callbackInterface;
        mURL = url;
    }

    public void executeTask() {
        boolean isInternetConnectionAvailable = SixtApplication.getInstance().isInternetConnectionAvailable();
        Print.i("executeTask(),isInternetConnectionAvailable:" + isInternetConnectionAvailable);
        if (isInternetConnectionAvailable) {
            new OkHttptask().execute();
        } else {
            MessagePumpEngine.sendAppMessage(MessagePumpEngine.MessageID.INTERNET_NOT_AVAILABLE);
        }
    }

    private final class OkHttptask extends AsyncTask<Void, Void, String> {

        private int mRetryCount;

        @Override
        protected String doInBackground(Void... params) {
            OkHTTPGet okHTTPGet = OkHTTPGet.getInstance();
            String responseObject = null;
            while (null == responseObject) {
                if (mRetryCount < MAX_RETRY_COUNT) {
                    try {
                        responseObject = okHTTPGet.run(mURL);
                    } catch (IOException e) {
                        responseObject = null;
                    } catch (Exception e) {
                        responseObject = null;
                    }
                    mRetryCount++;
                } else {
                    break;
                }
            }
            return responseObject;
        }

        @Override
        protected void onPostExecute(String response) {
            mAsyncCallBackInterface.onTaskCompleted(response, AppUtils.SUCCESS);
            Print.i("Response " + response + " " + ",Request:" + mURL);
        }
    }




}
