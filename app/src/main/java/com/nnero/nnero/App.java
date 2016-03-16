package com.nnero.nnero;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.nnero.nnero.http.retrofit.Api;
import com.nnero.nnero.manager.CacheManager;
import com.nnero.nnero.manager.ConfigManager;
import com.nnero.nnero.util.ImageUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by NNERO on 16/1/28.
 */
public class App extends Application{

    private static App instance;
    private Api mApi;//retrofit才有用

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CacheManager.init();
        if(BuildConfig.IMAGE_LOADER == ConfigManager.IMAGE_LOADER){
            ImageUtil.initImageLoader(this);
        } else if(BuildConfig.IMAGE_LOADER == ConfigManager.GLIDE){

        }

        if(BuildConfig.NETWORK == ConfigManager.RETROFIT){
            mApi = new Api();
        } else if(BuildConfig.NETWORK == ConfigManager.ASYNC_TASK){

        }


    }

    public Api getApi(){
        return mApi;
    }

    public static Context getInstance(){
        return instance;
    }
}
