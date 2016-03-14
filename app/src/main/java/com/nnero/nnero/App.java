package com.nnero.nnero;

import android.app.Application;
import android.content.Context;

import com.nnero.nnero.http.retrofit.Api;
import com.nnero.nnero.manager.CacheManager;
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
//    private Api mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        mApi = new Api();
        CacheManager.init();
        initImageLoader(this);
    }

//    public Api getApi(){
//        return mApi;
//    }
    public static Context getInstance(){
        return instance;
    }

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)
//        .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .threadPoolSize(3)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//        .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
