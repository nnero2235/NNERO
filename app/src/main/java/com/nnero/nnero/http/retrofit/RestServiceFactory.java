package com.nnero.nnero.http.retrofit;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by NNERO on 16/1/29.
 *
 * retrofit框架封装
 */
public class RestServiceFactory {

    private static Retrofit sGitHubRetrofit;

    //该方法由 Api类调用
    public static void init(){
        sGitHubRetrofit = new Retrofit.Builder()
                .client(Api.getOkHttpClient())
                .baseUrl(Api.GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T createGitHubApi(Class<T> clazz){
        return sGitHubRetrofit.create(clazz);
    }
}
