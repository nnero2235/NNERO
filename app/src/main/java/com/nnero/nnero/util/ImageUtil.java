package com.nnero.nnero.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * Created by NNERO on 16/1/19.
 */
public class ImageUtil {

    public static void initImageLoader(Context context){
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


    private static DisplayImageOptions.Builder getOptions(int errorDrawable) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .showImageOnLoading(errorDrawable)
                .showImageForEmptyUri(errorDrawable)
                .showImageOnFail(errorDrawable);
    }

    public static void loadImage(String url, ImageView imageView, int errorDrawable) {
        DisplayImageOptions options = getOptions(errorDrawable)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public static void blurLoadImage(String url,ImageView imageView,int errorDrawable, final Context context){
        DisplayImageOptions options = getOptions(errorDrawable)
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        return BlurUtil.fastBlur(context, bitmap, 25);
                    }
                })
                .build();
        ImageLoader.getInstance().displayImage(url,imageView,options);
    }
}
