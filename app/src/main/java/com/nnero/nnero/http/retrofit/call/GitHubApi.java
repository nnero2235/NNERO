package com.nnero.nnero.http.retrofit.call;

import com.nnero.nnero.bean.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by NNERO on 16/1/29.
 * 还有多种方式  查github retrofit
 */
public interface GitHubApi {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
