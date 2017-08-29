package com.shail.sixtdemo.server_connection;

/**
 * Created by joyal on 10/5/2015.
 */

import android.content.Context;

import com.shail.sixtdemo.interfaces.AsyncCallBackInterface;

import org.json.JSONObject;

public class Webservices {

    public static void sendGetCall(AsyncCallBackInterface callbackInterface,String url) {
        new ServerTask(callbackInterface, url).executeTask();
    }


}
