package com.shail.sixtdemo.server_connection;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHTTPGet {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static final OkHTTPGet OK_HTTP_GET = new OkHTTPGet();

    private OkHTTPGet() {
        //Making constructor Private.
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public InputStream getStreamFromServer(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().byteStream();
    }

    public static synchronized OkHTTPGet getInstance() {
        return OK_HTTP_GET;
    }
}
