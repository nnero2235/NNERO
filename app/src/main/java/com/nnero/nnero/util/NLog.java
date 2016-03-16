package com.nnero.nnero.util;

import android.util.Log;

import com.nnero.nnero.BuildConfig;

import java.util.List;

/**
 * Created by nnero on 16/3/11.
 */
public class NLog {

    public static void d(String tag,String msg){
        if(BuildConfig.DEBUG)
            Log.d(tag,msg);
    }

    public static <T> void dList(String tag,List<T> list){
        if(BuildConfig.DEBUG){
            for(T t:list){
                Log.d(tag,t.toString());
            }
        }
    }
}
