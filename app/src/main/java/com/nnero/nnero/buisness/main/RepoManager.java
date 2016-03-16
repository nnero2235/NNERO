package com.nnero.nnero.buisness.main;

import com.nnero.nnero.bean.Repo;
import com.nnero.nnero.callback.CallBack;
import com.nnero.nnero.http.MyAsyncTask;
import com.nnero.nnero.manager.CacheManager;
import com.nnero.nnero.net.RepoNet;

import java.util.List;
import java.util.ListResourceBundle;

/**
 * Created by nnero on 16/3/11.
 * 按业务划分的manager
 */
public class RepoManager {

    public static MyAsyncTask requestRepos(CallBack<List<Repo>> callBack){
        return new MyAsyncTask<List<Repo>>(callBack) {
            @Override
            protected List<Repo> doInBackground(Void... params) {
                List<Repo> repoList = (List<Repo>) CacheManager.get("repo_list");
                if(repoList == null || repoList.size() == 0){
                    repoList = RepoNet.requestRepo("nnero2235");
                    CacheManager.save("repo_list",repoList);
                }
                return repoList;
            }
        }.doExecute();
    }

    public static MyAsyncTask requestRepos(){
        return new MyAsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                return RepoNet.requestRepo("nnero2235");
            }
        }.doExecute();
    }
}
