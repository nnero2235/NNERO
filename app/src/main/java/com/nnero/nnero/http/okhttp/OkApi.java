package com.nnero.nnero.http.okhttp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by NNERO on 16/1/29.
 *
 * 简单封装okhttp  可以认为是传输层
 */
public class OkApi {

    public static final String GITHUB_URL = "https://api.github.com";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient sOkHttpClient = new OkHttpClient();

    private static String buildGetParams(Map<String,String> params) throws UnsupportedEncodingException {
        if(params == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(String key : params.keySet()){
            sb.append(key)
                    .append("=")
                    .append(URLEncoder.encode(params.get(key),"utf-8"))
                    .append("&");
        }
        if(sb.length() > 0){
            sb.insert(0,"?");
            return sb.substring(0,sb.length()-2);
        } else {
            return "";
        }
    }

    private static RequestBody buildPostBody(String json){
        return RequestBody.create(JSON,json);
    }

    public static Response executeGet(String path) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(GITHUB_URL+path)
                .build();
        return sOkHttpClient.newCall(request).execute();
    }

    public static Response executePost(String path,RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .post(requestBody)
                .url(GITHUB_URL+path)
                .build();
        return sOkHttpClient.newCall(request).execute();
    }
}
