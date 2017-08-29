package com.shail.sixtdemo.server_connection;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

import com.shail.sixtdemo.interfaces.AsyncCallBackInterface;

public class Webservices {

    public static void sendGetCall(AsyncCallBackInterface callbackInterface,String url) {
        new ServerTask(callbackInterface, url).executeTask();
    }


}
