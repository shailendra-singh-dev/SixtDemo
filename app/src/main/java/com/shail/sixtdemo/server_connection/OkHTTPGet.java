package com.shail.sixtdemo.server_connection;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class OkHTTPGet {
    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static final OkHTTPGet OK_HTTP_GET = new OkHTTPGet();

    private OkHTTPGet() {
        //Making constructor Private.
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static synchronized OkHTTPGet getInstance() {
        return OK_HTTP_GET;
    }
}
