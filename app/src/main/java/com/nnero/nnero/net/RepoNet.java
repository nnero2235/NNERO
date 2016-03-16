package com.nnero.nnero.net;

import com.nnero.nnero.bean.Repo;
import com.nnero.nnero.http.okhttp.Apis;
import com.nnero.nnero.http.okhttp.OkApi;
import com.nnero.nnero.http.okhttp.Response;
import com.nnero.nnero.util.NLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nnero on 16/3/11.
 * 获取repo网络层
 */
public class RepoNet {

    public static final String TAG = "RepoNet";

    public static List<Repo> requestRepo(String user){
        String path = Apis.API_GITHUB_REPO;
        String prefix = path.substring(0, path.indexOf("{"));
        String suffix = path.substring(path.indexOf("}")+1);
        path = prefix + user + suffix;
        try {
            Response response = OkApi.executeGet(path);
            if(response.isSuccess()){
                JSONArray array = new JSONArray(response.getJson());
                List<Repo> repos = new ArrayList<>();
                for(int i=0;i<array.length();i++){
                    repos.add(new Repo((JSONObject) array.get(i)));
                }
                return repos;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
