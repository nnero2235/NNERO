package com.nnero.nnero.http.retrofit;


import com.nnero.nnero.http.retrofit.call.GitHubApi;

import okhttp3.OkHttpClient;

/**
 * Created by NNERO on 16/1/29.
 */
public class Api {

    public static final String GITHUB_URL = "https://api.github.com";

    private static OkHttpClient okHttpClient;

    public Api(){
        okHttpClient = new OkHttpClient();
        RestServiceFactory.init();
    }

    public static OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    public GitHubApi getGitHubApi(){
        return RestServiceFactory.createGitHubApi(GitHubApi.class);
    }
}
